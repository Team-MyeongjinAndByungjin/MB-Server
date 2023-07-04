package team.mb.mbserver.domain.coupon.model;

import lombok.*;
import team.mb.mbserver.domain.coupon.entity.Coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CouponDto {
        private Long id;
        private String imageUrl;
        private String name;
        private LocalDate expiredAt;

        public Coupon toEntity() {
            Coupon build = Coupon.builder()
                    .name(name)
                    .imageUrl(imageUrl)
                    .build();
            return build;
        }

        @Builder
        public CouponDto(Long id, String name, String imageUrl,LocalDate expiredAt) {
            this.id = id;
            this.expiredAt = expiredAt;
            this.imageUrl = imageUrl;
            this.name = name;
        }
    }

