package project.livestreaming.search.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.livestreaming.video.domain.Video;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Test
    void search() {
    }

    @Test
    void sampleSave() {
        //given
        Video video = Video.builder()
                .title("title")
                .description("description")
                .url("url")
                .thumbnailUrl("thumbnailUrl")
                .build();
        //when
        searchService.sampleSave(video);

        //then
        int size = searchService.search("title").size();
        assertEquals(1, size);
    }
}