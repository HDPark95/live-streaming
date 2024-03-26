package project.livestreaming.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.livestreaming.core.domain.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
