package project.livestreaming.video.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;
import project.livestreaming.video.domain.Video;

@Data
public class VideoDTO {
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private MultipartFile file;

    @JsonIgnore
    //@Schema(hidden = true)
    private String url;

    @JsonIgnore
    //@Schema(hidden = true)
    private String thumbnailUrl;

    private MultipartFile thumbnailFile;

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
