package com.batata.dualDb.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
class DataSourceConfig {

    @Primary
    @Bean(name = "h2Datasource")
    @ConfigurationProperties("spring.h2.datasource")
    public DataSource h2DataSource() {
        return DataSourceBuilder.create()
                                .build();
    }

//    @Bean(name = "mysqlDatasource")
//    @ConfigurationProperties(prefix = "spring.mysql.datasource")
    public DataSource mysqlDataSource() {
        return  DataSourceBuilder.create().build();
    }

//    @Bean(name = "mysqlEntityManager")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(mysqlDataSource())
                      .persistenceUnit("default")
                      .build();
    }

//    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * This will run scripts for the second database since flyway just do automatically for @primary one
     * PostConstruction throws a error, the object is not ready :(
     */
   // @Bean
    public String migrateFlyway() {
        Flyway flyway = new Flyway();
        flyway.setLocations("classpath:/db/migration/mysql/");
        flyway.setDataSource(mysqlDataSource());
        flyway.migrate();

        return "done";
    }
}
