package team.mb.mbserver.domain.coupon.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.mb.mbserver.domain.coupon.model.CouponResponse;
import team.mb.mbserver.domain.coupon.model.CreateCouponRequest;
import team.mb.mbserver.domain.coupon.service.CouponService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/coupons")
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public void save(@RequestBody @Valid CreateCouponRequest request) {
        couponService.saveCoupon(request);
    }

    @DeleteMapping("/{coupon-id}")
    public void delete(@PathVariable("coupon-id") Long couponId) {
        couponService.deleteCoupon(couponId);
    }

    @GetMapping
    public List<CouponResponse> getCoupons() {
        return couponService.getCoupons();
    }


}
