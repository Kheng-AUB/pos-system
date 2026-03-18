# 📊 POS Application - Architecture & Exception Flow Diagrams

## 1. Exception Handling Architecture

```
┌──────────────────────────────────────────────────────────────────┐
│                      CLIENT (Frontend)                            │
│  Makes HTTP Request with JWT Token in Authorization header       │
└─────────────────────────────┬──────────────────────────────────────┘
                              │
                              ↓
┌──────────────────────────────────────────────────────────────────┐
│                     SPRING SECURITY FILTER CHAIN                 │
│                                                                   │
│  ┌────────────────────────────────────────────────────────────┐  │
│  │ JwtValidator Filter (OncePerRequestFilter)                │  │
│  │                                                             │  │
│  │  1. Extract token from Authorization header               │  │
│  │     → getHeader("Authorization")                          │  │
│  │                                                             │  │
│  │  2. Validate & Parse token                                │  │
│  │     → JwtService.getEmailFromToken(token)                 │  │
│  │     → JwtService.getAuthoritiesFromToken(token)           │  │
│  │                                                             │  │
│  │  3. On Error → Throws AppException                         │  │
│  │     → Caught by GlobalExceptionHandler ✅                  │  │
│  │                                                             │  │
│  │  4. Set SecurityContext                                    │  │
│  │     → UsernamePasswordAuthenticationToken                  │  │
│  │                                                             │  │
│  └────────────────────────────────────────────────────────────┘  │
└─────────────────────────────┬──────────────────────────────────────┘
                              │
                              ↓
┌──────────────────────────────────────────────────────────────────┐
│                    CONTROLLER LAYER                              │
│                                                                   │
│  StoreController.createStore()                                  │
│    ↓                                                              │
│    Calls: userInfoService.getUserInfoFromJwtToken(token)        │
│    ↓                                                              │
│    UserInfo userInfo = response.getData()                       │
│    ↓                                                              │
│    Validates: if (userInfo == null) → throw AppException ✅      │
│    ↓                                                              │
│    Calls: storeService.createStore(storeDto, userInfo)          │
└─────────────────────────────┬──────────────────────────────────────┘
                              │
                              ↓
┌──────────────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                                 │
│                                                                   │
│  StoreServiceImpl.createStore()                                  │
│    ↓                                                              │
│    Validates input:                                              │
│      if (storeDto == null) → throw AppException                  │
│      if (userInfo == null) → throw AppException                  │
│    ↓                                                              │
│    Performs business logic                                       │
│      → StoreRepository.save()                                    │
│    ↓                                                              │
│    Returns BaseApiResponse with code="0", status="success"       │
└─────────────────────────────┬──────────────────────────────────────┘
                              │
                              ↓
┌──────────────────────────────────────────────────────────────────┐
│                   EXCEPTION HANDLER                              │
│          @RestControllerAdvice (Catches All Exceptions)          │
│                                                                   │
│  IF Exception Occurs at ANY level:                               │
│  ↓                                                                │
│  @ExceptionHandler(AppException.class)                           │
│    → Log: log.warn("AppException: {}", message)                 │
│    → Create ErrorResponse(code, status, msg)                    │
│    → Return ResponseEntity with HTTP status                      │
│                                                                   │
│  @ExceptionHandler(Exception.class)                              │
│    → Log: log.error("Internal server error", ex)  [Stack trace] │
│    → Return 500 Internal Server Error                            │
│                                                                   │
│  @ExceptionHandler(UserException.class)                          │
│    → Backward compatibility handler                              │
└─────────────────────────────┬──────────────────────────────────────┘
                              │
                              ↓
┌──────────────────────────────────────────────────────────────────┐
│                   HTTP RESPONSE                                  │
│                                                                   │
│  Success (200):                                                  │
│  ┌─────────────────────────────────────────────────────────┐     │
│  │ {                                                        │     │
│  │   "code": "0",                                           │     │
│  │   "status": "success",                                   │     │
│  │   "data": { ... }                                        │     │
│  │ }                                                        │     │
│  └─────────────────────────────────────────────────────────┘     │
│                                                                   │
│  Error (401/404/400/500):                                        │
│  ┌─────────────────────────────────────────────────────────┐     │
│  │ {                                                        │     │
│  │   "code": "USER_NOT_FOUND",                             │     │
│  │   "status": "fail",                                      │     │
│  │   "msg": "User not found"                               │     │
│  │ }                                                        │     │
│  └─────────────────────────────────────────────────────────┘     │
└──────────────────────────────────────────────────────────────────┘
                              │
                              ↓
┌──────────────────────────────────────────────────────────────────┐
│                      CLIENT (Frontend)                           │
│  Receives HTTP Response with:                                    │
│  ✅ Correct Status Code (200/401/404/etc)                        │
│  ✅ Error Code (for error handling logic)                        │
│  ✅ Error Message (for user display)                             │
└──────────────────────────────────────────────────────────────────┘
```

