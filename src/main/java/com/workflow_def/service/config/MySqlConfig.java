package com.workflow_def.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MySqlConfig {

    @Bean
    public HikariConfig getConnection() {

        HikariConfig dbConfig = new HikariConfig();

        Map<String, String> dbData = System.getenv();

        dbConfig.setJdbcUrl(dbData.get("URL"));
        dbConfig.setUsername(dbData.get("USERNAME"));
        dbConfig.setPassword(dbData.get("PASSWORD"));
        dbConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return new HikariDataSource(dbConfig);

    }
}