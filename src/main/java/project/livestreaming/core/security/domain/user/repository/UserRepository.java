package project.livestreaming.core.security.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.livestreaming.core.security.domain.user.model.User;

public class UserRepository implements JpaRepository<User, Long> {
}
