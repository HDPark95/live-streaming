package project.livestreaming.core.dto;

import lombok.Data;
import project.livestreaming.core.code.RoleName;
import project.livestreaming.core.entity.User;

import java.util.List;

@Data
public class UserResponseDTO {

    private String username;

    private String email;

    private List<RoleName> roles;

    public static UserResponseDTO mapToDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.username = user.getUsername();
        userResponseDTO.email = user.getEmail();
        userResponseDTO.roles = user.getUserRoles().stream().map(userRole -> userRole.getRole().getName()).toList();
        return userResponseDTO;
    }
}