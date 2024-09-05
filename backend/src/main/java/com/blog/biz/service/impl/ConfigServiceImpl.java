package com.blog.biz.service.impl;

import com.blog.biz.annotation.Config;
import com.blog.biz.annotation.ConfigProperty;
import com.blog.biz.model.config.GitConfig;
import com.blog.biz.model.config.LocalStoragePolicyConfig;
import com.blog.biz.model.config.StoragePolicyConfig;
import com.blog.biz.model.entity.ConfigEntity;
import com.blog.biz.repository.ConfigRepository;
import com.blog.biz.service.ConfigService;
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

	private final Map<Class<?>, ConfigClass> configClassMap = new ConcurrentHashMap<>();

	private final ConfigRepository configRepository;

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

		ConfigClass configClass = configClassMap.computeIfAbsent(clazz, this::parseConfigClass);

		String prefix = configClass.getPrefix();

		List<ConfigEntity> configEntities = configRepository.findAllByConfigKeyLike(prefix + "%");
		if (CollectionUtils.isEmpty(configEntities)) {
			log.warn("No configs found for the key prefix [{}].", prefix);
			return Optional.empty();
		}
		Map<String, ConfigEntity> configEntityMap = configEntities.stream()
			.collect(Collectors.toMap(ConfigEntity::getConfigKey, Function.identity()));

		// 使用BeanWrapper，方便不同类型赋值
		BeanWrapper beanWrapper = new BeanWrapperImpl(object);

		for (ConfigField configField : configClass.getConfigFields()) {
			Field field = configField.getField();

			String configKey = prefix + field.getName();
			ConfigEntity configEntity = configEntityMap.get(configKey);
			if (configField.isRequired() && configEntity == null) {
				throw new IllegalArgumentException("The required config [" + configKey + "] is not found.");
			}
			if (configEntity != null && configEntity.getConfigValue() != null) {
				String value = configEntity.getConfigValue();
				// 判断是否需要解密
				if (configField.isEncrypt()) {
					value = SecureUtil.decrypt(value);
				}
				beanWrapper.setPropertyValue(configField.getName(), value);
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

		ConfigClass configClass = configClassMap.computeIfAbsent(clazz, this::parseConfigClass);

		String prefix = configClass.getPrefix();

		for (ConfigField configField : configClass.getConfigFields()) {
			Field field = configField.getField();
			String configKey = prefix + configField.getName();
			field.setAccessible(true);
			Object value = field.get(data);
			if (value == null && configField.isRequired()) {
				throw new IllegalArgumentException("The required config [" + configKey + "] is not found.");
			}
			if (value != null && configField.isEncrypt()) {
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
	private ConfigClass parseConfigClass(Class<?> clazz) {
		Config config = clazz.getAnnotation(Config.class);
		String prefix = config.prefix();

		List<ConfigField> configFields = Arrays.stream(clazz.getDeclaredFields())
			.filter(field -> !Modifier.isStatic(field.getModifiers()))
			.map(field -> {
				if (field.isAnnotationPresent(ConfigProperty.class)) {
					ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
					return new ConfigField(field,
							StringUtils.isNotBlank(configProperty.name()) ? configProperty.name() : field.getName(),
							configProperty.encrypt(), configProperty.required());
				}
				else {
					return new ConfigField(field, field.getName(), false, true);
				}
			})
			.toList();
		return new ConfigClass(prefix, configFields);
	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class ConfigClass {

		/**
		 * 前缀
		 */
		private String prefix;

		/**
		 * 字段配置
		 */
		private List<ConfigField> configFields;

	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class ConfigField {

		/**
		 * 字段
		 */
		private Field field;

		/**
		 * 映射字段名称
		 */
		private String name;

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
