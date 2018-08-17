package com.project.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.project.repository")
//@PropertySource("classpath:jpa.properties")
public class JpaConfig {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.SQL_SERVER);
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("com.project.entity");
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("hibernate.connection.driver_class"));
		dataSource.setUrl(env.getRequiredProperty("hibernate.connection.url"));
		dataSource.setUsername(env.getRequiredProperty("hibernate.connection.username"));
		dataSource.setPassword(env.getRequiredProperty("hibernate.connection.password"));
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.put(AvailableSettings.DIALECT, env.getRequiredProperty("hibernate.dialect"));
		properties.put(AvailableSettings.SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
		properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));
		properties.put(AvailableSettings.HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
		properties.put(AvailableSettings.C3P0_MAX_SIZE, env.getRequiredProperty("hibernate.c3p0.max_size"));
		properties.put(AvailableSettings.C3P0_MIN_SIZE, env.getRequiredProperty("hibernate.c3p0.min_size"));
		properties.put(AvailableSettings.C3P0_MAX_STATEMENTS, env.getRequiredProperty("hibernate.c3p0.max_statements"));
		properties.put(AvailableSettings.C3P0_TIMEOUT, env.getRequiredProperty("hibernate.c3p0.timeout"));
		return properties;
	}
}
