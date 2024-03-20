package project.livestreaming.search.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.livestreaming.search.domain.ESVideo;
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
        ESVideo video = ESVideo
                .builder()
                .videoId(1)
                .title("title")
                .description("description")
                .build();
        //when
        searchService.sampleSave(video);

        //then
        int size = searchService.search("title").size();
        assertEquals(1, size);
    }
}