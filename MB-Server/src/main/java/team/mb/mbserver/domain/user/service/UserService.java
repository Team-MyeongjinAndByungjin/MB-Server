package team.mb.mbserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.mb.mbserver.domain.user.entity.User;
import team.mb.mbserver.domain.user.entity.UserRepository;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @Transactional
    public TokenResponse signUp(SignUpRequest request) {

        if (userRepository.existsByAccountId(request.getAccountId())) {
            throw new BusinessException(409, "User Already Exists");
        }

        User user = User.builder()
                .accountId(request.getAccountId())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return new TokenResponse(
                jwtProvider.generateAccessToken(user.getAccountId()),
                LocalDateTime.now().plusSeconds(jwtProperties.getEXPIRATION_TIME())
        );
    }

    @Transactional
    public TokenResponse signIn(SignInRequest request) {

        User user = userRepository.findByAccountId(request.getAccountId())
                .orElseThrow(() -> new BusinessException(404, "User Not Found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "Invalid Password");
        }

        return new TokenResponse(
                jwtProvider.generateAccessToken(request.getAccountId()),
                LocalDateTime.now().plusSeconds(jwtProperties.getEXPIRATION_TIME())
        );
    }
}
