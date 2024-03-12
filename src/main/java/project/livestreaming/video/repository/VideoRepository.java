package project.livestreaming.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.livestreaming.video.domain.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
