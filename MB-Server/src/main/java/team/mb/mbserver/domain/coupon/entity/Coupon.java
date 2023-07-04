package team.mb.mbserver.domain.coupon.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.mb.mbserver.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate expiredAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Coupon(Long id, String name, String imageUrl, LocalDate expiredAt, User user) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.expiredAt = expiredAt;
        this.user = user;
    }
}
