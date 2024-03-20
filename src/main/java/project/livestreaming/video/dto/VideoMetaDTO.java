package project.livestreaming.video.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VideoMetaDTO {
    private String title;
    private String description;
    private String url;
    private MultipartFile thumbnailFile;

}
