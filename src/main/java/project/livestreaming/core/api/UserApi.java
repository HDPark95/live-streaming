package project.livestreaming.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.livestreaming.core.dto.JoinDTO;
import project.livestreaming.core.dto.LoginDTO;
import project.livestreaming.core.dto.JwtResponseDTO;
import project.livestreaming.core.dto.UserResponseDTO;
import project.livestreaming.core.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApi {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserResponseDTO> join(@Validated @RequestBody JoinDTO joinDTO) {

        return ResponseEntity.ok(UserResponseDTO.mapToDTO(userService.join(joinDTO)));
    }
}

