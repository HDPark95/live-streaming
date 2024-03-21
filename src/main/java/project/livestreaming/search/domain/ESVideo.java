package project.livestreaming.search.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "video")
@NoArgsConstructor
public class ESVideo {
    @Id
    private String id;
    private long videoId;
    private String title;
    private String description;

    @Builder
    public ESVideo(long videoId, String title, String description) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
    }
}
