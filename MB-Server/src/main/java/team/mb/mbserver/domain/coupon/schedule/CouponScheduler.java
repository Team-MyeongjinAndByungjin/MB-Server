package team.mb.mbserver.domain.coupon.schedule;

import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.mb.mbserver.domain.coupon.entity.CouponRepository;
import team.mb.mbserver.domain.user.entity.User;
import team.mb.mbserver.domain.user.entity.UserRepository;
import team.mb.mbserver.infrastructure.fcm.FCMFacade;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class CouponScheduler {

    private final FirebaseMessaging firebaseMessaging;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final FCMFacade fcmFacade;

    @Scheduled(cron = "0 1 * * * *", zone = "Asia/Seoul")
    public void checkCouponExpiredAt() {
        couponRepository.findAll()
                .stream()
                .filter(
                        coupon -> coupon.getExpiredAt().isAfter(LocalDate.now())
                                && coupon.getExpiredAt().isBefore(LocalDate.now().plusDays(7))
                )
                .forEach(coupon -> {
                    User user = userRepository.queryUserByCouponIn(coupon);

                    fcmFacade.notificationForCouponExpiredAt(user.getDeviceToken());
                });
    }
}
