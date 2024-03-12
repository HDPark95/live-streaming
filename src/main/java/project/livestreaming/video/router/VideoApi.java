package project.livestreaming.video.router;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.livestreaming.video.dto.VideoDTO;
import project.livestreaming.video.service.VideoService;

import java.io.File;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoApi {

    private final VideoService videoService;
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> createVideo(@ModelAttribute VideoDTO videoDTO) throws Exception{
        videoService.createVideo(videoDTO);
        return ResponseEntity.ok().build();
    }
    /* 섭네일 만들기 */
    @PostMapping(consumes = "multipart/form-data", value = "/thumbnail")
    public ResponseEntity<File> createThumbnail(@RequestParam("file") MultipartFile file) throws Exception{
        return ResponseEntity.ok(videoService.createThumbnail(file));
    }
}
