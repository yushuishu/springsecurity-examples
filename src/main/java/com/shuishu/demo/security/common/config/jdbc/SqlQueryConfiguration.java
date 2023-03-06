package com.shuishu.demo.security.common.config.jdbc;


import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 19:59
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：QueryDsl配置
 */
@Configuration
public class SqlQueryConfiguration {
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DriverManagerDataSource dataSource() {
        return DataSourceBuilder.create().type(DriverManagerDataSource.class).build();
    }

    @Bean
    public com.querydsl.sql.Configuration queryDslConfiguration() {
        //change to your Templates
        SQLTemplates templates = PostgreSQLTemplates.builder().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);

        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        return configuration;
    }

    @Bean
    public SQLQueryFactory sqlQueryFactory() {
        return new SQLQueryFactory(queryDslConfiguration(), new SpringConnectionProvider(dataSource()));
    }
}
