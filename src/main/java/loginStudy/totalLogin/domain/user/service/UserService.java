package loginStudy.totalLogin.domain.user.service;

import loginStudy.totalLogin.domain.user.entity.User;
import loginStudy.totalLogin.domain.user.dto.JoinRequest;
import loginStudy.totalLogin.domain.user.repository.UserRepository;
import loginStudy.totalLogin.domain.user.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    //로그인 아이디 중복 확인
    public boolean validateLoginId(String loginId){
        return userRepository.existsByLoginId(loginId);
    }

    //닉네임 중복 확인
    public boolean validateNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }



    //회원가입 기능 1 (비밀번호 암호화 저장 X)
    public void join(JoinRequest joinRequest){
        userRepository.save(joinRequest.toEntity());
    }

    //회원가입 기능 2 (비밀번호 암호화 저장 O)
    public void encryptionJoin(JoinRequest joinRequest){
        userRepository.save(joinRequest.toEntity(encoder.encode(joinRequest.getPassword())));
    }


    //로그인 기능
    public User login(LoginRequest loginRequest){
        Optional<User> findUser = userRepository.findByLoginId(loginRequest.getLoginId());

        if(findUser.isEmpty()){
            return null;
        }

        User user = findUser.get();

        if (!user.getPassword().equals(loginRequest.getPassword())){
            return  null;
        }

        return user;
    }


    //유저 ID로 user 찾기 기능
    //인증, 인가시 사용
    public User getLoginUserById(Long userId){
        if(userId == null) return  null;

        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) return null;

        return findUser.get();

    }

    //유저 loginID로 찾기 기능
    //인증, 인가시 사용
    public User getLoginUserByLoginId(String loginId){
        if(loginId == null) return null;

        Optional<User> findUser = userRepository.findByLoginId(loginId);
        if (findUser.isEmpty()) return null;

        return findUser.get();
    }
}
