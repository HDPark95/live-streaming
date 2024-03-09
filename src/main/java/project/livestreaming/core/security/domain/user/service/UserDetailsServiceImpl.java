package project.livestreaming.core.security.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.livestreaming.core.security.domain.user.model.User;
import project.livestreaming.core.security.domain.user.model.UserDetailsImpl;
import project.livestreaming.core.security.domain.user.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Entering in loadUserByUsername Method...");
//        User user = userRepository.findByUsername(username);
        User user = User.builder()
                .username("webmaster")
                .password(passwordEncoder.encode("1234"))
                .build();
        if(user == null){
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        log.info("User Authenticated Successfully..!!!");
        return new UserDetailsImpl(user);
    }
}
