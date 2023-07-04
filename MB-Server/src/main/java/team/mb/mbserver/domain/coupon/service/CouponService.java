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

import javax.persistence.EntityNotFoundException;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다."));

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
                        .imageUrl(coupon.getImageUrl())
                        .expiredAt(coupon.getExpiredAt())
                        .build())
                .toList();
    }

    private User getCurrentUser() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException());
    }
}
