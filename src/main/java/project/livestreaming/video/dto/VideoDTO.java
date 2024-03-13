package project.livestreaming.video.dto;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.livestreaming.video.domain.Video;

@Data
public class VideoDTO {
    private String title;
    private String description;
    private String url;
    private MultipartFile file;
    private String thumbnailUrl;
    public Video toEntity() {
        if (Strings.isNullOrEmpty(url)){
            throw new IllegalArgumentException("url is empty");
        }
        return Video.builder()
                .title(title)
                .description(description)
                .url(url)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
