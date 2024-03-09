package project.livestreaming.core.security.domain.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import project.livestreaming.core.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserRole> userRoles = new HashSet<>();

    @Builder.Default
    @Comment("계정 만료 여부")
    @Column(name = "account_non_expired")
    boolean accountNonExpired = true;

    @Builder.Default
    @Comment("계정 잠금 여부")
    @Column(name = "account_non_locked")
    boolean accountNonLocked = true;

    @Builder.Default
    @Comment("비밀번호 만료 여부")
    @Column(name = "credentials_non_expired")
    boolean credentialsNonExpired = true;

    @Builder.Default
    @Comment("계정 활성화 여부")
    @Column(name = "enabled")
    boolean enabled = true;
}

