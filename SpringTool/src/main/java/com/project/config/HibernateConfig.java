package com.project.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:hibernate.properties")
public class HibernateConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("hibernate.connection.driver_class"));
		dataSource.setUrl(env.getRequiredProperty("hibernate.connection.url"));
		dataSource.setUsername(env.getRequiredProperty("hibernate.connection.username"));
		dataSource.setPassword(env.getRequiredProperty("hibernate.connection.password"));
		return dataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.project.model" });
		sessionFactory.setHibernateProperties(getHibernateProperties());
		return sessionFactory;
	}

	private Properties getHibernateProperties() {
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

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}
}
