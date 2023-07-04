package team.mb.mbserver.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.mb.mbserver.domain.coupon.entity.Coupon;
import team.mb.mbserver.domain.coupon.entity.CouponRepository;
import team.mb.mbserver.domain.coupon.model.CouponDto;
import team.mb.mbserver.domain.user.entity.User;
import team.mb.mbserver.domain.user.entity.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponService {

    private CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(CouponDto couponDto) {

        User user = getCurrentUser();

        Coupon coupon = couponDto.toEntity();

        return couponRepository.save(coupon).getId();
    }

    @Transactional
    public void delete(Long id) {
        Coupon entity = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다."));
        couponRepository.deleteById(id);
    }

    private User getCurrentUser() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public List<CouponDto> getCoupons() {
        User user = getCurrentUser();

        List<Coupon> coupons = user.getCoupons();

        return coupons.stream()
                .map(coupon -> CouponDto
                        .builder()
                        .id(coupon.getId())
                        .name(coupon.getName())
                        .imageUrl(coupon.getImageUrl())
                        .build())
                .toList();
    }
}
