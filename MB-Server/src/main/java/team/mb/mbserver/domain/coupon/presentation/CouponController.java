package team.mb.mbserver.domain.coupon.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.mb.mbserver.domain.coupon.model.CouponDto;
import team.mb.mbserver.domain.coupon.service.CouponService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/coupons")
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public Long save(@RequestBody CouponDto couponDto) {
        return couponService.save(couponDto);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        couponService.delete(id);
    }

    @GetMapping
    public List<CouponDto> getCoupons() {
        return couponService.getCoupons();
    }


}