---

## 2. JWT Token Validation Flow

```
┌─────────────────────────────────────────────────────────┐
│  Client sends request:                                   │
│  GET /api/store                                          │
│  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...          │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────┐
│  JwtValidator Filter intercepts request                 │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ↓
    ┌─────────────────────────────────────┐
    │  Extract token from header          │
    │  jwt = request.getHeader("Auth...")  │
    └─────────────────┬───────────────────┘
                      │
                      ↓
    ┌──────────────────────────────────────────────┐
    │  Is jwt null or empty?                       │
    └──────────────────┬───────────────────────────┘
         YES           │           NO
         │             │
         ↓             ↓
    [CONTINUE] ┌───────────────────────────────────┐
               │  Call JwtService methods:          │
               │  1. getEmailFromToken(jwt)        │
               │  2. getAuthoritiesFromToken(jwt)  │
               └──────────────┬────────────────────┘
                              │
                              ↓
                    ┌─────────────────────────────────┐
                    │  Token validation logic:         │
                    │  ✓ Remove "Bearer " prefix      │
                    │  ✓ Check if empty               │
                    │  ✓ Parse JWT                    │
                    │  ✓ Extract email claim          │
                    │  ✓ Check for JwtException       │
                    └──────────────┬──────────────────┘
                                   │
                    ┌──────────────┴────────────────┐
                    │                               │
            Valid Token ✓              Invalid Token ✗
                    │                               │
                    ↓                               ↓
        ┌──────────────────────┐      ┌──────────────────────────────┐
        │ Extract:             │      │ Throw AppException:          │
        │ - email              │      │ - code: INVALID_TOKEN        │
        │ - authorities        │      │ - status: UNAUTHORIZED       │
        │ - roles              │      │ - msg: Invalid/expired token │
        └────────────┬─────────┘      └──────────────┬───────────────┘
                     │                               │
                     ↓                               ↓
        ┌──────────────────────────────────────┐  GlobalExceptionHandler
        │ Set SecurityContext with:            │  catches AppException
        │ Authentication auth =                │  → Returns 401 response
        │   new UsernamePasswordAuth...()      │
        │ SecurityContextHolder.getContext()   │
        │   .setAuthentication(auth)           │
        └────────────┬─────────────────────────┘
                     │
                     ↓
        ┌──────────────────────────────────┐
        │ Continue to Controller            │
        │ User is now authenticated!        │
        │ Can access /api/** endpoints      │
        └──────────────────────────────────┘
```

---

## 3. Store Creation Flow

