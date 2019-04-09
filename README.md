# 주택 금융 서비스 API 개발


### 개발 환경 및 프레임워크
<pre><code>- Spring Boot 2.1.4
- Java 1.8
- JPA
- H2 Database</code></pre>


### 실행방법

소스를 내려받으신 후, pretest-0.0.1-SNAPSHOT.jar를 CMD창에서 실행해주세요.
<pre><code>java -jar -Dfile.encoding=UTF-8 pretest-0.0.1-SNAPSHOT.jar</code></pre>

서버가 부팅되면 curl로 API를 호출할 수 있습니다.
<pre><code>curl -X [HTTP Method] localhost:8080</code></pre>

### 문제 해결 전략

[기본문제 1] 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
- 먼저 데이터를 저장하기 위한 테이블을 설계하였습니다. (Finance.java : 주택금융 공급관리)
<pre><code>Finance(seq, year, month, institute_code, amount)</code></pre>

- CSVReader를 이용하여 CSV 파일을 파싱하고, 테이블 구조에 맞게 저장하는 기능을 구현하였습니다.
#
[기본문제 2] 주택금융 공급 금융기관(은행) 목록을 출력하는 API 개발
- institute_code(기관코드), institute_name(기관명) 컬럼을 포함하는 "Institute" 테이블을 작성 후, 기초 데이터를 등록하기위해 /resources 밑에 data.sql 파일을 작성하였습니다.
<pre><code>Institute(institute_code, institute_name)</code></pre>

- 목록 조회를 위해 findAll() 메소드를 사용하였습니다.
#
[기본문제 3] 연도별 각 금융기관의 지원금액 합계를 출력하는 API 개발
- 연도(year)로 GROUP BY후, SUM, CASE문을 사용하여 전체 총합과 기관별 지원금액 합을 구했습니다.
#
[기본문제 4] 각 연도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
- 연도(year)와 기관코드(institute_code)로 GROUP BY후, 지원금액 합이 큰 순으로 정렬하여 LIMIT로 첫 행만 가져왔습니다.
#
[기본문제 5] 전체 연도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
- 기관코드(institute_code)를 입력받아 각 연도별 평균금액을 오름차순 정렬하여 쿼리를 뽑고, 결과 리스트의 [0]번 인덱스와 [size()-1]번 인덱스 데이터를 추출하였습니다.
#
[추가 제약사항] API 인증을 위해 JWT(Json Web Token)를 이용해서 Token 기반 API 인증 기능을 개발하고 각 API 호출 시에 HTTP Header 에 발급받은 토큰을 가지고 호출하세요.

[1] signup 계정생성 API 개발
- JSON으로 id, password를 입력받고, 패스워드를 인코딩하기 위해 BCrypt 라이브러리를 사용하여 encodingPassword() 메소드를 구현하였습니다.
- 패스워드 문자열에 솔트(salt)를 추가하여 인코딩하였습니다.

[2] signin 로그인 API 개발
- 로그인 요청에 성공하면 JwtService에 구현된 createToken() 메소드를 호출하여 토큰을 생성합니다.
- 생성된 토큰을 응답 해더에 등록하고, 유효기간은 30분으로 설정하였습니다.

로그인 기능을 개발 후, JwtInterceptor 클래스를 구현하여 API 호출 시 토큰 존재 유무와 유효한 토큰인지 체크하는 로직을 추가하였습니다.
#

### API 실행 순서
1. 회원가입과 로그인 API를 호출하여 토큰을 생성합니다.
2. 데이터 파일(CSV)을 데이터베이스에 저장하는 API를 호출합니다.
3. 그 외에 다른 API를 호출합니다.
* 생성된 토큰은 Request Header에 포함하여 다른 API를 호출해야 합니다.
#

### API 목록

회원가입
- /user/signup

요청
<pre><code>curl -X POST localhost:8080/user/signup -H "Content-Type: application/json" -d "{\"id\":\"khghouse\",\"password\":\"1111\"}"</code></pre>
응답
<pre><code>{
  "resultMsg": "회원 가입 되었습니다!!"
}</code></pre>
#

로그인
- /user/signin

요청
<pre><code>curl -X POST localhost:8080/user/signin -H "Content-Type: application/json" -d "{\"id\":\"khghouse\",\"password\":\"1111\"}"</code></pre>
응답
<pre><code>{
    "login_id": "khghouse",
    "token": "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTU0ODM3ODc2NDI0LCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyVG9rZW4iLCJ1c2VyIjp7ImlkIjoia2hnaG91c2UiLCJwYXNzd29yZCI6IiQyYSQxMCR4OHcyNk10UUFXWVJLdnRIaUg4Sjd1YzZPejBMMXFyYS5PdmVGVmRuRzh2cDNQWFZjUk13TyJ9LCJleHAiOjE1NTQ4Mzk2NzZ9.VCmlpXtMiFsdh5YVrm2td-ipj9ycg1CyoJTuWcTVpVA"
}</code></pre>
#

