# 데이터 연동 API 구현

## 캐시노트

### 간편연결 확인 동의 API

#### Request Url

```
GET /api/v1/quick-connect/availability?registrationNumber=사업자등록번호
```

#### Response

```json
{
    "result": true
}
```

### 약관 동의 내역 저장 API

#### Request Url

```
POST /api/v1/terms/agreement
```

#### Request

```json
{
    "registrationNumber": "사업자등록번호",
    "agreedType": true
}
```

## 개발 환경

### 주요 환경

- Kotlin 2.0.20, JDK 22
- Spring Boot 3.3.4
- Spring Cloud 2023.0.3

### 테스트 환경

- Junit, Kotest Assertion, Mockk

### 문서 관리

- mockMvc 테스트 코드를 통해 OpenAPI yaml 파일을 생성하여 Swagger-ui를 제공합니다.
    - 문서도 코드와 마찬가지로 관리 대상이기 때문에 테스트 코드를 통해 제공하는 것이 좋다고 생각했습니다.

### Linter

- 테스트 코드가 실행될 때마다 kotliner의 formatKotlin Task가 실행되도록 설정하였습니다.
- 사람이다보니 포맷에 대한 실수가 발생하기 때문에 자동화는 것이 좋다고 생각했습니다.
- klint 규칙 중에 좋아하지 않은 포맷팅은 `.editorconfig`에 예외를 두었습니다.

## 실행 방법

1. docker-compose.yaml을 통해 rabbitmq 실행

```
$ docker-compose up -d
```

2. gradlew를 통해 빌드 후 실행합니다.

```
$ ./gradlew build
$ java -jar build/libs/cash-note-1.0-SNAPSHOT.jar
```

3. 스웨거 접속 후 테스트를 진행합니다.

```
http://localhost:8080
```

4. 임의 데이터
아래와 같이 데이터를 미리 삽입하도록 `SampleInsertRunner`에 설정하였습니다.

|   사업자등록번호    | 약관 동의 여부 | 최초 데이터 송수신 여부 | 데일리 데이터 송수신 여부 |
|:------------:|:--------:|:-------------:|:--------------:|
| 111-11-11111 |    X     |       X       |       X        |
| 222-22-22222 |    O     |       X       |       X        |
| 333-33-33333 |    O     |       O       |       X        |
| 444-44-44444 |    O     |       O       |       O        |


## 구현 전략

### DB 스키마

```sql
# 약관 동의
CREATE TABLE term (
    registration_number VARCHAR(12) NOT NULL,
    agree_status VARCHAR(16),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (registration_number)
);
CREATE INDEX idx_created_at_term ON term(created_at);

# 카드 내역
CREATE TABLE card_transaction_history (
    approval_number VARCHAR(8) NOT NULL,
    transaction_type VARCHAR(16),
    transaction_date DATE,
    transaction_time TIME,
    card_company VARCHAR(16),
    affiliated_card_company VARCHAR(16),
    card_number VARCHAR(19),
    approval_amount INT,
    installment_period VARCHAR(10),
    provider VARCHAR(16),
    registration_number VARCHAR(12),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (approval_number),
    FOREIGN KEY (registration_number) REFERENCES community_sync_info (registration_number)
);
CREATE INDEX idx_created_at_card_transaction_history ON card_transaction_history(created_at);

# 데이터 동기화 정보
CREATE TABLE community_sync_info (
    registration_number VARCHAR(12) NOT NULL,
    sync_status VARCHAR(16),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (registration_number)
);
CREATE INDEX idx_created_at_community_sync_info ON community_sync_info(created_at);
```

### 패키지 구조

- `interfaces`: web 요청의 Controller 부분입니다.
- `application`: 비즈니스 로직 기능들을 모아두었습니다. 서비스의 접미사는 `service`로 사용하고 있습니다.
- `domain`: 도메인 기능들을 모아두었습니다.
    - `service`: 서비스라고 칭하기보다 어떤 일하는 객체로 이름을 두고 싶어서 반영하였습니다.
    - `model`: 도메인 모델과 Value Object를 모아두었습니다.
- `infrastructure`: 외부 인프라와 통신을 하는 객체들을 모아두었습니다.
    - `client`: httpClient의 기능을 사용하는 모듈을 모아두었습니다.
    - `database`: database에 접근하는 모듈을 모아두었습니다.
    - `queue`: queue에 접근하는 모듈을 모아두었습니다.

### 약관 동의 내역 저장 API

#### 핵심

- RDB는 트랜잭션을 통해 롤백을 할 수 있기 때문에 먼저 데이터를 저장한 이후에 공동체 API 호출하도록 구현하였습니다.

#### 개선할 점

- 사업자가 존재하는지 확인한 이후에 약관 정보를 저장하는 것이 안정적이라고 생각이 듭니다.

### 캐시노트 -> 공동체 데이터 송수신

#### 핵심

- Spring Scheduler를 활용하였습니다.
- 일일 스케줄러(어제 매출 정보 전송)와 최초 스케줄러(처음으로 매출 정보를 전송)을 구분하여 스케줄러를 실행하도록 하였습니다.
    - 일일 스케줄러와 최초 스케줄러가 수행되는 주기가 비즈니스상 다르게 설정할 수 있다고 생각이 들었습니다.
- 스케줄러에 비즈니스 로직을 담기보다 별도의 서비스를 선언하였습니다.
    - 최초 스케줄러의 경우 약관 동의하면 바로 데이터를 전달하고 싶을 수 있다고 생각이 들어 비즈니스 로직을 따로 빼놨습니다.
- 데이터 동기화에 대한 관리를 별도로 하는 것이 추후 로깅하는데 유리하다고 생각이 들어 별도로 저장하는 객체를 선언했습니다.
    - 동기화는 `약관 정보`를 보지 않고 `동기화 데이터`를 통해 동기화를 진행합니다.
- 카드내역의 근원지가 캐시노트인지 공동체인지 구분해서 전달하는게 효율적이라 생각이 들어 `Provider`를 추가하였습니다.

#### 개선할 점

- 배치를 Spring Batch나 에어플로우 같은 도구를 통해 배치 실행 컨텍스트를 남겨 관리하는 것이 좋다고 생각합니다.
- 데이터가 많을 것을 대비하여 chunk 단위로 처리하도록 구현하는 것이 좋을 듯 합니다.

### 공동체 -> 캐시노트 데이터 송수신

#### 핵심

- 간단한 설정을 통해 사용할 수 있는 RabbitMq를 선택하였습니다.

#### 개선할 점

- 데이터가 많아졌을 때 메시지 브로커를 카프카로 변경하여 관리를 하는 것이 더 좋다고 생각합니다.

### 그외
- 직접 테스트를 해볼 수 있게 구성하는데 시간을 많이 써서 테스트 코드를 많이 작성하지 못해 아쉬웠습니다.
