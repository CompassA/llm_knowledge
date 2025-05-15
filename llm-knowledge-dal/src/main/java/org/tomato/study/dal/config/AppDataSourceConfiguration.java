package org.tomato.study.dal.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepositoryConfiguration;
import org.apache.shardingsphere.spring.boot.datasource.DataSourceMapSetter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Tomato
 * Created on 2025.05.12
 */
@Configuration
public class AppDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("druid-datasource.llm-knowledge")
    public DruidDataSourceProperties llmKnowledgeConfig() {
        return new DruidDataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource llmKnowledgeDatasource(DruidDataSourceProperties llmKnowledgeConfig) {
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
        druidDataSource.setProxyFilters(createDruidDataSourceFilter());
        return druidDataSource;
    }

    @Bean
    public DataSource knowledgeFlowShardingDataSource(Environment environment,
                                                      ObjectProvider<List<RuleConfiguration>> rules) throws SQLException {
        Map<String, DataSource> dataSourceMap = DataSourceMapSetter.getDataSourceMap(environment);
        DataSource knowledgeFlowShardingDataSource = ShardingSphereDataSourceFactory.createDataSource(
                "knowledgeFlowShardingDataSource",
                new ModeConfiguration("Standalone", new StandalonePersistRepositoryConfiguration("JDBC", new Properties())),
                dataSourceMap,
                rules.getObject(),
                new Properties()
        );

        dataSourceMap.forEach((dataSourceName, dataSource) -> {
            if (dataSource instanceof DruidDataSource druidDataSource) {
                druidDataSource.setProxyFilters(createDruidDataSourceFilter());
            }
        });
        return knowledgeFlowShardingDataSource;
    }

    private List<Filter> createDruidDataSourceFilter() {
        return Lists.newArrayList(
                createDruidStatFilter(),
                createDruidSlf4jLog()
        );
    }

    private StatFilter createDruidStatFilter() {
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(1000L);
        filter.setLogSlowSql(true);
        return filter;
    }

    private Slf4jLogFilter createDruidSlf4jLog() {
        return new Slf4jLogFilter();
    }
}
