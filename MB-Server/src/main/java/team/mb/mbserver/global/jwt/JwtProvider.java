package team.mb.mbserver.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.mb.mbserver.domain.user.entity.User;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    public static String createAccessToken(User userEntity) {
        return JWT.create()
                .withSubject(userEntity.getAccountId())
                .withClaim("id", userEntity.getId())
                .withClaim("username", userEntity.getId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
}
 }
