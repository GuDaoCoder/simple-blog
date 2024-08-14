package com.github.biz.service;

import com.github.biz.annotation.Config;
import com.github.biz.annotation.ConfigProperty;
import com.github.biz.model.entity.ConfigEntity;
import com.github.biz.repository.ConfigRepository;
import com.github.common.util.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
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

	private final ConfigRepository configRepository;

	@SneakyThrows
	@Override
	public <T> T load(Class<T> clazz) {
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

		Config config = clazz.getAnnotation(Config.class);
		String prefix = config.prefix();

		List<ConfigEntity> configEntities = configRepository.findAllByConfigKeyLike(prefix + "%");
		if (CollectionUtils.isEmpty(configEntities)) {
			log.warn("No configs found for the key prefix [{}].", prefix);
		}
		Map<String, ConfigEntity> configEntityMap = configEntities.stream()
			.collect(Collectors.toMap(ConfigEntity::getConfigKey, Function.identity()));

		// 使用BeanWrapper，方便不同类型赋值
		BeanWrapper beanWrapper = new BeanWrapperImpl(object);

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 不包括静态变量
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			// 属性名称
			String name = field.getName();
			// 是否加密
			boolean encrypt = false;
			// 是否必须
			boolean required = true;

			if (field.isAnnotationPresent(ConfigProperty.class)) {
				ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
				if (StringUtils.isNotBlank(configProperty.key())) {
					name = configProperty.key();
				}
				encrypt = configProperty.encrypt();
				required = configProperty.required();
			}
			String configKey = prefix + name;
			ConfigEntity configEntity = configEntityMap.get(configKey);
			if (required && configEntity == null) {
				throw new IllegalArgumentException("The required config [" + configKey + "] is not found.");
			}
			if (configEntity != null && configEntity.getConfigValue() != null) {
				String value = configEntity.getConfigValue();
				// 判断是否需要解密
				if (encrypt) {
					value = SecureUtil.decrypt(value);
				}
				beanWrapper.setPropertyValue(field.getName(), value);
			}
		}
		return object;
	}

}
