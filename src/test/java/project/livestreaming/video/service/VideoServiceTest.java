package project.livestreaming.video.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
}