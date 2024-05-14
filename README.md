# whatSeoul
- ESTSoft 대화형 AI 에이전트 [앨런](https://alan.estsoft.ai/old-home#about-1)과 [서울 실시간 도시데이터](https://data.seoul.go.kr/dataList/OA-21285/F/1/datasetView.do)를 활용한 웹 사이트
<br>
- `서비스 URL` : '...'
><center style="font-style: italic">서울 어딘가로 떠나고 싶은 오늘,<br>어느 곳으로 가야 할지 모르겠다면<br><br><strong style="font-style: normal">서울시 실시간 지역 정보</strong>를 확인하고<br><strong style="font-style: normal">앨런에게 명소를 추천</strong>받아 보세요!</center> 

## 🗺️ 주요 기능
### 서울시 115개 핫스팟 정보 조회
### 서울시 실시간 도시데이터 조회
### SSE를 활용한 앨런 AI 실시간 응답 조회
### Spring Security를 활용한 유저 인증, 인가
### 게시판 게시글 및 댓글 작성
### 마이페이지 

## 📑 요구사항 명세서

## 📜 API 명세서

## 🤼‍♂️ Team
<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/soogoori"><img src="" width="100px;" alt="프로필 사진"/><br /><sub><b>윤수빈(팀장)</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/gyenug"><img src="" width="100px;" alt="프로필 사진"/><br /><sub><b>고경욱</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/kkyro"><img src="" width="100px;" alt="프로필 사진"/><br /><sub><b>김경록</b></sub></a><br /></td>
     </tr>
    <tr>
        <td>- 서울 실시간 도시데이터 조회 기능 구현 <br> - 앨런 AI API 연동 <br> - CI/CD 구축 </td>
        <td>- 사용자 인증 및 인가 (Spring Security)<br>- 로그인 및 회원가입 조건 기능 구현</td>
        <td>- 사용자 인증 및 인가 (Spring Security) </td>
    </tr>
    <tr>
      <td align="center"><a href="https://github.com/kimmingyu74"><img src="" width="100px;" alt="프로필 사진"/><br /><sub><b>김민규</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/biiit4894"><img src="" width="100px;" alt="프로필 사진"/><br /><sub><b>장한빛</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/ch0Changhyun"><img src="" width="100px;" alt="프로필 사진"/><br /><sub><b>조창현</b></sub></a><br /></td>
    </tr>
    <tr>
        <td>- 게시판 CRUD 기능 구현 </td>
        <td>- 서울 실시간 도시데이터 조회 기능 구현 <br> - 앨런 AI API 연동 <br> - 지역 정보 저장·조회 기능 구현</td>
        <td> - 서울 실시간 도시데이터 조회 기능 구현 <br> - 게시판 CRUD 기능 구현 <br> - 댓글 작성·삭제 기능 구현</td>
    </tr>
  </tbody>
</table>

## ⚙️ 시스템 설계도
![image](https://github.com/WhatSEOUL/WhatSeoul/assets/82032418/5c0ea7a7-ce43-4e48-ba70-91c868915a17)

## ⛏ ERD

## 📦 Project Structure
<details>
  <summary>디렉토리 구조</summary>
  <pre>
  ├───main
  │   ├───generated
  │   ├───java
  │   │   └───com
  │   │       └───example
  │   │           └───whatseoul
  │   │               ├───config
  │   │               ├───controller
  │   │               │   ├───account
  │   │               │   ├───alan
  │   │               │   ├───citydata
  │   │               │   └───post
  │   │               ├───dto
  │   │               │   ├───request
  │   │               │   └───response
  │   │               ├───entity
  │   │               ├───exception
  │   │               ├───repository
  │   │               │   ├───cityData
  │   │               │   ├───post
  │   │               │   └───user
  │   │               ├───security
  │   │               ├───service
  │   │               └───util
  │   └───resources
  │       ├───static
  │       │   ├───css
  │       │   │   ├───alan
  │       │   │   ├───citydata
  │       │   │   ├───index
  │       │   │   ├───post
  │       │   │   ├───selectarea
  │       │   │   └───user
  │       │   ├───js
  │       │   │   ├───alan
  │       │   │   ├───citydata
  │       │   │   ├───index
  │       │   │   └───selectarea
  │       │   └───media
  │       └───templates
  │           ├───alan
  │           ├───citydata
  │           ├───header
  │           ├───index
  │           ├───post
  │           ├───selectarea
  │           └───user
  └───test
      └───java
          └───com
              └───example
                  └───whatseoul
                      └───service


  </pre>
</details>

## 🛠 Tech Stack
Language & Framework<br>
<img alt="Java 17" src ="https://img.shields.io/badge/Java 17-007396.svg?&style=for-the-badge&logo=java&logoColor=white"/>
<img alt="Springboot 3.2.5" src ="https://img.shields.io/badge/Springboot 3.2.5-6DB33F.svg?&style=for-the-badge&logo=springboot&logoColor=white"/>
<img alt="Spring Security" src ="https://img.shields.io/badge/Spring Security-6DB33F.svg?&style=for-the-badge&logo=spring security&logoColor=white"/>

Database<br>
<img alt="MySQL" src ="https://img.shields.io/badge/MySQL-4479A1.svg?&style=for-the-badge&logo=MySQL&logoColor=white"/>
<img alt="Spring data JPA" src ="https://img.shields.io/badge/spring data JPA-444AA1.svg?&style=for-the-badge&logo=jpa&logoColor=white"/>

배포 환경<br>
<img alt="Amazon EC2" src ="https://img.shields.io/badge/Amazon EC2-FF9900.svg?&style=for-the-badge&logo=amazonec2&logoColor=white"/>
<img alt="Amazon RDS" src ="https://img.shields.io/badge/Amazon RDS-527FFF.svg?&style=for-the-badge&logo=Amazon RDS&logoColor=white"/>
<img alt="Docker" src ="https://img.shields.io/badge/Docker-1D63ED.svg?&style=for-the-badge&logo=docker&logoColor=white"/>
<img alt="Github Actions" src ="https://img.shields.io/badge/Github Actions-000000.svg?&style=for-the-badge&logo=github actions&logoColor=white"/>

UI 개발<br>
<img alt="HTML" src ="https://img.shields.io/badge/HTML-E34F26.svg?&style=for-the-badge&logo=html5&logoColor=white"/>
<img alt="CSS" src ="https://img.shields.io/badge/CSS3-1572B6.svg?&style=for-the-badge&logo=css3&logoColor=white"/>
<img alt="Javascript" src ="https://img.shields.io/badge/Javascript-F7DF1E.svg?&style=for-the-badge&logo=javascript&logoColor=white"/>
<img alt="Thymeleaf" src ="https://img.shields.io/badge/Thymeleaf-005F0F.svg?&style=for-the-badge&logo=thymeleaf&logoColor=white"/>

IDEA<br>
<img alt="Intellij IDEA" src ="https://img.shields.io/badge/Intellij Idea-000000.svg?&style=for-the-badge&logo=intellijidea&logoColor=white"/>

협업 툴<br>
<img alt="Github" src ="https://img.shields.io/badge/github-181717.svg?&style=for-the-badge&logo=github&logoColor=white"/>
<img alt="Git" src ="https://img.shields.io/badge/git-F05032.svg?&style=for-the-badge&logo=git&logoColor=white"/>
<br>
<img alt="Discord" src ="https://img.shields.io/badge/Discord-5865F2.svg?&style=for-the-badge&logo=discord&logoColor=white"/>
<a href="">
<img alt="Notion" src ="https://img.shields.io/badge/Notion (Link)-000000.svg?&style=for-the-badge&logo=Notion&logoColor=white"/>
</a>
<img alt="Miro" src ="https://img.shields.io/badge/miro-050038.svg?&style=for-the-badge&logo=miro&logoColor=white"/>
<img alt="Figma" src ="https://img.shields.io/badge/figma-C382DF.svg?&style=for-the-badge&logo=figma&logoColor=white"/>

## 🌈 Branch
> 브랜치는 release branch, develop branch, feature branch 총 3개의 브랜치를 사용합니다.

## 🌞 Commit Message Convention
| Emoticon | Commit Type | Desc |
| --- | --- | --- |
| ✨ | feat | 새로운 기능 추가 |
| 🐛 | fix | 버그 수정 |
| 📝 | docs | 문서 수정 (md 파일) |
| ♻️ | refactor | 코드 리팩토링 |
| 💄 | style | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
| ✅ | test | 테스트 코드, 리팩토링 테스트 코드 추가 |
| 🚀 | chore | 패키지 매니저 수정 (Dockerfile, gradle, sh, yml) |
| 🚑 | !hotfix | 급하게 치명적인 버그를 고쳐야 하는 경우 |
