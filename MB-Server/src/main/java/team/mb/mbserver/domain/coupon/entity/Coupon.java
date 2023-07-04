package team.mb.mbserver.domain.coupon.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.mb.mbserver.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate expiredAt;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Builder
    public Coupon(Long id, String name,String imageUrl) {
        this.id = id;
        this.name= name;
        this.imageUrl = imageUrl;
    }
}