데이터 파일 DB 저장
- /finance/init/data

요청
<pre><code>curl -X POST localhost:8080/finance/init/data -H "Authorization: 토큰값"</code></pre>
응답
<pre><code>{
    "resultMsg": "데이터가 등록되었습니다."
}</code></pre>
#

금융기관 목록 조회
- /institutes

요청
<pre><code>curl -X GET localhost:8080/institutes -H "Authorization: 토큰값"</code></pre>
응답
<pre><code>[
    {
        "institute_code": "hcf",
        "institute_name": "주택도시기금"
    },
    {
        "institute_code": "kb",
        "institute_name": "국민은행"
    },
    {
        "institute_code": "wr",
        "institute_name": "우리은행"
    },
    {
        "institute_code": "sh",
        "institute_name": "신한은행"
    },
    {
        "institute_code": "ct",
        "institute_name": "한국시티은행"
    },
    {
        "institute_code": "hn",
        "institute_name": "하나은행"
    },
    {
        "institute_code": "nh",
        "institute_name": "농협은행/수협은행"
    },
    {
        "institute_code": "keb",
        "institute_name": "외환은행"
    },
    {
        "institute_code": "etc",
        "institute_name": "기타은행"
    }
]</code></pre>
#

연도별 총합 통계
- /finance/year/sum

요청
<pre><code>curl -X GET localhost:8080/finance/year/sum -H "Authorization: 토큰값"</code></pre>
응답
<pre><code>[
    {
        "year": 2005,
        "totalAmount": 48016,
        "detailAmount": [
            {
                "주택도시기금": 22247,
                "국민은행": 13231,
                "우리은행": 2303,
                "신한은행": 1815,
                "한국시티은행": 704,
                "하나은행": 3122,
                "농협은행/수협은행": 1486,
                "외환은행": 1732,
                "기타은행": 1376
            }
        ]
    },
    
    ...
    
    {
        "year": 2017,
        "totalAmount": 295126,
        "detailAmount": [
            {
                "주택도시기금": 85409,
                "국민은행": 31480,
                "우리은행": 38846,
                "신한은행": 40729,
                "한국시티은행": 7,
                "하나은행": 35629,
                "농협은행/수협은행": 26969,
                "외환은행": 0,
                "기타은행": 36057
            }
        ]
    }
]</code></pre>
#

연도별 가장 큰 지원금액의 금융기관 조회
- /finance/year/max

요청
<pre><code>curl -X GET localhost:8080/finance/year/max -H "Authorization: 토큰값"</code></pre>
응답
<pre><code>{
    "YEAR": "2014",
    "BANK": "주택도시기금"
}</code></pre>
#

외환은행(특정은행)의 연도별 평균지원금의 최소, 최대금액 조회
- /finance/year/avg/{institute_code}

요청
<pre><code>curl -X GET localhost:8080/finance/year/avg/keb -H "Authorization: 토큰값"</code></pre>
응답
<pre><code>{
    "bank": "외환은행",
    "supportAmount": [
        {
            "YEAR": "2017",
            "AMOUNT": "0"
        },
        {
            "YEAR": "2015",
            "AMOUNT": "1702"
        }
    ]
}</code></pre>
#

### 미구현 항목..
선택 문제(옵션) 지원 금액 예측 API 개발
- 특정 은행의 지원금액 전체 데이터를 입력하고, 그 데이터를 토대로 2018년 지원 금액을 예측하기 위해 "선형 회귀 알고리즘"을 적용해 보려고 생각해봤습니다.
선형 회귀 알고리즘은 그래프 상에서 학습 데이터들을 관통하는 하나의 직선을 찾아 그것을 통해 예측하는 방식이라고 합니다.
그래프로 표현하면 x축은 200501부터(혹은 200501을 대체할 수) 시작하고, y축은 지원금액을 표현할 수 있을 거 같았습니다.
예측 알고리즘 중에는 가장 기본이 되는 알고리즘이라고는 하는데, 생각보다 구현이 쉽지 않았습니다.

추가 제약사항(옵션) refresh 토큰 재발급 API 개발
- 로그인하면 발급해주는 액세스 토큰과는 다르게 재발급 토큰은 "기존 토큰 삭제"나 "기존 토큰에서 유저정보 획득" 등의 여러 로직이 필요할 것으로
판단되었고, 개인적으로 인증 기술이 생소하여 기간내에 해결하지 못했습니다.


