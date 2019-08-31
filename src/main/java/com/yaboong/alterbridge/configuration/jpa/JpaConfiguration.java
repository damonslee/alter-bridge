package com.yaboong.alterbridge.configuration.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaboong.alterbridge.application.common.auditing.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by yaboong on 2019-08-31
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    /** Jpa Auditing */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    /** Querydsl */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
