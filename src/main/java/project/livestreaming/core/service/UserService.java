package project.livestreaming.core.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.livestreaming.core.code.RoleName;
import project.livestreaming.core.dto.JoinDTO;
import project.livestreaming.core.entity.Role;
import project.livestreaming.core.entity.User;
import project.livestreaming.core.entity.UserRole;
import project.livestreaming.core.repository.RoleRepository;
import project.livestreaming.core.repository.UserRepository;
import project.livestreaming.core.repository.UserRoleRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initRoleAndUser() {
        Role role = roleRepository.findByName(RoleName.ADMIN);
        if(role == null) {
            role = Role.builder()
                    .name(RoleName.ADMIN)
                    .build();
            roleRepository.save(role);
        }

        Role uRole = roleRepository.findByName(RoleName.USER);
        if(uRole == null) {
            uRole = Role.builder()
                    .name(RoleName.USER)
                    .build();
            roleRepository.save(uRole);
        }

        User user = userRepository.findByUsername("webmaster");
        if(user == null) {
            user = User.builder()
                    .username("webmaster")
                    .password(passwordEncoder.encode("1234"))
                    .email("wnekfa1004@naver.com")
                    .build();
            User webmaster = userRepository.save(user);

            UserRole userRole = UserRole.builder()
                    .user(webmaster)
                    .role(role)
                    .build();
            userRoleRepository.save(userRole);
        }
    }

    @Transactional
    public User join(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist) {
            throw new IllegalArgumentException("username already exists..!!");
        }

        User newUser = userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build());

        Role role = roleRepository.findByName(RoleName.USER);

        UserRole newUserRole = userRoleRepository.save(UserRole.builder()
                .user(newUser)
                .role(role)
                .build());

        newUser.getUserRoles().add(newUserRole);

        return newUser;
    }
}
