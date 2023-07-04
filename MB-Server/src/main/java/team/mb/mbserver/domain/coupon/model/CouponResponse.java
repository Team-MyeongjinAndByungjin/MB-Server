package team.mb.mbserver.domain.coupon.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CouponResponse {
    private Long id;
    private String imageUrl;
    private String name;
    private LocalDate expiredAt;
}