```
┌────────────────────────────────────────────────────────────────┐
│  POST /api/store                                                │
│  Authorization: Bearer <valid_token>                            │
│  Body: { brand, description, storeType, contact }              │
└────────────────────┬───────────────────────────────────────────┘
                     │
                     ↓
┌────────────────────────────────────────────────────────────────┐
│  JwtValidator validates token → ✅ Authenticated               │
└────────────────────┬───────────────────────────────────────────┘
                     │
                     ↓
┌────────────────────────────────────────────────────────────────┐
│  StoreController.createStore(token, storeDto)                 │
└────────────────────┬───────────────────────────────────────────┘
                     │
                     ├─→ Call: userInfoService.getUserInfoFromJwtToken(token)
                     │         ↓
                     │   ┌─────────────────────────────────────┐
                     │   │ Extract email from token           │
                     │   │ Query: userRepository.findByEmail() │
                     │   └─────────────┬───────────────────────┘
                     │                 │
                     │         ┌───────┴────────┐
                     │         │                │
                     │    User Found ✓    User Not Found ✗
                     │         │                │
                     │         ↓                ↓
                     │    [Return UserInfo] Throw AppException
                     │                     code: USER_NOT_FOUND
                     │                     status: NOT_FOUND
                     │                     msg: "User not found"
                     │
                     ├─→ Validate userInfo: if (null) → throw
                     │
                     ├─→ Validate storeDto: if (null) → throw
                     │
                     └─→ Call: storeService.createStore(storeDto, userInfo)
                              ↓
                    ┌─────────────────────────────────────┐
                    │ StoreServiceImpl.createStore()       │
                    │                                      │
                    │ 1. Validate inputs:                 │
                    │    - storeDto != null?              │
                    │    - userInfo != null?              │
                    │    → throw AppException if not      │
                    │                                      │
                    │ 2. Map StoreDto to Store entity:    │
                    │    - StoreMapper.toEntity()         │
                    │    - Sets storeAdmin = userInfo     │
                    │    - Sets timestamps                │
                    │                                      │
                    │ 3. Save to database:                │
                    │    - storeRepository.save(store)    │
                    │                                      │
                    │ 4. Map back to StoreDto:            │
                    │    - StoreMapper.toDto()            │
                    │                                      │
                    │ 5. Set response:                    │
                    │    - response.setData(storeDto)     │
                    │    - response.isSuccess()           │
                    │    - response.code = "0"            │
                    │    - response.status = "success"    │
                    │                                      │
                    │ 6. Return BaseApiResponse           │
                    └──────────────┬──────────────────────┘
                                   │
                        ┌──────────┴──────────┐
                        │                     │
                    Success ✓            Exception ✗
                        │                     │
                        ↓                     ↓
            ┌──────────────────────┐  GlobalExceptionHandler
            │ Response (HTTP 200):  │  │
            │ {                     │  ├─→ Log error
            │   code: "0",          │  │
            │   status: "success",  │  ├─→ Create ErrorResponse
            │   data: {             │  │
            │     id: 1,            │  └─→ Return with proper
            │     brand: "Gito",    │      HTTP status
            │     ...               │      (400/401/500/etc)
            │   }                   │
            │ }                     │
            └──────────────────────┘
```

---

## 4. Error Handling Decision Tree

```
┌──────────────────────────────────────────────────────────┐
│  Exception/Error occurs in application                    │
└────────────────────┬─────────────────────────────────────┘
                     │
    ┌────────────────┼────────────────┐
    │                │                │
    ↓                ↓                ↓
 AppException   UserException   Any Other Exception
    │                │                │
    ↓                ↓                ↓
 ┌──┐          ┌──────┐          ┌────────┐
 │✓│          │     │           │  ✗    │
 └──┘          └──────┘          └────────┘
   │              │                 │
   ├─ Has:        ├─ Has:          └─ Unwrap and
   │ · code       │ · status          log stack trace
   │ · status     │ · message
   │ · message    │
   │ · HTTP code  │
   │              │
   ↓              ↓                   ↓
┌────────────┐ ┌─────────────┐ ┌────────────┐
│ HANDLED:   │ │ HANDLED:    │ │ HANDLED:   │
│ Returns    │ │ Returns     │ │ Returns    │
│ Response   │ │ Response    │ │ 500 Error  │
│ with code  │ │ with code   │ │ Response   │
│ & status   │ │ & status    │ │ (generic)  │
└──────┬─────┘ └──────┬──────┘ └──────┬─────┘
       │              │              │
       ↓              ↓              ↓
    ┌─────────────────────────────────┐
    │   HTTP Response to Client       │
    │   with proper status code       │
    │   and error information         │
    └─────────────────────────────────┘
```

---

## 5. Package Organization

