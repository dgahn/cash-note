# 데이터 연동 API 구현
## 캐시노트
### 간편 연결 대상 확인 동의 조회 API
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
POST /api/v1/terms/agreements
```
#### Request
```json
{
    "registrationNumber": "사업자등록번호",
    "agreedType": true
}
```
