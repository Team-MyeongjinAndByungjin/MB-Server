package team.mb.mbserver.domain.coupon.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.mb.mbserver.domain.user.entity.User;
import team.mb.mbserver.global.error.BusinessException;

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
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate created_at;

    @Column(nullable = false)
    private LocalDate expiredAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Coupon(String name, String imageUrl, int price, LocalDate expiredAt, User user) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.expiredAt = expiredAt;
        this.created_at = LocalDate.now();
        this.user = user;
    }

    public void giveCoupon(User currentUser, User user) {
        if (this.user != currentUser) {
            throw new BusinessException(401, "쿠폰 주인이 아닙니다.");
        }
        this.user = user;
    }
}
