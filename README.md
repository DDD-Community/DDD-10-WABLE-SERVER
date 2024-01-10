# harmonika - DDD-10-WABLE-SERVER 
> harmonika API ..

CI/CD 끝나면 아래 링크 수정 필요

[![Build Status](https://harmonika/DDD-Community/DDD-10-WABLE-SERVER.svg?branch=main)](https://harmonika/DDD-Community/DDD-10-WABLE-SERVER)
[![Coverage Status](https://harmonika/DDD-Community/DDD-10-WABLE-SERVER.svg?branch=main)](https://harmonika/DDD-Community/DDD-10-WABLE-SERVER?branch=main)


* MySQL 뭐시기..
* API 뭐시기..
* 스웨거 뭐시기..


## 요구 사항
* JDK ..
* Maven/Gradle ..

## 실행 방법


```sh
# vi /etc/hosts
127.0.0.1       ddd.com api.ddd.com db.ddd.com soan.ddd.com jenkins.ddd.com

# cli
docker-compose up -d
```
http://localhost:81 에서 잘 하셈

## 개발 환경 설정

개발 의존성 설치 방법과 자동 테스트 슈트 실행 방법 작성

```sh
..
```

## 업데이트 내역

* 0.0.0
    * 준비중..

## MVP

<details><summary>Phase 0</summary><pre markdown="1">
- 타켓(동아리/동호회)
- 기능
    - 기본 제공 카드를 주거나, 받을 수 있음
    - 기본 자기소개를 완성할 수 있음
    - 카드에 메시지를 추가할 수 있음
    - 초대 링크로 팀원을 초대할 수 있음
</pre></details>

<details><summary>Phase 1</summary><pre markdown="1">
- 타켓(동아리/동호회)
- 기능
    - 소셜 로그인
    - 조직별 자기소개 템플릿 설정
    - 카드 비공개, 공개 모드를 설정할 수 있음
    - 사용자는 카드를 롤링페이퍼 형식으로 볼 수있음 
    - 이메일로 팀원을 초대할 수 있음
</pre></details>

<details><summary>Phase 2</summary><pre markdown="1">
- 타켓(동아리/동호회, 스타트업)
- 기능
    - 기본 제공 카드, 조직 커스텀 카드를 주거나, 받을 수 있음
    - 관리자는 조직의 커스텀 카드를 생성할 수 있음
    - 조직별 분석 제공 (가장 많은 카드를 보낸 사람, 가장 많은 칭찬 카드를 받은 사람 ..)
    - 관리자는 미션을 만들 수 있음 (0일 뒤 00의 생일이에요! 생일 축하 카드를 보내보아요! 등)
</pre></details>

<details><summary>Phase 3</summary><pre markdown="1">
- 타켓(동아리/동호회, 스타트업, 학교, 커뮤니티)
- 기능
    - 기본 제공 카드, 조직 커스텀 카드, 사용자 커스텀 카드를 주거나, 받을 수 있음
    - 관리자는 조직의 커스텀 카드를 생성할 수 있음
    - 사용자는 조직의 커스텀 카드를 생성할 수 있음
    - 사용자별 인공지능 요약 제공 (사람들이 말한 바에 따르면, 당신은 00한 사람이군요!)
</pre></details>

