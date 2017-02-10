package com.saniaky.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author saniaky
 * @since 2/10/17
 */
@Configuration
public class ElasticSearchConfig {

    private static Logger LOG = LoggerFactory.getLogger(ElasticSearchConfig.class);

    @Value("${elasticsearch.home:/usr/local/Cellar/elasticsearch/5.2.0}")
    private String elasticSearchHome;

    @Bean
    public Client client() {
        try {
            Path tmpDir = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "elasticsearch_data");
            LOG.debug(tmpDir.toAbsolutePath().toString());

            Settings.Builder elasticsearchSettings = Settings.settingsBuilder()
                    .put("http.enabled", "false")
                    .put("path.data", tmpDir.toAbsolutePath().toString())
                    .put("path.home", elasticSearchHome);

            return new NodeBuilder()
                    .local(true)
                    .settings(elasticsearchSettings)
                    .node()
                    .client();

        } catch (final IOException ioex) {
            LOG.error("Cannot create temp dir", ioex);
            throw new RuntimeException();
        }
    }

}
