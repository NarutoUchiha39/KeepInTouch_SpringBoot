package com.Messaging.messaging.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SharedDataSourceConfig {
    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.url}")
    String url;


    @Bean(name = "sharedDataSource")
    public DataSource sharedDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .password(password)
                .username(username)
                .build();
    }
}
