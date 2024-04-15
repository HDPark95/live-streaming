package project.livestreaming;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableBatchProcessing
@EnableJpaRepositories(basePackages = {"project.livestreaming.video.repository", "project.livestreaming.core.repository"})
@EnableElasticsearchRepositories(basePackages = "project.livestreaming.search.elasticsearch")
@SpringBootApplication
public class LiveStreamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveStreamingApplication.class, args);
    }

}
