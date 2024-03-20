package project.livestreaming.search.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import project.livestreaming.search.domain.ESVideo;

import java.util.List;

public interface VideoElasticRepository extends ElasticsearchRepository<ESVideo, String> {
    List<ESVideo> findByTitleOrDescription(String keyword, String keyword1);
}
