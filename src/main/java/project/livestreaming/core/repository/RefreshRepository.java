package project.livestreaming.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.livestreaming.core.entity.Refresh;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Boolean existsByRefresh(String refresh);
    void deleteByRefresh(String refresh);


}
