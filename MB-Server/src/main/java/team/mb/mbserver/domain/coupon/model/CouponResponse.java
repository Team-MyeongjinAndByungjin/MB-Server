package team.mb.mbserver.domain.coupon.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CouponResponse {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private LocalDate expiredAt;
}

