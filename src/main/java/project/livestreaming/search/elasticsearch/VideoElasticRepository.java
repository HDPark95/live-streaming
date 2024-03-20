package project.livestreaming.search.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import project.livestreaming.video.domain.Video;

import java.util.List;

public interface VideoElasticRepository extends ElasticsearchRepository<Video, String> {
    List<Video> findByTitleOrDescription(String keyword, String keyword1);
}
