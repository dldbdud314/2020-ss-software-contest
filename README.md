# 2020 교내 소프트웨어 경진대회

## 수룡아 뭐먹지?

![image](https://user-images.githubusercontent.com/57944099/90314670-55ea0800-df50-11ea-804c-3f27247e4e1a.png)

**상황에 따른 적합한 분위기의 맛집을 추천하는 수정이 맞춤 어플리케이션입니다.**

### 참여자
> [김지현](https://github.com/JEEHYUNEE) <br>
> [이유영](https://github.com/dldbdud314) <br>
> [안수민](https://github.com/ahnsumin)

### 깃 브랜치 (참여자 이름)
master --- jeehyun yooyoung sumin

### 역할
- 지현: 로그인(SignInActivity 전반), 마이페이지/로그아웃/회원탈퇴(MypageActivity), 회원가입(SignUpActivity 전반), 인스타그램 형용사 태그 추출(KoNLPy패키지 사용), 가게상세페이지(StoreInfoActivity)
- 유영: 검색 기능(SearchActivity), 리뷰 리스트 및 리뷰 등록 기능(StoreInfoActivity 일부, ReviewRegisterActivity), 인스타그램 가게 크롤링
- 수민: 로그인(이메일 인증 확인 후 로그인 가능 등 SignInActivity 일부), 회원가입(유효성 검사 등 SignUpActivity 일부), 지도(지도뷰, 마커 등 MapsActivity), 아이디/비밀번호 찾기(IdPassSearchActivity)

### 개발 환경
#### 기본환경 및 기술스택

![image](https://user-images.githubusercontent.com/57944099/90315048-2ee10580-df53-11ea-967f-414e436179d2.png)
![image](https://user-images.githubusercontent.com/57944099/90315057-402a1200-df53-11ea-839e-004c886b46f7.png)
![image](https://user-images.githubusercontent.com/57944099/90315065-50da8800-df53-11ea-959b-e7550fec40ef.png)

#### 기타
- 카카오 지도 api

![image](https://user-images.githubusercontent.com/57944099/90315192-6b613100-df54-11ea-801e-e9b81326eb90.png)

- 인스타그램 해쉬태그 크롤링

![image](https://user-images.githubusercontent.com/57944099/90315247-db6fb700-df54-11ea-9e40-2bf8a38e2b62.png)

### 전체 플로우 및 기본 기능 설명

#### 기본 기능
1. 로그인/회원가입

![image](https://user-images.githubusercontent.com/57944099/91684522-c5880600-eb92-11ea-8617-362c431a2bcd.png)
![image](https://user-images.githubusercontent.com/57944099/91684538-ca4cba00-eb92-11ea-9bd7-09da041b427e.png)

2. 상황별 적합한 맛집 추천

![image](https://user-images.githubusercontent.com/57944099/91684556-d89ad600-eb92-11ea-853a-ce76a13971ce.png)
![image](https://user-images.githubusercontent.com/57944099/91684567-dd5f8a00-eb92-11ea-8269-46f67dbcd896.png)

3. 가게 상세 정보 조회

![image](https://user-images.githubusercontent.com/57944099/91684600-ecded300-eb92-11ea-86e0-2c74a0375609.png)

4. 맛집 검색

![image](https://user-images.githubusercontent.com/57944099/91684633-fe27df80-eb92-11ea-9577-c2d108f27246.png)

5. 마이페이지

![image](https://user-images.githubusercontent.com/57944099/91684657-0aac3800-eb93-11ea-923d-51dd53e0f421.png)

#### 전체 플로우
![image](https://user-images.githubusercontent.com/57944099/91018482-9d485680-e62a-11ea-90fa-e4941cac981e.png)

### ERD
![image](https://user-images.githubusercontent.com/57944099/91018011-f663ba80-e629-11ea-9c87-509427ace96f.png)
