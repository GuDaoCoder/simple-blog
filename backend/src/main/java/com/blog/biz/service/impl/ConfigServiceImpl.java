package com.blog.biz.service.impl;

import com.blog.biz.annotation.Config;
import com.blog.biz.annotation.ConfigProperty;
import com.blog.biz.model.config.GitConfig;
import com.blog.biz.model.config.LocalStoragePolicyConfig;
import com.blog.biz.model.config.StoragePolicyConfig;
import com.blog.biz.model.entity.ConfigEntity;
import com.blog.biz.model.response.ConfigItemResponse;
import com.blog.biz.model.response.ConfigResponse;
import com.blog.biz.repository.ConfigRepository;
import com.blog.biz.service.ConfigService;
import com.blog.common.util.ReflectUtil;
import com.blog.common.util.SecureUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Gudao
 * @since 2024/8/13
 */
@Slf4j
@AllArgsConstructor
@Service
public class ConfigServiceImpl implements ConfigService {

	private final Map<Class<?>, ConfigClassContext> configClassMap = new ConcurrentHashMap<>();

	private final ConfigRepository configRepository;

	private Set<Class<?>> classes;

	@SneakyThrows
	@Override
	public List<ConfigResponse> list() {
		List<ConfigEntity> configEntities = configRepository.findAll();
		if (CollectionUtils.isEmpty(classes)) {
			classes = ReflectUtil.scanClassesWithAnnotation("com.blog.biz.model.config", Config.class);
		}
		if (CollectionUtils.isNotEmpty(configEntities) && CollectionUtils.isNotEmpty(classes)) {
			Map<String, ConfigEntity> configEntityMap = configEntities.stream()
				.collect(Collectors.toMap(ConfigEntity::getConfigKey, Function.identity()));
			return classes.stream().map(clazz -> {
				ConfigClassContext configClassContext = configClassMap.computeIfAbsent(clazz, this::parseConfigClass);
				ConfigResponse configResponse = new ConfigResponse();
				configResponse.setConfigPrefix(configClassContext.getPrefix());
				configResponse.setConfigDesc(configClassContext.getDesc());

				List<ConfigItemContext> configItemContexts = configClassContext.getConfigItemContexts();
				if (CollectionUtils.isNotEmpty(configItemContexts)) {
					List<ConfigItemResponse> configItemResponses = new ArrayList<>();
					configItemContexts.forEach(configItemContext -> {
						String configKey = configClassContext.getPrefix() + configItemContext.getName();
						ConfigEntity configEntity = configEntityMap.get(configKey);
						if (configEntity != null) {
							ConfigItemResponse configItemResponse = new ConfigItemResponse();
							configItemResponse.setConfigItemDesc(configItemContext.getDesc())
								.setConfigItemValue(configEntity.getConfigValue())
								.setRequired(configItemContext.isRequired())
								.setEncrypt(configItemContext.isEncrypt());
							configItemResponses.add(configItemResponse);
						}
					});
					configResponse.setItems(configItemResponses);
				}
				return configResponse;
			}).toList();
		}
		return List.of();
	}

	@SneakyThrows
	@Override
	public <T> Optional<T> load(Class<T> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("The provided class cannot be null.");
		}
		if (!clazz.isAnnotationPresent(Config.class)) {
			throw new IllegalArgumentException("The provided class must have the @Config annotation.");
		}

		// 通过无参构造器创建对象
		T object;
		try {
			object = clazz.getDeclaredConstructor().newInstance();
			log.debug("Successfully created an instance of {}", clazz.getName());
		}
		catch (Exception e) {
			log.error("Failed to create an instance of {}: {}", clazz.getName(), e.getMessage());
			throw new RuntimeException("Failed to create an instance of " + clazz.getName(), e);
		}

		ConfigClassContext configClassContext = configClassMap.computeIfAbsent(clazz, this::parseConfigClass);

		String prefix = configClassContext.getPrefix();

		List<ConfigEntity> configEntities = configRepository.findAllByConfigKeyLike(prefix + "%");
		if (CollectionUtils.isEmpty(configEntities)) {
			log.warn("No configs found for the key prefix [{}].", prefix);
			return Optional.empty();
		}
		Map<String, ConfigEntity> configEntityMap = configEntities.stream()
			.collect(Collectors.toMap(ConfigEntity::getConfigKey, Function.identity()));

