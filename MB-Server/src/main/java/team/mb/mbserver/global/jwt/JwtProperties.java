package team.mb.mbserver.global.jwt;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {
    String SECRET = "ftjygfdhhtujgb";
    Long EXPIRATION_TIME = 60000*10L;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
