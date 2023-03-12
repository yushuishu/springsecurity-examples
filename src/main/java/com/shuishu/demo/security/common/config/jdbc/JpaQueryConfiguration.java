package com.shuishu.demo.security.common.config.jdbc;


import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shuishu.demo.security.common.config.id.IdGenerate;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 19:58
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Configuration
public class JpaQueryConfiguration {
    /**
     * id 默认生成器
     *
     * @return idGenerate
     */
    @Bean
    public IdGenerate idGenerate() {
        return new IdGenerate(0, 0);
    }

    @Resource
    @PersistenceContext
    public EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
    }
}