		// 使用BeanWrapper，方便不同类型赋值
		BeanWrapper beanWrapper = new BeanWrapperImpl(object);

		for (ConfigItemContext configItemContext : configClassContext.getConfigItemContexts()) {
			Field field = configItemContext.getField();

			String configKey = prefix + field.getName();
			ConfigEntity configEntity = configEntityMap.get(configKey);
			if (configItemContext.isRequired() && configEntity == null) {
				throw new IllegalArgumentException("The required config [" + configKey + "] is not found.");
			}
			if (configEntity != null && configEntity.getConfigValue() != null) {
				String value = configEntity.getConfigValue();
				// 判断是否需要解密
				if (configItemContext.isEncrypt()) {
					value = SecureUtil.decrypt(value);
				}
				beanWrapper.setPropertyValue(configItemContext.getName(), value);
			}
		}
		return Optional.of(object);
	}

	@SneakyThrows
	@Override
	public <T> void save(T data) {
		if (data == null) {
			throw new IllegalArgumentException("The data cannot be null.");
		}
		Class<?> clazz = data.getClass();
		if (!clazz.isAnnotationPresent(Config.class)) {
			throw new IllegalArgumentException("The provided class must have the @Config annotation.");
		}

		List<ConfigEntity> entities = new ArrayList<>();

		ConfigClassContext configClassContext = configClassMap.computeIfAbsent(clazz, this::parseConfigClass);

		String prefix = configClassContext.getPrefix();

		for (ConfigItemContext configItemContext : configClassContext.getConfigItemContexts()) {
			Field field = configItemContext.getField();
			String configKey = prefix + configItemContext.getName();
			field.setAccessible(true);
			Object value = field.get(data);
			if (value == null && configItemContext.isRequired()) {
				throw new IllegalArgumentException("The required config [" + configKey + "] is not found.");
			}
			if (value != null && configItemContext.isEncrypt()) {
				value = SecureUtil.encrypt(value.toString());
			}
			entities.add(new ConfigEntity(configKey, value != null ? value.toString() : null));
		}

		if (CollectionUtils.isNotEmpty(entities)) {
			configRepository.saveAll(entities);
		}
	}

	@Override
	public Optional<GitConfig> loadGitConfig() {
		return load(GitConfig.class);
	}

	@Override
	public Optional<StoragePolicyConfig> loadStoragePolicy() {
		return load(StoragePolicyConfig.class);
	}

	@Override
	public Optional<LocalStoragePolicyConfig> loadLocalStoragePolicy() {
		return load(LocalStoragePolicyConfig.class);
	}

	/**
	 * 解析Config类信息
	 * @param clazz
	 * @return ConfigClass
	 **/
	private ConfigClassContext parseConfigClass(Class<?> clazz) {
		Config config = clazz.getAnnotation(Config.class);
		String prefix = config.prefix();

		List<ConfigItemContext> configItemContexts = Arrays.stream(clazz.getDeclaredFields())
			.filter(field -> !Modifier.isStatic(field.getModifiers()))
			.map(field -> {
				if (field.isAnnotationPresent(ConfigProperty.class)) {
					ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
					return new ConfigItemContext(field,
							StringUtils.isNotBlank(configProperty.name()) ? configProperty.name() : field.getName(),
							configProperty.desc(), configProperty.encrypt(), configProperty.required());
				}
				else {
					return new ConfigItemContext(field, field.getName(), null, false, true);
				}
			})
			.toList();
		return new ConfigClassContext(prefix, config.desc(), configItemContexts);
	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class ConfigClassContext {

		/**
		 * 前缀
		 */
		private String prefix;

		/**
		 * 描述
		 */
		private String desc;

		/**
		 * 字段配置
		 */
		private List<ConfigItemContext> configItemContexts;

	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class ConfigItemContext {

		/**
		 * 字段
		 */
		private Field field;

		/**
		 * 映射字段名称
		 */
		private String name;

		/**
		 * 描述
		 */
		private String desc;

		/**
		 * 是否加密
		 */
		private boolean encrypt;

		/**
		 * 是否必须
		 */
		private boolean required;

	}

}
