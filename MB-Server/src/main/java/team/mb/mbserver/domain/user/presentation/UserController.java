package team.mb.mbserver.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.mb.mbserver.domain.user.model.SignInRequest;
import team.mb.mbserver.domain.user.model.SignUpRequest;
import team.mb.mbserver.domain.user.model.TokenResponse;
import team.mb.mbserver.domain.user.service.UserService;

import javax.validation.Valid;

@Tag(name = "유저", description = "유저 관련 API 입니다.")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "유저 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = SignUpRequest.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    public TokenResponse signUp(@RequestBody SignUpRequest request) {
        System.out.println(request.getAccountId());
        return userService.signUp(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/token")
    @Operation(summary = "유저 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = SignInRequest.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    public TokenResponse signIn(@RequestBody @Valid SignInRequest request) {
        return userService.signIn(request);
    }
}
