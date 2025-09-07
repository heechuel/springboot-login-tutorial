package loginStudy.totalLogin.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import loginStudy.totalLogin.domain.user.dto.JoinRequest;
import loginStudy.totalLogin.domain.user.dto.LoginRequest;
import loginStudy.totalLogin.domain.user.entity.Role;
import loginStudy.totalLogin.domain.user.entity.User;
import loginStudy.totalLogin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionLoginController {

    private final UserService userService;

    @GetMapping(value = {"","/"})
    public String home(@SessionAttribute(name = "sessionId", required = false)Long userId, Model model){
        model.addAttribute("loginType", "session");
        model.addAttribute("pageName", "세션 로그인");

        User findUser = userService.getLoginUserById(userId);

        if (findUser != null){
            model.addAttribute("nickname", findUser.getNickname());
        }

        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("loginType", "session");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model){
        model.addAttribute("loginType", "session");
        model.addAttribute("pageName", "세션 로그인");

        if (userService.validateLoginId(joinRequest.getLoginId())){
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디 중복"));
        }
        if(userService.validateNickname(joinRequest.getNickname())){
            bindingResult.addError(new FieldError("joinRequest", "nickname", "닉네임 중복"));
        }
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호 불일치"));
        }

        if(bindingResult.hasErrors()){
            return "join";
        }

        userService.join(joinRequest);
        return "redirect:/session";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginType", "session");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest, Model model){
        model.addAttribute("loginType", "session");
        model.addAttribute("pageName", "세션 로그인");

        User user = userService.login(loginRequest);

        //로그인 실패시 error return
        if(user == null){
            bindingResult.reject("loginFail", "로그인 아이디 혹은 비밀번호 오류");
        }

        if(bindingResult.hasErrors()){
            return "login";
        }

        //로그인 성공 시 세션 생성
        //세션 생성 전 기존 세션 파기
        httpServletRequest.getSession().invalidate();

        HttpSession session = httpServletRequest.getSession(true);      //세션 미 존재 시 생성
        session.setAttribute("sessionId", user.getId());
        session.setMaxInactiveInterval(3600);               //3600초 == 1시간 세션 유지

        return "redirect:/session";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, Model model){
        model.addAttribute("loginType", "session");
        model.addAttribute("pageName", "세션 로그인");

        HttpSession session = httpServletRequest.getSession(false);     //세션 미 존재 시 null return
        if(session != null){
            session.invalidate();
        }

        return "redirect:/session";
    }

    @GetMapping("/info")
    public String userInfo(@SessionAttribute(name = "sessionId", required = false)Long userId, Model model){
        model.addAttribute("loginType", "session");
        model.addAttribute("pageName", "세션 로그인");

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser == null) {
            return "redirect:/session/login";
        }

        model.addAttribute("user", loginUser);
        return "info";
    }

    @GetMapping("/admin")
    public String adminPage(@SessionAttribute(name = "sessionId", required = false) Long userId, Model model) {
        model.addAttribute("loginType", "session");
        model.addAttribute("pageName", "세션 로그인");

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser == null) {
            return "redirect:/session/login";
        }

        if(!loginUser.getRole().equals(Role.ADMIN)) {
            return "redirect:/session";
        }

        return "admin";
    }

}
