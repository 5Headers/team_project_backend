# ⚙️ 5Headers Back-End — GPT 기반 맞춤형 컴퓨터 견적 서비스

[![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3+-brightgreen?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Java](https://img.shields.io/badge/Java-17+-red?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![GPT API](https://img.shields.io/badge/OpenAI%20API-Integrated-black?logo=openai&logoColor=white)](https://platform.openai.com/)
[![Naver API](https://img.shields.io/badge/Naver%20Shopping%20API-Integrated-03C75A?logo=naver&logoColor=white)](https://developers.naver.com/docs/serviceapi/search/shopping/shopping.md)
[![Kakao API](https://img.shields.io/badge/Kakao%20Map%20API-Integrated-FFCD00?logo=kakao&logoColor=black)](https://apis.map.kakao.com/)
[![Firebase](https://img.shields.io/badge/Firebase-Storage-FFCA28?logo=firebase&logoColor=black)](https://firebase.google.com/)

> GPT를 활용하여 사용자의 요구에 맞는 **맞춤형 컴퓨터 견적**을 생성하고  
> 네이버 쇼핑 및 카카오맵 API를 통해 **온라인/오프라인 구매 경로**를 제공하며,  
> Firebase를 활용한 **프로필 이미지 업로드/저장 기능**까지 지원하는  
> **5Headers 팀의 백엔드 서버**입니다.  
> Spring Boot + MySQL + REST API 기반으로 프론트엔드(Vite + React)와 통신합니다.

---

## 🚀 주요 기능

- 🔐 **회원가입 / 로그인 / JWT 인증**
- 💬 **GPT API 연동** — 사용자의 입력 기반 견적 생성
- 🧠 **OpenAI 응답 JSON 파싱 및 데이터 정제**
- 🛒 **네이버 쇼핑 API** — 부품별 최저가, 온라인 구매 링크 제공
- 🏪 **카카오맵 API** — 내 위치 기반 오프라인 컴퓨터 매장 정보 제공
- 💾 **견적 저장 / 불러오기 / 즐겨찾기**
- 🖼️ **Firebase 연동** — 프로필 이미지 업로드 및 관리
- ⚙️ **Controller / Service / DAO 계층 구조**
- 🌐 **CORS / 예외 처리 / 응답 구조 통일**

---

## 🧰 기술 스택

| 분류 | 사용 기술 |
|------|-----------|
| Language | ![Java](https://img.shields.io/badge/Java-17-red?logo=openjdk&logoColor=white) |
| Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3+-brightgreen?logo=springboot&logoColor=white) |
| DB | ![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?logo=mysql&logoColor=white) |
| Auth | ![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT%20Auth-6DB33F?logo=springsecurity&logoColor=white) |
| OAuth2 | Google / Naver / Kakao |
| AI Integration | ![OpenAI](https://img.shields.io/badge/OpenAI-API-black?logo=openai&logoColor=white) |
| API Integration | ![Naver](https://img.shields.io/badge/Naver%20Shopping-API-03C75A?logo=naver&logoColor=white) ![Kakao](https://img.shields.io/badge/Kakao%20Map-API-FFCD00?logo=kakao&logoColor=black) |
| Storage | ![Firebase](https://img.shields.io/badge/Firebase-Storage-FFCA28?logo=firebase&logoColor=black) |
| Build | ![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white) |
| Test | ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf&logoColor=white)

---

## 📦 주요 의존성 (`pom.xml` 기준)

```xml
<!-- Web, Security, Mail, JWT -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>

    <!-- MyBatis -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>3.0.5</version>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>

    <!-- MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Dev / Utility -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>




```
 ## 🏗️ 프로젝트 구조
```
src/
 ┣ main/
 ┃ ┣ java/project_5headers/com/
 ┃ ┃ ┣ config/         # Security, CORS, WebFlux 설정
 ┃ ┃ ┣ controller/     # API 엔드포인트 정의
 ┃ ┃ ┣ service/        # 비즈니스 로직
 ┃ ┃ ┣ domain/         # Entity, DTO 클래스
 ┃ ┃ ┣ mapper/         # MyBatis 매퍼 인터페이스
 ┃ ┃ ┗ jwt/            # JWT 토큰 유틸 및 필터
 ┃ ┗ resources/
 ┃   ┣ mapper/         # MyBatis XML 매퍼
 ┃   ┣ templates/      # Thymeleaf (테스트용)
 ┃   ┗ application.properties # 환경 설정 파일
 ┗ test/
     ┗ java/...         # 단위 및 통합 테스트




```

## 📑 API 명세

### AuthController
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| GET | /auth/principal | - | 현재 로그인한 사용자 정보 | 로그인 사용자 조회 |
| POST | /auth/signup | SignupReqDto | ApiRespDto | 회원가입 |
| POST | /auth/signin | SigninReqDto | ApiRespDto | 로그인 & JWT 발급 |
| GET | /auth/check-username | username | ApiRespDto | 아이디 중복 확인 |
| GET | /auth/check-email | email | ApiRespDto | 이메일 중복 확인 |
| DELETE | /auth/withdraw | - | ApiRespDto | 회원탈퇴 |

### AccountController
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| POST | /account/change/password | ChangePasswordReqDto | ApiRespDto | 비밀번호 변경 |
| POST | /account/reset-password | ResetPasswordReqDto | ApiRespDto | 비밀번호 찾기 |
| POST | /account/change/profileimg | ChangeProfileImgReqDto | ApiRespDto | 프로필 이미지 변경 |
| POST | /account/find-id | name, email | ApiRespDto | 아이디 찾기 |

### EstimateController
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| POST | /estimate/add | Estimate | ApiRespDto | 견적 추가 |
| GET | /estimate/{estimateId} | - | ApiRespDto | 견적 조회 |
| GET | /estimate/user/{userId} | - | ApiRespDto | 사용자 견적 목록 |
| GET | /estimate/list | - | ApiRespDto | 전체 견적 목록 |
| POST | /estimate/update | Estimate | ApiRespDto | 견적 수정 |
| POST | /estimate/remove/{estimateId} | - | ApiRespDto | 견적 삭제 |
| POST | /estimate/gpt | GptEstimateRequest | ApiRespDto | GPT 기반 견적 생성 |

### EstimatePartController
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| POST | /estimate-part/add | EstimatePart | ApiRespDto | 부품 추가 |
| GET | /estimate-part/{estimatePartId} | - | EstimatePart | 부품 조회 |
| GET | /estimate-part/list/{estimateId} | - | List<EstimatePart> | 견적별 부품 목록 |
| POST | /estimate-part/update | EstimatePart | ApiRespDto | 부품 수정 |
| POST | /estimate-part/remove/{estimatePartId} | - | ApiRespDto | 부품 삭제 |
| GET | /estimate-part/list | - | List<EstimatePart> | 전체 부품 목록 |

### BookmarkController
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| POST | /bookmark/add | Bookmark | ApiRespDto | 즐겨찾기 추가 |
| GET | /bookmark/user/{userId} | - | ApiRespDto | 사용자 즐겨찾기 목록 |
| DELETE | /bookmark/remove/{bookmarkId} | - | ApiRespDto | 즐겨찾기 삭제 |
| POST | /bookmark/toggle/{estimateId} | - | ApiRespDto | 즐겨찾기 토글 |

### ChatController
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| POST | /chat/estimate | purpose, cost, title | ApiRespDto<EstimateRespDto> | GPT 기반 견적 생성 |

### KakaoMapController
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| GET | /api/maps/nearby | category, x, y, radius | JSON | 카테고리 기반 주변 검색 |
| GET | /api/maps/computer-stores | x, y, radius | JSON | 내 위치 기준 컴퓨터 매장 검색 |

### MailController
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| POST | /mail/send | SendMailReqDto | ApiRespDto | 이메일 전송 |
| GET | /mail/verify | verifyToken | Thymeleaf | 이메일 인증 |

### OAuth2Controller
| Method | Endpoint | Request Body / Params | Response | 설명 |
|--------|----------|--------------------|----------|-----|
| POST | /oauth2/merge | OAuth2MergeReqDto | ApiRespDto | OAuth2 계정 병합 |
| POST | /oauth2/signup | OAuth2SignupReqDto | ApiRespDto | OAuth2 회원가입 |

---

## ⚙️ 설정 예시 (application.yml / properties)

```yaml
spring.application.name: team_project_backend
spring.datasource.url: jdbc:mysql://localhost:3306/project
spring.datasource.username: 
spring.datasource.password: 
spring.datasource.driver-class-name: com.mysql.cj.jdbc.Driver

mybatis.mapper-locations: mappers/*.xml

jwt.secret:



