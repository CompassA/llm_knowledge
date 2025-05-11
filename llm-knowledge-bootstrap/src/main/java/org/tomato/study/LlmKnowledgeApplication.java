package org.tomato.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

/**
 * @author Tomato
 * Created on 2025.05.11
 */
@SpringBootApplication(
        exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class }
)
public class LlmKnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LlmKnowledgeApplication.class);
    }
}
