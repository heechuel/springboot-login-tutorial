# 🚀 Spring Boot Login Tutorial

Spring Boot를 활용하여 로그인 기능을 학습/연습하기 위한 프로젝트입니다.  
현재는 **쿠키를 활용한 로그인 방식**을 구현했습니다.

---

## 🔑 구현한 로그인 방법
1. 🍪 **Cookie 기반 로그인**

---

## ⚙️ 구현 기능
1. 📝 **회원가입 기능**
    - 로그인 아이디 중복 검증
    - 닉네임 중복 검증
    - 비밀번호 확인 검증

2. 🔐 **로그인 기능**
    - 쿠키를 통한 세션 유지
    - 로그인 실패 시 에러 메시지 처리

3. 🚪 **로그아웃 기능**
    - 쿠키 제거를 통한 로그아웃 처리

4. 👤 **유저 정보 페이지**
    - 로그인된 사용자 정보 확인 가능

5. 🛡️ **관리자 페이지**
    - 관리자 권한 접근 가능 (권한 체크)

---

## 📂 프로젝트 구조

```bash
src
 └── main
     ├── java
     │   └── loginStudy/totalLogin
     │       ├── config         # 설정 관련
     │       └── domain
     │           ├── controller # Controller (쿠키 로그인 컨트롤러)
     │           ├── service    # 서비스 로직
     │           ├── user       # User 엔티티
     │           └── dto        # 요청 DTO (JoinRequest, LoginRequest)
     └── resources
         └── templates
             ├── home.html
             ├── login.html
             ├── join.html
             ├── info.html
             └── admin.html

```
---

## 🛠️ 기술 스택
- **Java 21**
- **Spring Boot 3.5.5**
- **Thymeleaf**
- **MySQL Database**

---

## 📌 향후 계획
- [ ] 세션 기반 로그인 구현
- [ ] Spring Security 사용 로그인 구현(Form Login)
- [ ] JWT Token API 기반 로그인 구현
- [ ] JWT Token Page 기반 로그인 구현
- [ ] OAuth 2.0 로그인 구현
- [ ] Keycloak 로그인 구현

---

## 🙌 목적
이 프로젝트는 **Spring Boot 로그인 학습용**으로 제작되었으며,  
쿠키, 세션, JWT 등 다양한 로그인 방식을 연습하며 정리하기 위한 튜토리얼입니다.
