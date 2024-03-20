package project.livestreaming.core.entity;

import jakarta.persistence.*;
import lombok.*;
import project.livestreaming.core.entity.Role;
import project.livestreaming.core.entity.User;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "tb_user_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}