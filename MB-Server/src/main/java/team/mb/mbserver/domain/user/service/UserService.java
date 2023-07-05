package team.mb.mbserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.mb.mbserver.domain.user.entity.User;
import team.mb.mbserver.domain.user.entity.UserJpaRepository;
import team.mb.mbserver.domain.user.model.SignInRequest;
import team.mb.mbserver.domain.user.model.SignUpRequest;
import team.mb.mbserver.domain.user.model.TokenResponse;
import team.mb.mbserver.global.error.BusinessException;
import team.mb.mbserver.global.jwt.JwtProperties;
import team.mb.mbserver.global.jwt.JwtProvider;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @Transactional
    public TokenResponse signUp(SignUpRequest request) {

        if (userJpaRepository.existsByAccountId(request.getAccountId())) {
            throw new BusinessException(409, "유저가 이미 존재합니다.");
        }

        User user = User.builder()
                .accountId(request.getAccountId())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userJpaRepository.save(user);

        return new TokenResponse(
                jwtProvider.generateAccessToken(user.getAccountId()),
                LocalDateTime.now().plusSeconds(jwtProperties.getEXPIRATION_TIME())
        );
    }

    @Transactional
    public TokenResponse signIn(SignInRequest request) {

        User user = userJpaRepository.findByAccountId(request.getAccountId())
                .orElseThrow(() -> new BusinessException(404, "유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "비밀번호가 잘못됐습니다.");
        }

        user.settingDeviceToken(request.getDeviceToken());

        return new TokenResponse(
                jwtProvider.generateAccessToken(request.getAccountId()),
                LocalDateTime.now().plusSeconds(jwtProperties.getEXPIRATION_TIME())
        );
    }
}
