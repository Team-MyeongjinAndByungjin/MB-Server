package team.mb.mbserver.domain.coupon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateCouponRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String from;

    @NotBlank
    private String imageUrl;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredAt;
}