```
com.kheng.pos/
│
├── configurations/
│   ├── jwt/
│   │   ├── constant/
│   │   │   └── JwtConstant.java        ← JWT constants (secret, header, etc)
│   │   │
│   │   ├── service/
│   │   │   └── JwtService.java         ← Centralized JWT logic
│   │   │
│   │   ├── JwtValidator.java           ← Spring Security filter
│   │   └── JwtProvider.java            ← Wrapper for backward compatibility
│   │
│   ├── security/
│   │   ├── config/
│   │   │   └── SecurityConfig.java     ← Spring Security configuration
│   │   └── service/
│   │       ├── contract/
│   │       │   └── AuthService.java
│   │       └── impl/
│   │           └── AuthServiceImpl.java
│   │
│   └── ...
│
├── exception/
│   ├── AppException.java               ← NEW: Proper exception with codes
│   ├── UserException.java              ← Legacy (kept for compatibility)
│   └── GlobalExceptionHandler.java     ← Catches and formats all exceptions
│
├── features/
│   ├── auth/
│   │   ├── controller/
│   │   │   └── AuthController.java     ← Signup/Login endpoints
│   │   ├── payload/
│   │   ├── dto/
│   │   └── mapper/
│   │
│   ├── store/
│   │   ├── controller/
│   │   │   └── StoreController.java    ← Store CRUD endpoints
│   │   ├── service/
│   │   │   ├── contract/
│   │   │   │   └── StoreService.java
│   │   │   └── impl/
│   │   │       └── StoreServiceImpl.java
│   │   ├── dto/
│   │   ├── mapper/
│   │   └── ...
│   │
│   └── user/
│       ├── controller/
│       │   └── UserInfoController.java ← User endpoints
│       ├── service/
│       │   ├── contract/
│       │   │   └── UserInfoService.java
│       │   └── impl/
│       │       └── UserInfoServiceImpl.java
│       └── ...
│
├── core/
│   └── dto/
│       └── BaseApiResponse.java        ← Response wrapper
│
├── databases/
│   ├── store/
│   │   ├── entities/
│   │   │   ├── Store.java
│   │   │   ├── StoreContact.java
│   │   │   └── StoreStatus.java
│   │   └── repositories/
│   │
│   └── user/
│       ├── entities/
│       └── repositories/
│
└── PosApplication.java
```

---

## 6. Data Flow: Token → User → Store

```
                    ┌─ JwtConstant.JWT_SECRET ──┐
                    │                             │
                    ↓                             ↓
        ┌─────────────────────────────────────────────┐
        │  Token in Authorization Header              │
        │  "Bearer eyJhbGciOiJIUzI1NiJ9..."          │
        └────────────────┬────────────────────────────┘
                         │
                         ↓
        ┌────────────────────────────────────────────┐
        │  JwtService validates signature            │
        │  with JwtConstant.JWT_SECRET               │
        └────────────┬───────────────────────────────┘
                     │
                     ↓
        ┌───────────────────────────────────────────┐
        │  Extract Claims from JWT payload:         │
        │  - email: "user@test.com"                │
        │  - authorities: "ROLE_ADMIN,ROLE_USER"   │
        └────────────┬──────────────────────────────┘
                     │
                     ↓
        ┌───────────────────────────────────────────┐
        │  Query UserInfoRepository with email      │
        │  findByEmail("user@test.com")            │
        └────────────┬──────────────────────────────┘
                     │
                     ↓
        ┌───────────────────────────────────────────┐
        │  Get UserInfo Entity from database        │
        │  {                                         │
        │    id: 1,                                  │
        │    email: "user@test.com",                │
        │    fullName: "John Doe",                  │
        │    role: "ROLE_STORE_OWNER",             │
        │    store: Store { id: 5, ... }           │
        │  }                                         │
        └────────────┬──────────────────────────────┘
                     │
                     ↓
        ┌───────────────────────────────────────────┐
        │  Use UserInfo to create Store             │
        │  store.setStoreAdmin(userInfo)            │
        │  storeRepository.save(store)              │
        └────────────┬──────────────────────────────┘
                     │
                     ↓
        ┌───────────────────────────────────────────┐
        │  Store saved with admin relationship      │
        │  {                                         │
        │    id: 100,                               │
        │    brand: "Gito",                         │
        │    storeAdmin: UserInfo { id: 1, ... }   │
        │    createdAt: "2026-03-17T10:30:00"      │
        │  }                                         │
        └───────────────────────────────────────────┘
```

---

## 7. Logging Points

```
Application Execution → Logging Checkpoint

Entry:
  DEBUG: Request comes in
  → log.debug("JWT token validated for user: {}", email)

Service Processing:
  INFO:  Processing business logic
  → log.info("Creating store for user: {}", userId)

Success:
  INFO:  Operation completed successfully
  → log.info("Store created successfully with ID: {}", storeId)

Warning:
  WARN:  Recoverable issue detected
  → log.warn("AppException: User not found")
  → log.warn("Store not found for admin")

Error:
  ERROR: Unexpected error with full stack trace
  → log.error("Internal server error", exception)
  → [Full Java stack trace printed]

Application Logs Location: 
  → Console (development)
  → log files (production setup required)
  → Application logs in IDE console
```

---

**🎯 Summary:** Your exception handling now flows through a proper architecture with centralized JWT service, structured exceptions, and comprehensive error responses!

