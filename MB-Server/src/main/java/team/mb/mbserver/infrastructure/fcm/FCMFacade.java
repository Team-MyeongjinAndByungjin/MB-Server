package team.mb.mbserver.infrastructure.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.mb.mbserver.global.error.BusinessException;

@RequiredArgsConstructor
@Component
public class FCMFacade {

    private final FirebaseMessaging firebaseMessaging;

    public void notificationForCoupon(String owner, String deviceToken) {
        Notification notification = Notification.builder()
                .setTitle("\uD83C\uDF81 선물 도착")
                .setBody(owner + "님에게 고마움을 표현하세요!")
                .build();

        Message message = Message.builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .build();

        sendMessage(message);
    }

    public void notificationForCouponExpiredAt(String deviceToken) {
        Notification notification = Notification.builder()
                .setTitle("😱쿠폰의 유효기간이 얼마 남지 않았어요.")
                .setBody("😀쿠폰을 빨리 사용하세요 ㅎㅎ")
                .build();

        Message message = Message.builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .build();

        sendMessage(message);
    }

    private void sendMessage(Message message) {
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new BusinessException(400, "알림 보내기를 실해했습니다.");
        }
    }
}
