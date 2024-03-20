package project.livestreaming.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.livestreaming.search.domain.ESVideo;
import project.livestreaming.search.elasticsearch.VideoElasticRepository;
import project.livestreaming.video.domain.Video;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final VideoElasticRepository videoRepository;

    public List<Video> search(String keyword) {
        return videoRepository.findByTitleOrDescription(keyword, keyword);
    }

    public void sampleSave(ESVideo video) {
        videoRepository.save(video);
    }
}
