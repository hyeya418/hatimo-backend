# 💖 Hatimo Backend

**하티모(Hatimo)** 는 `heart` + `emotion`을 합친 이름으로,  
사용자의 감정을 기록하고 AI가 따뜻하게 위로해주는 감정 기록 서비스입니다.

이 저장소는 하티모의 **백엔드(Spring Boot)** 서버를 담당합니다.

---

## 🚀 프로젝트 정보

| 항목             | 내용                       |
|------------------|--------------------------|
| 개발 언어        | Java 17                  |
| 프레임워크       | Spring Boot 3.4.4        |
| 빌드 도구        | Gradle                   |
| 데이터베이스     | PostgreSQL 17.4          |
| API 연동         | OpenAI GPT (GPT-4o mini) |

---

## 🧱 주요 기능 (예정 포함)

- [x] `/hello` Hello World 테스트
- [x] 카카오 로그인 연동, Spring Security, JWT
- [x] TDD 적용
- [x] 감정 기록 API
- [x] GPT 기반 감정 위로 메시지 제공 API
- [ ] 감정 히스토리 조회
- [ ] 푸시 알림 연동 (FCM or Expo)
- [ ] 마이페이지 기능
