package com.sparta.springauth.service;

import com.sparta.springauth.dto.LoginRequestDto;
import com.sparta.springauth.dto.SignupRequestDto;
import com.sparta.springauth.entity.User;
import com.sparta.springauth.entity.UserRoleEnum;
import com.sparta.springauth.jwt.JwtUtil;
import com.sparta.springauth.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN (관리자인지 일반사용자인지를 구분하기 위함)
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, email, password, role);
        userRepository.save(user);
    }

//    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
//        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
//
//        // 데이터베이스에 사용자 정보 존재하는지 확인
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("데이터가 없습니다.")
//        );
//
//        // 비밀번호 확인
//        if(!passwordEncoder.matches(password, user.getPassword())){
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
//        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
//        jwtUtil.addJwtToCookie(token, res); // 쿠키에 추가하고 res에 담기
//    }
}