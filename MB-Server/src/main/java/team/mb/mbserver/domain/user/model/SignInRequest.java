package team.mb.mbserver.domain.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SignInRequest {

    @NotBlank
    private String accountId;

    @NotBlank
    private String password;
}
