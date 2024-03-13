package project.livestreaming.video.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VideoServiceTest {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private VideoService videoService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String originalName = "testVideo.mp4";
    private String contentType = "video/mp4";
    private byte[] content = "test content".getBytes();
    private MultipartFile file;

    @BeforeEach
    public void setUp() {
        file = new MockMultipartFile("file", originalName, contentType, content);
    }

    @AfterEach
    public void tearDown() {
        // Delete the file after test
        s3Client.deleteObject(bucket, originalName);
    }

    @Test
    public void testUploadAndDeleteVideo() throws Exception {
        // Act
        String returnedUrl = videoService.uploadVideo(file);

        // Assert
        assertNotNull(returnedUrl);
        assertTrue(s3Client.doesObjectExist(bucket, originalName));

        // Cleanup is handled in tearDown
    }

    @Test
    @DisplayName("잘못된 파일 형식 업로드 시 예외 발생 테스트")
    public void testUploadVideoThrowsExceptionForInvalidFile() {
        // Arrange: 잘못된 파일 형식 준비 (텍스트 파일)
        MultipartFile invalidFile = new MockMultipartFile("file", "invalidVideo.txt", "text/plain", "invalid content".getBytes());

        // Act & Assert: 업로드 시 예외 발생 확인
        assertThrows(RuntimeException.class, () -> {
            videoService.uploadVideo(invalidFile);
        });
    }

    @Test
    @DisplayName("내용이 비어 있는 파일 업로드 시 예외 발생 테스트")
    public void testUploadVideoWithEmptyContent() {
        // Arrange: 내용이 비어 있는 파일 준비
        MultipartFile emptyFile = new MockMultipartFile("file", "emptyVideo.mp4", contentType, new byte[0]);

        // Act & Assert: 업로드 시 예외 발생 확인
        assertThrows(RuntimeException.class, () -> {
            videoService.uploadVideo(emptyFile);
        });
    }

    @Test
    @DisplayName("null 파일 업로드 시 예외 발생 테스트")
    public void testUploadVideoWithNullFile() {
        // Act & Assert: null 파일 업로드 시 예외 발생 확인
        assertThrows(NullPointerException.class, () -> {
            videoService.uploadVideo(null);
        });
    }

    @Test
    @DisplayName("썸네일 생성 테스트")
    public void testCreateThumbnail() throws IOException, InterruptedException {
        // 임시 파일 생성을 위한 준비
        byte[] content = Files.readAllBytes(Path.of("src/test/resources/test.mp4"));
        MultipartFile mockFile = new MockMultipartFile("test.mp4", "test.mp4", "video/mp4", content);

        // 썸네일 생성 테스트 실행
        File thumbnail = videoService.createThumbnail(mockFile);

        // 검증: 생성된 썸네일이 null이 아니어야 함
        assertNotNull(thumbnail);
        // 검증: 생성된 썸네일 파일이 실제로 존재해야 함
        assertTrue(thumbnail.exists());

        // 임시 파일 정리
        Files.deleteIfExists(thumbnail.toPath()); // 테스트 후 썸네일 파일 삭제
    }
}