package org.fifa.api.fifacentral.dao;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public DataSource dataSource() {
        org.springframework.jdbc.datasource.DriverManagerDataSource dataSource =
                new org.springframework.jdbc.datasource.DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dotenv.get("CENTRAL_DB_URL",
                "jdbc:postgresql://localhost:5432/fifa_central"));
        dataSource.setUsername(dotenv.get("CENTRAL_DB_USER", "postgres"));
        dataSource.setPassword(dotenv.get("CENTRAL_DB_PASSWORD", "postgres"));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}