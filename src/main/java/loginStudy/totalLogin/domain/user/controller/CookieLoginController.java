package loginStudy.totalLogin.domain.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import loginStudy.totalLogin.domain.user.entity.Role;
import loginStudy.totalLogin.domain.user.entity.User;
import loginStudy.totalLogin.domain.user.dto.JoinRequest;
import loginStudy.totalLogin.domain.user.service.UserService;
import loginStudy.totalLogin.domain.user.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cookie")
public class CookieLoginController {

    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String home(@CookieValue(name = "userId", required = false) Long userId, Model model){
        model.addAttribute("loginType", "cookie");
        model.addAttribute("pageName", "쿠키 로그인");

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser != null){
            model.addAttribute("nickname", loginUser.getNickname());

        }
        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("loginType", "cookie");
        model.addAttribute("pageName", "쿠키 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model){
        model.addAttribute("loginType", "cookie");
        model.addAttribute("pageName", "쿠키 로그인");

        //로그인아이디 중복 체크
        if(userService.validateLoginId(joinRequest.getLoginId())){
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디 중복"));
        }

        //닉네임 중복 체크
        if(userService.validateNickname(joinRequest.getNickname())){
            bindingResult.addError(new FieldError("joinRequest", "nickname", "닉네임이 중복됩니다."));
        }

        //비밀번호 재검사
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "바밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()){
            return "join";
        }

        userService.join(joinRequest);
        return "redirect:/cookie";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginType", "cookie");
        model.addAttribute("pageName", "쿠키 로그인");

        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult, HttpServletResponse response, Model model){
        model.addAttribute("loginType", "cookie");
        model.addAttribute("pageName", "쿠키 로그인");

        User user = userService.login(loginRequest);

        //로그인 아이디 또는 비밀번호 오류시 error
        if(user == null){
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호 오류");
        }

        if(bindingResult.hasErrors()){
            return "login";
        }

        //로그인 성공 시 쿠키 생성
        Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
        cookie.setMaxAge(60 * 60); // 쿠키 유효시간 : 1시간
        response.addCookie(cookie);

        return "redirect:/cookie";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, Model model){
        model.addAttribute("loginType", "cookie");
        model.addAttribute("pageName", "쿠키 로그인");

        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/cookie";
    }

    @GetMapping("/info")
    public String userInfo(@CookieValue(name = "userId", required = false)Long userId, Model model){
        model.addAttribute("loginType", "cookie");
        model.addAttribute("pageName", "쿠키 로그인");

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser == null) {
            return "redirect:/cookie/login";
        }

        model.addAttribute("user", loginUser);
        return "info";
    }

    @GetMapping("/admin")
    public String adminPage(@CookieValue(name = "userId", required = false) Long userId, Model model) {
        model.addAttribute("loginType", "cookie");
        model.addAttribute("pageName", "쿠키 로그인");

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser == null) {
            return "redirect:/cookie/login";
        }

        if(!loginUser.getRole().equals(Role.ADMIN)) {
            return "redirect:/cookie";
        }

        return "admin";
    }
}
