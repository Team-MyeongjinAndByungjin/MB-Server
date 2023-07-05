package team.mb.mbserver.domain.coupon.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CouponResponse {
    private final Long id;
    private final String name;
    private final String from;
    private final String imageUrl;
    private final LocalDate createdAt;
    private final LocalDate expiredAt;
}

