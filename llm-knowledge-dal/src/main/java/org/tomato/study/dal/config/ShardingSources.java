package org.tomato.study.dal.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Tomato
 * Created on 2025.05.12
 */
@RequiredArgsConstructor
public class ShardingSources {

    private final List<DruidDataSource> druidDataSources;

    public void init() throws SQLException {
        for (DruidDataSource druidDataSource : druidDataSources) {
            druidDataSource.init();
        }
    }

    public void close() {
        for (DruidDataSource druidDataSource : druidDataSources) {
            druidDataSource.close();
        }
    }
}
