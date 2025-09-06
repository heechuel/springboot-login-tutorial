package loginStudy.totalLogin.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import loginStudy.totalLogin.domain.user.entity.Role;
import loginStudy.totalLogin.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "로그인 아이디 입력")
    private String loginId;

    @NotBlank(message = "비밀번호 입력")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "닉네임 입력")
    private String nickname;

    public User toEntity(){
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .nickname(this.nickname)
                .role(Role.USER)
                .build();
    }

    public User toEntity(String encodedPassword){
        return User.builder()
                .loginId(this.loginId)
                .password(encodedPassword)
                .nickname(this.nickname)
                .role(Role.USER)
                .build();
    }
}
