package project.livestreaming.video.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.livestreaming.video.dto.VideoDTO;
import project.livestreaming.video.repository.VideoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final AmazonS3 s3Client;

    private final VideoRepository videoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadVideo(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("file is empty");
        }
        //포맷 비디오 검사
        if (!file.getContentType().startsWith("video")) {
            throw new RuntimeException("file is not video");
        }

        String originalName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        s3Client.putObject(bucket, originalName, file.getInputStream(), metadata);
        return s3Client.getUrl(bucket, originalName).toString();
    }
    public String uploadThumbnail(File file) throws IOException {
        if (file.length() == 0) {
            throw new RuntimeException("file is empty");
        }
        String originalName = file.getName();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());
        metadata.setContentType("image/jpeg");

        s3Client.putObject(bucket, originalName, file);
        return s3Client.getUrl(bucket, originalName).toString();
    }

    public File createThumbnail(MultipartFile inputFile) throws IOException, InterruptedException {
        // 입력 파일을 임시 디렉토리에 저장합니다.
        Path tempDir = Files.createTempDirectory("tempFiles");
        String originalFileName = inputFile.getOriginalFilename();
        Path inputFilePath = tempDir.resolve(originalFileName);
        File inputFileFile = inputFilePath.toFile();
        inputFile.transferTo(inputFileFile);

        // 생성될 썸네일 파일의 경로를 정의합니다.
        String thumbnailFileName = "thumbnail-" + originalFileName + ".jpg";
        Path thumbnailFilePath = tempDir.resolve(thumbnailFileName);
        File thumbnailFile = thumbnailFilePath.toFile();

        // FFmpeg를 사용하여 썸네일 생성
        String cmd = String.format(
                "ffmpeg -i \"%s\" -ss 00:00:01 -frames:v 1 \"%s\"",
                inputFileFile.getAbsolutePath(),
                thumbnailFile.getAbsolutePath());

        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor(); // FFmpeg 프로세스 완료 대기

        // 임시 디렉토리에 저장된 파일을 삭제합니다.
        Files.delete(inputFilePath);

        // 썸네일 파일 반환
        return thumbnailFile;
    }

    public void createVideo(VideoDTO videoDTO) throws IOException, InterruptedException {
        //비디오 저장
        String url = uploadVideo(videoDTO.getFile());
        videoDTO.setUrl(url);
        videoDTO.setThumbnailUrl(uploadThumbnail(createThumbnail(videoDTO.getFile())));
        videoRepository.save(videoDTO.toEntity());
    }

}
