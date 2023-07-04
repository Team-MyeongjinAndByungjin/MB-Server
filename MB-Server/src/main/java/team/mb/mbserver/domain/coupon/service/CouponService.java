package team.mb.mbserver.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.mb.mbserver.domain.coupon.entity.Coupon;
import team.mb.mbserver.domain.coupon.entity.CouponRepository;
import team.mb.mbserver.domain.coupon.model.CouponResponse;
import team.mb.mbserver.domain.coupon.model.CreateCouponRequest;
import team.mb.mbserver.domain.user.entity.User;
import team.mb.mbserver.domain.user.entity.UserRepository;
import team.mb.mbserver.global.error.BusinessException;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveCoupon(CreateCouponRequest request) {

        User user = getCurrentUser();

        Coupon coupon = Coupon.builder()
                .name(request.getName())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .expiredAt(request.getExpiredAt())
                .user(user)
                .build();

        couponRepository.save(coupon);
    }

    @Transactional
    public void deleteCoupon(Long couponId) {
        User user = getCurrentUser();

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BusinessException(404, "존재하지 않습니다."));

        if (user != coupon.getUser()) {
            throw new BusinessException(403, "Invalid Writer");
        }

        couponRepository.delete(coupon);
    }

    public List<CouponResponse> getCoupons() {
        User user = getCurrentUser();

        List<Coupon> coupons = user.getCoupons();

        return coupons.stream()
                .map(coupon -> CouponResponse
                        .builder()
                        .id(coupon.getId())
                        .name(coupon.getName())
                        .price(coupon.getPrice())
                        .imageUrl(coupon.getImageUrl())
                        .expiredAt(coupon.getExpiredAt())
                        .build())
                .toList();
    }

    @Transactional
    public void giveCoupon(Long couponId, String accountId) {
        User currentUser = getCurrentUser();

        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new BusinessException(404, "User Not Found"));

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BusinessException(404, "Coupon Not Found"));
        coupon.giveCoupon(currentUser, user);
    }

    private User getCurrentUser() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new BusinessException(404, "User Not Found"));
    }
}
