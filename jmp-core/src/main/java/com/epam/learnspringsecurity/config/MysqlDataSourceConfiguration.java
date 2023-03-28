package com.epam.learnspringsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MySQL Datasource configuration
 */
@Configuration
public class MysqlDataSourceConfiguration {

    @Value("${mysql.url}")
    private String dataSourceUrl;
    @Value("${mysql.driver.class.name}")
    private String driverClassName;
    @Value("${mysql.password}")
    private String password;
    @Value("${mysql.username}")
    private String username;

    @Bean(name = "mysql_datasource")
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(dataSourceUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

}
