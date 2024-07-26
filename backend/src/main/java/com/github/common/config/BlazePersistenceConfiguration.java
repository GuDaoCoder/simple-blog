package com.github.common.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * Blaze-Persistence配置 <a href=
 * <a>https://persistence.blazebit.com/documentation/1.6/core/manual/en_US/index.html></a>
 *
 * @author Gudao
 * @since 2024/7/26
 */
@Configuration
public class BlazePersistenceConfiguration {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	/**
	 * 创建CriteriaBuilderFactory
	 * @return CriteriaBuilderFactory
	 **/
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Lazy(false)
	public CriteriaBuilderFactory createCriteriaBuilderFactory() {
		CriteriaBuilderConfiguration config = Criteria.getDefault();
		return config.createCriteriaBuilderFactory(entityManagerFactory);
	}

}
