package project.livestreaming.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_refresh")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "refresh")
    private String refresh;

    @Column(name = "expiration")
    private String expiration;

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeRefresh(String refresh) {
        this.refresh = refresh;
    }

    public void changeExpiration(String string) {
        this.expiration = string;
    }
}
