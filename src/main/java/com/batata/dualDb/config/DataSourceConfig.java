package com.batata.dualDb.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.h2.datasource")
    public DataSource h2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.mysql.datasource")
    public DataSource mysqlDataSource() {
        return  DataSourceBuilder.create().build();
    }

    @Bean
    public String migrateFlyway() {
        Flyway flyway = new Flyway();
        //source 2
        flyway.setLocations("classpath:/db/migration/mysql/");
        flyway.setDataSource(mysqlDataSource());
        flyway.migrate();

        return "meh";
    }
}
