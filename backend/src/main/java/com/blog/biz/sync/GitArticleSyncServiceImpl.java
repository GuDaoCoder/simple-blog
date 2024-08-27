package com.blog.biz.sync;

import com.blog.biz.enums.ArticleSource;
import com.blog.biz.enums.ArticleStatus;
import com.blog.biz.exception.BlogSyncException;
import com.blog.biz.file.FileFactory;
import com.blog.biz.model.config.GitConfig;
import com.blog.biz.model.entity.*;
import com.blog.biz.repository.*;
import com.blog.biz.service.ConfigService;
import com.blog.biz.support.GitOperation;
import com.blog.biz.support.MarkdownParser;
import com.blog.common.util.ColorUtil;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Gudao
 * @since 2024/8/15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GitArticleSyncServiceImpl implements ArticleSyncService {

	private final ConfigService configService;

	private final FileFactory fileFactory;

	private final CategoryRepository categoryRepository;

	private final TagRepository tagRepository;

	private final ArticleRepository articleRepository;

	private final ArticleContentRepository articleContentRepository;

	private final ArticleTagRepository articleTagRepository;

	private int summaryLength;

	private GitConfig gitConfig;

	private Map<String, CategoryEntity> existsCategoryMap;

	private Map<String, TagEntity> existsTagMap;

	@Override
	public void sync() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		log.info("Start sync markdown article");

		// 初始化信息
		init();

		// 同步git仓库信息
		syncGitRepo();
		log.info("Sync git repo success");

		// 扫描md文件
		Collection<File> files = scanFilterMarkdownFiles();
		if (CollectionUtils.isNotEmpty(files)) {
			// 初始化一些已存在的信息，包括分类、标签
			initExistsInfo();

			files.forEach(this::doSync);
		}

		stopWatch.stop();
		log.info("Sync markdown article consume {} ms", stopWatch.getTotalTimeMillis());
	}

	/**
	 * 初始化信息
	 * @return void
	 **/
	private void init() {
		this.gitConfig = getGitConfig();
		if (this.summaryLength == 0) {
			this.summaryLength = getSummaryLength();
		}
	}

	/**
	 * 执行同步
	 * @param file
	 * @return void
	 **/
	private void doSync(File file) {
		MarkdownParser.MdContext mdContext = parseMd(file);
		syncCategory(mdContext.getCategories());
		syncTag(Optional.ofNullable(mdContext.getExtra()).map(MarkdownParser.Extra::getTags).orElse(null));
		syncArticle(mdContext);
	}

	/**
	 * 同步分类
	 * @param parsedCategories
	 * @return void
	 **/
	private void syncCategory(List<MarkdownParser.Category> parsedCategories) {
		CategoryEntity parentCategoryEntity = null;
		for (MarkdownParser.Category parsedCategory : parsedCategories) {
			CategoryEntity categoryEntity = existsCategoryMap.get(categoryKey(parsedCategory));
			if (categoryEntity == null) {
				categoryEntity = new CategoryEntity();
				categoryEntity.setCategoryName(parsedCategory.getCategoryName())
					.setLevel(parsedCategory.getLevel())
					.setOrderNo(parsedCategory.getOrderNo())
					.setParentCategoryId(parentCategoryEntity == null ? 0L : parentCategoryEntity.getCategoryId())
					.setFullId(parentCategoryEntity == null ? "0" : categoryKey(parentCategoryEntity));
				categoryRepository.save(categoryEntity);
				existsCategoryMap.put(categoryKey(categoryEntity), categoryEntity);
			}
			parentCategoryEntity = categoryEntity;
		}
	}

	/**
	 * 同步标签
	 * @param parsedTags
	 * @return void
	 **/
	private void syncTag(List<String> parsedTags) {
		if (CollectionUtils.isNotEmpty(parsedTags)) {
			List<TagEntity> needToAddTags = parsedTags.stream()
				.filter(tagName -> !existsTagMap.containsKey(tagName))
				.map(tagName -> {
					TagEntity tagEntity = new TagEntity();
					tagEntity.setTagName(tagName).setColor(ColorUtil.generateHexColor());
					return tagEntity;
				})
				.toList();
			if (CollectionUtils.isNotEmpty(needToAddTags)) {
				tagRepository.saveAll(needToAddTags);
				existsTagMap.putAll(
						needToAddTags.stream().collect(Collectors.toMap(TagEntity::getTagName, Function.identity())));
			}
		}
	}

	/**
	 * 同步文章
	 * @param mdContext
	 * @return void
	 **/
	public void syncArticle(MarkdownParser.MdContext mdContext) {
		// 通过对文章内容nd5加密生成文章内容的唯一标识
		String parsedFileHash = DigestUtils
			.md5Hex(StringUtils.isNotBlank(mdContext.getContent()) ? mdContext.getContent() : "empty");
		// 解析出的分类Id
		Long parsedCategoryId = null;
		if (CollectionUtils.isNotEmpty(mdContext.getCategories())) {
			MarkdownParser.Category category = mdContext.getCategories().get(mdContext.getCategories().size() - 1);
			parsedCategoryId = Optional.ofNullable(existsCategoryMap.get(categoryKey(category)))
				.map(CategoryEntity::getCategoryId)
				.orElse(null);
		}

		// 文章基础信息
		ArticleEntity articleEntity = articleRepository.findByTitle(mdContext.getTitle());
		// 文章内容信息
		ArticleContentEntity articleContentEntity = null;

		boolean isNewArticle = false;

		if (articleEntity != null) {
			// 文章未发生改变
			if (StringUtils.equals(parsedFileHash, articleEntity.getFileHash())
					&& Objects.equals(parsedCategoryId, articleEntity.getCategoryId())) {
				return;
			}
			articleEntity.setFileHash(parsedFileHash)
				.setSummary(getSummary(mdContext.getSummary()))
				.setCoverPictureUrl(mdContext.getCoverPictureUrl())
				.setCategoryId(parsedCategoryId);

			articleContentEntity = articleContentRepository.findByArticleId(articleEntity.getArticleId());
			articleContentEntity.setContent(mdContext.getContent());
		}
		else {
			isNewArticle = true;
			articleEntity = new ArticleEntity();
			articleEntity.setTitle(mdContext.getTitle())
				.setSummary(getSummary(mdContext.getSummary()))
				.setCoverPictureUrl(mdContext.getCoverPictureUrl())
				.setFileHash(parsedFileHash)
				.setSource(ArticleSource.GIT_SYNC)
				.setStatus(ArticleStatus.DRAFT)
				.setTop(Boolean.FALSE)
				.setEnableComment(Boolean.FALSE)
				.setCategoryId(parsedCategoryId);
		}
		// 保存文章基础信息
		articleRepository.save(articleEntity);
		if (articleContentEntity == null) {
			articleContentEntity = new ArticleContentEntity();
			articleContentEntity.setArticleId(articleEntity.getArticleId()).setContent(mdContext.getContent());
		}
		// 保存文章内容信息
		articleContentRepository.save(articleContentEntity);

		// 保存文章标签关联关系
		syncArticleTags(isNewArticle, articleEntity.getArticleId(), mdContext.getTags());
	}

	/**
	 * 同步文章和标签关系
	 * @param isNewArticle
	 * @param articleId
	 * @param tagNames
	 * @return void
	 **/
	private void syncArticleTags(boolean isNewArticle, Long articleId, List<String> tagNames) {
		List<ArticleTagEntity> existsArticleTagEntities = isNewArticle ? new ArrayList<>()
				: articleTagRepository.findAllByArticleId(articleId);
		List<ArticleTagEntity> parsedArticleTagEntities = tagNames == null ? new ArrayList<>()
				: tagNames.stream().map(tagName -> {
					TagEntity tagEntity = existsTagMap.get(tagName);
					if (tagEntity != null) {
						return new ArticleTagEntity().setArticleId(articleId).setTagId(tagEntity.getTagId());
					}
					return null;
				}).filter(Objects::nonNull).toList();

		Collection<ArticleTagEntity> needDeleteData = CollectionUtils.subtract(existsArticleTagEntities,
				parsedArticleTagEntities);
		if (CollectionUtils.isNotEmpty(needDeleteData)) {
			articleTagRepository.deleteAll(needDeleteData);
		}
		Collection<ArticleTagEntity> needAddData = CollectionUtils.subtract(parsedArticleTagEntities,
				existsArticleTagEntities);
		if (CollectionUtils.isNotEmpty(needAddData)) {
			articleTagRepository.saveAll(needAddData);
		}
	}

	/**
	 * 获取git配置信息
	 * @return GitConfig
	 **/
	private GitConfig getGitConfig() {
		Optional<GitConfig> optionalGitConfig = configService.loadGitConfig();
		if (optionalGitConfig.isEmpty()) {
			throw new BlogSyncException("Git信息未配置");
		}
		return optionalGitConfig.get();
	}

	/**
	 * 获取简介长度
	 * @param
	 * @return int
	 **/
	private int getSummaryLength() {
		Field summaryField = null;
		try {
			summaryField = ArticleEntity.class.getDeclaredField("summary");
		}
		catch (NoSuchFieldException ignored) {
		}
		if (summaryField != null) {
			if (summaryField.isAnnotationPresent(Column.class)) {
				Column column = summaryField.getAnnotation(Column.class);
				return column.length();
			}
		}
		return 200;
	}

	/**
	 * 同步git仓库信息
	 * @return void
	 **/
	private void syncGitRepo() {
		// 从Git上拉取最新的博客文章信息
		try {
			GitOperation.builder()
				.url(gitConfig.getUrl())
				.username(gitConfig.getUsername())
				.password(gitConfig.getPassword())
				.localPath(gitConfig.getLocalPath())
				.branch(gitConfig.getBranch())
				.build()
				.thenPull()
				.execute();
		}
		catch (GitAPIException e) {
			throw new BlogSyncException(e, "从Git地址[{}]同步博客文章信息失败", gitConfig.getUrl());
		}
	}

	/**
	 * 扫描md文件
	 * @return Collection<File>
	 **/
	private Collection<File> scanFilterMarkdownFiles() {
		return FileUtils.listFiles(new File(gitConfig.getLocalPath()), new SuffixFileFilter(".md"),
				TrueFileFilter.INSTANCE);
	}

	/**
	 * 初始化一些已存在的信息，包括分类、标签
	 * @return void
	 **/
	private void initExistsInfo() {
		existsCategoryMap = categoryRepository.findAll()
			.stream()
			.collect(Collectors.toConcurrentMap(this::categoryKey, Function.identity()));
		existsTagMap = tagRepository.findAll()
			.stream()
			.collect(Collectors.toConcurrentMap(TagEntity::getTagName, Function.identity()));
	}

	/**
	 * 分类key
	 * @param categoryEntity
	 * @return String
	 **/
	private String categoryKey(CategoryEntity categoryEntity) {
		return categoryEntity.getCategoryName() + "-" + categoryEntity.getLevel();
	}

	/**
	 * 分类key
	 * @param category
	 * @return String
	 **/
	private String categoryKey(MarkdownParser.Category category) {
		return category.getCategoryName() + "-" + category.getLevel();
	}

	/**
	 * 解析md文件
	 * @param file
	 * @return MarkdownParser.MdContext
	 **/
	private MarkdownParser.MdContext parseMd(File file) {
		try {
			return MarkdownParser.builder()
				.file(file)
				.rootPath(gitConfig.getLocalPath())
				.replaceImageUrl(originalImageUrl -> {
					if (StringUtils.isNotBlank(originalImageUrl)) {
						File imageFile = new File(file.getParentFile(), originalImageUrl);
						if (imageFile.exists()) {
							AttachmentEntity attachmentEntity = fileFactory.getFileHandler().upload(imageFile);
							return attachmentEntity.getPath();
						}
					}
					return originalImageUrl;
				})
				.build()
				.parse();
		}
		catch (IOException e) {
			throw new BlogSyncException(e, "Parse markdown file [{}] error", file.getAbsolutePath(), e);
		}
	}

	/**
	 * 获取简介信息
	 * @param summary
	 * @return String
	 **/
	private String getSummary(String summary) {
		if (StringUtils.isBlank(summary)) {
			return summary;
		}
		if (summary.length() <= summaryLength) {
			return summary;
		}
		return summary.substring(0, summaryLength);
	}

}
