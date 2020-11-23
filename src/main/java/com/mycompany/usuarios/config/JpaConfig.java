/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usuarios.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author hescu
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.mycompany.usuarios.repo")
public class JpaConfig {

    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        if (configProperties.getType().equals("persisted")) {
            vendorAdapter.setDatabase(Database.MYSQL);
        } else {
            vendorAdapter.setDatabase(Database.H2);
        }

        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.mycompany.usuarios.entity");
        em.setJpaVendorAdapter(vendorAdapter);;

        return em;
    }

    @Bean
    public DataSource dataSource() {
        if (configProperties.getType().equals("persisted")) {
            Datasource config = configProperties.getMysql();
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName(config.getDriverClassName());
            dataSourceBuilder.url(config.getUrl());
            dataSourceBuilder.username(config.getUsername());
            dataSourceBuilder.password(config.getPassword());
            return dataSourceBuilder.build();
        } else {
            Datasource config = configProperties.getH2();
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName(config.getDriverClassName());
            dataSourceBuilder.url(config.getUrl());
            dataSourceBuilder.username(config.getUsername());
            dataSourceBuilder.password(config.getPassword());
            return dataSourceBuilder.build();
        }

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

}
