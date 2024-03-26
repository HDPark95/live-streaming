package project.livestreaming.core.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.livestreaming.core.code.RoleName;
import project.livestreaming.core.domain.User;
import project.livestreaming.core.dto.JoinDTO;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("회원가입_정상")
    @Test
    void 회원가입_정상() {
        // given
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setEmail("test@test.com");
        joinDTO.setUsername("test1");
        joinDTO.setPassword("1234");

        // when
        User newUser = userService.join(joinDTO);

        // then
        assertNotNull(newUser,"newUser should not be null");
        assertTrue(passwordEncoder.matches("1234", newUser.getPassword()));
        assertTrue(
                newUser.getUserRoles().stream()
                .filter(userRole -> userRole.getRole().getName() == RoleName.USER)
                .findAny()
                .isPresent()
        );

    }

}