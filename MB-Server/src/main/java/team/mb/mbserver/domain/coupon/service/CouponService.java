package team.mb.mbserver.domain.coupon.service;

import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.mb.mbserver.domain.coupon.entity.Coupon;
import team.mb.mbserver.domain.coupon.entity.CouponRepository;
import team.mb.mbserver.domain.coupon.model.CouponResponse;
import team.mb.mbserver.domain.coupon.model.CreateCouponRequest;
import team.mb.mbserver.domain.user.entity.User;
import team.mb.mbserver.domain.user.entity.UserJpaRepository;
import team.mb.mbserver.global.error.BusinessException;
import team.mb.mbserver.infrastructure.fcm.FCMFacade;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserJpaRepository userJpaRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final FCMFacade fcmFacade;

    @Transactional
    public void saveCoupon(CreateCouponRequest request) {

        User user = getCurrentUser();

        Coupon coupon = Coupon.builder()
                .name(request.getName())
                .from(request.getFrom())
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
                .orElseThrow(() -> new BusinessException(404, "쿠폰이 존재하지 않습니다."));

        if (user != coupon.getUser()) {
            throw new BusinessException(403, "쿠폰 주인이 아닙니다.");
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
                        .from(coupon.getFromUser())
                        .imageUrl(coupon.getImageUrl())
                        .createdAt(coupon.getCreatedAt())
                        .expiredAt(coupon.getExpiredAt())
                        .build())
                .toList();
    }

    @Transactional
    public void giveCoupon(Long couponId, String accountId) {
        User currentUser = getCurrentUser();

        User user = userJpaRepository.findByAccountId(accountId)
                .orElseThrow(() -> new BusinessException(404, "유저를 찾지 못했습니다."));

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BusinessException(404, "쿠폰을 찾지 못했습니다."));
        coupon.giveCoupon(currentUser, user);

        fcmFacade.notificationForCoupon(currentUser.getAccountId(), user.getDeviceToken(), coupon.getImageUrl());
    }

    private User getCurrentUser() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userJpaRepository.findByAccountId(accountId)
                .orElseThrow(() -> new BusinessException(404, "유저를 찾지 못했습니다."));
    }
}
