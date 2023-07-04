package team.mb.mbserver.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private final String token;
    private final LocalDateTime time;
}
