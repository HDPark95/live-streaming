package project.livestreaming.video.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.livestreaming.core.entity.BaseEntity;

@Entity
@Table(name = "tb_video")
@NoArgsConstructor
public class Video extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @Column(name = "video_title")
    private String title;

    @Column(name = "video_description")
    private String description;

    @Column(name = "video_url")
    private String url;

    @Builder
    public Video(Long id, String title, String description, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
    }

}
