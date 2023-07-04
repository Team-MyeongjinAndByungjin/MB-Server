package team.mb.mbserver.domain.coupon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateCouponRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private LocalDate expiredAt;
}
