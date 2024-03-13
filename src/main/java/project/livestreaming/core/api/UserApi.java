package project.livestreaming.core.api;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import project.livestreaming.core.service.JwtService;
import project.livestreaming.core.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/join")
    public ResponseEntity<UserResponseDTO> join(@Validated @RequestBody JoinDTO joinDTO) {
        return ResponseEntity.ok(UserResponseDTO.mapToDTO(userService.join(joinDTO)));
    }

    /**
     * Refresh 재발급
     */
    @PostMapping("/refresh")
    public ResponseEntity<Void> reissue(HttpServletRequest request, HttpServletResponse response) {
        jwtService.refreshReissue(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

