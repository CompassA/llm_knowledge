package org.tomato.study.dal.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tomato
 * Created on 2025.05.12
 */
@Getter
@Setter
public class DruidDataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Long maxWait;
    private Long timeBetweenEvictionRunsMillis;
    private Boolean alive;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean poolPreparedStatements;
    private Integer maxOpenPreparedStatements;
}
