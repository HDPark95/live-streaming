package project.livestreaming.video.router;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.livestreaming.video.dto.VideoDTO;
import project.livestreaming.video.dto.VideoMetaDTO;
import project.livestreaming.video.service.VideoService;

import java.io.File;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoApi {

    private final VideoService videoService;

    /* 비디오 업로드 */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> createVideo(@ModelAttribute VideoDTO videoDTO) throws Exception{
        videoService.createVideo(videoDTO);
        return ResponseEntity.ok().build();
    }

    /* 섬네일 만들기 */
    @PostMapping(consumes = "multipart/form-data", value = "/thumbnail")
    public ResponseEntity<File> createThumbnail(@RequestParam("file") MultipartFile file) throws Exception{
        return ResponseEntity.ok(videoService.createThumbnail(file));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVideo(@RequestParam Long videoId) {
        videoService.deleteVideo(videoId);
        return ResponseEntity.ok().build();
    }

    /* 영상 메타정보 수정 */
    @PutMapping
    public ResponseEntity<Void> updateVideo(@RequestParam Long videoId, @RequestBody VideoMetaDTO videoDTO) throws Exception {
        videoService.updateVideo(videoId, videoDTO);
        return ResponseEntity.ok().build();
    }
}
