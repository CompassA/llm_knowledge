package org.tomato.study.dal.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomato
 * Created on 2025.05.12
 */
@Configuration
@EnableConfigurationProperties(LlmKnowledgeDataSourceProperties.class)
public class LlmKnowledgeDataSourceConfiguration {

    @Resource
    private LlmKnowledgeDataSourceProperties properties;

    @Bean
    public StatFilter druidStatFilter() {
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(10000L);
        filter.setLogSlowSql(true);
        return filter;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource singleDataSource(StatFilter druidStatFilter) {
        return buildDruidDataSource(
                "singleDataSource",
                properties.getSingleUrl(),
                Lists.newArrayList(druidStatFilter)
        );
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public ShardingSources shardingSources(StatFilter druidStatFilter) {
        List<DruidDataSource> druidDataSources = new ArrayList<>();
        List<String> shardingUrls = properties.getShardingUrls();
        for (int i = 0; i < shardingUrls.size(); i++) {
            DruidDataSource druidDataSource = buildDruidDataSource(
                    "shardingDataSource-" + i,
                    shardingUrls.get(i),
                    Lists.newArrayList(druidStatFilter)
            );

            druidDataSources.add(druidDataSource);
        }
        return new ShardingSources(druidDataSources);
    }

    public DataSource shardingJDBCDataSource(ShardingSources shardingSources) {
        // fixme TOMATO todo sharing-jdbc数据源
        return null;
    }

    private DruidDataSource buildDruidDataSource(String name,
                                                 String url,
                                                 List<Filter> filters) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setName(name);
        druidDataSource.setUrl(url);
        druidDataSource.setProxyFilters(filters);

        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUsername(properties.getUser());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setInitialSize(properties.getDruidPoolInitialSize());
        druidDataSource.setMaxActive(properties.getDruidPoolMaxSize());
        druidDataSource.setMinIdle(properties.getDruidPoolMinSize());
        druidDataSource.setMaxWait(properties.getDruidMaxWait());
        druidDataSource.setMaxOpenPreparedStatements(properties.getDruidPoolMaxSize());

        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);

        return druidDataSource;
    }
}
