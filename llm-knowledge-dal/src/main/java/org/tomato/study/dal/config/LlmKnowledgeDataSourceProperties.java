package org.tomato.study.dal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Tomato
 * Created on 2025.05.12
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "llm-knowledge.datasource")
public class LlmKnowledgeDataSourceProperties {

    private String driverClassName;
    private String user;
    private String password;
    private String singleUrl;
    private List<String> shardingUrls;
    private Integer druidPoolInitialSize;
    private Integer druidPoolMinSize;
    private Integer druidPoolMaxSize;
    private Long druidMaxWait;
}
