package team.mb.mbserver.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.mb.mbserver.domain.coupon.entity.Coupon;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String password;

    private String deviceToken;

    @OneToMany(mappedBy = "user")
    private List<Coupon> coupons = new ArrayList<>();

    public void settingDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
