package org.tomato.study.dal.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomato
 * Created on 2025.05.12
 */
@Configuration
@DependsOn({"shardingSphereDataSource"})
public class AppDataSourceConfiguration {

    @Bean
    public StatFilter druidStatFilter() {
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(10000L);
        filter.setLogSlowSql(true);
        return filter;
    }

    @Bean
    public Slf4jLogFilter druidSlf4jLog() {
        return new Slf4jLogFilter();
    }

    @Bean
    @Primary
    @ConfigurationProperties("druid-datasource.llm-knowledge")
    public DruidDataSourceProperties llmKnowledgeConfig() {
        return new DruidDataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource llmKnowledgeDatasource(DruidDataSourceProperties llmKnowledgeConfig,
                                             StatFilter druidStatFilter,
                                             Slf4jLogFilter druidSlf4jLog) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setName("llmKnowledgeDatasource");
        druidDataSource.setUrl(llmKnowledgeConfig.getUrl());
        druidDataSource.setDriverClassName(llmKnowledgeConfig.getDriverClassName());
        druidDataSource.setUsername(llmKnowledgeConfig.getUsername());
        druidDataSource.setPassword(llmKnowledgeConfig.getPassword());
        druidDataSource.setInitialSize(llmKnowledgeConfig.getInitialSize());
        druidDataSource.setMaxActive(llmKnowledgeConfig.getMaxActive());
        druidDataSource.setMinIdle(llmKnowledgeConfig.getMinIdle());
        druidDataSource.setMaxWait(llmKnowledgeConfig.getMaxWait());
        druidDataSource.setMaxOpenPreparedStatements(llmKnowledgeConfig.getMaxOpenPreparedStatements());
        druidDataSource.setTestWhileIdle(llmKnowledgeConfig.getTestWhileIdle());
        druidDataSource.setTimeBetweenEvictionRunsMillis(llmKnowledgeConfig.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setTestOnBorrow(llmKnowledgeConfig.getTestOnBorrow());
        druidDataSource.setTestOnReturn(llmKnowledgeConfig.getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(llmKnowledgeConfig.getPoolPreparedStatements());
        druidDataSource.setProxyFilters(Lists.newArrayList(druidStatFilter, druidSlf4jLog));
        return druidDataSource;
    }

    @Bean
    public Map<String, DataSource> llmKnowledgeDataSource(ShardingSphereDataSource shardingSphereDataSource,
                                                          StatFilter druidStatFilter,
                                                          Slf4jLogFilter druidSlf4jLog) {
        // fixme TOMATO druid配置
        return new HashMap<>();
    }
}
