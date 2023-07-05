package team.mb.mbserver.domain.user.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.mb.mbserver.domain.coupon.entity.Coupon;

import static team.mb.mbserver.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Component
public class UserRepository {

    private final JPAQueryFactory queryFactory;

    public User queryUserByCouponIn(Coupon coupon) {
        return queryFactory
                .selectFrom(user)
                .where(user.coupons.contains(coupon))
                .fetchOne();
    }
}
