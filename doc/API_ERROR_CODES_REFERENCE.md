# 🚀 Quick Reference - POS API Error Codes & Response Format

## Response Structure
```json
{
  "code": "0|1|404|CUSTOM_CODE",
  "status": "success|fail",
  "msg": "Human readable message",
  "data": {}  // Only on success
}
```

---

## HTTP Status Codes

| Code | Meaning | When Used |
|------|---------|-----------|
| **200** | OK | Request successful |
| **400** | Bad Request | Invalid data, empty fields |
| **401** | Unauthorized | Invalid/expired token, wrong password |
| **403** | Forbidden | User not allowed (e.g., non-admin signup) |
| **404** | Not Found | Resource doesn't exist |
| **409** | Conflict | Resource already exists |
| **500** | Internal Error | Unexpected server error |

---

## Error Codes & Messages

### Authentication Errors (401)
```
code: "INVALID_TOKEN"
msg: "Invalid or expired token"

code: "INVALID_EMAIL"
msg: "Email is required"

code: "INVALID_PASSWORD"
msg: "Password is required"

code: "INVALID_CREDENTIALS"
msg: "Invalid email or password"
```

### User Errors (404)
```
code: "USER_NOT_FOUND"
msg: "User not found"

code: "EMPLOYEE_NOT_FOUND"
msg: "Employee not found"
```

### Store Errors (404)
```
code: "STORE_NOT_FOUND"
msg: "Store not found"

code: "STORE_NOT_FOUND"
msg: "Employee has no associated store"
```

### Conflict Errors (409)
```
code: "USER_EXISTS"
msg: "User with this email already registered!"
```

### Permission Errors (403)
```
code: "ADMIN_REGISTRATION_DENIED"
msg: "You are not allowed to register as admin!"
```

### Validation Errors (400)
```
code: "INVALID_DATA"
msg: "Store data is required"

code: "INVALID_ID"
msg: "Invalid store ID"

code: "INVALID_STATUS"
msg: "Store status is required"

code: "INVALID_USER"
msg: "User information is required"
```

---

## Common API Flows

### 1️⃣ Create Store (POST /api/store)
```bash
Request:
POST /api/store
Authorization: Bearer <token>
Content-Type: application/json

{
  "brand": "Store Name",
  "description": "Description",
  "storeType": "Type",
  "contact": {
    "address": "Address",
    "email": "email@test.com",
    "phone": "123456789"
  }
}

Success Response (200):
{
  "code": "0",
  "status": "success",
  "data": { store object }
}

Error Response (401):
{
  "code": "INVALID_TOKEN",
  "status": "fail",
  "msg": "Invalid or expired token"
}

Error Response (404):
{
  "code": "USER_NOT_FOUND",
  "status": "fail",
  "msg": "User not found"
}

Error Response (400):
{
  "code": "INVALID_DATA",
  "status": "fail",
  "msg": "Store data is required"
}
```

### 2️⃣ Login (POST /auth/login)
```bash
Request:
POST /auth/login
Content-Type: application/json

{
  "email": "user@test.com",
  "password": "password123"
}

Success Response (200):
{
  "code": "0",
  "status": "success",
  "data": {
    "jwt": "eyJhbGciOiJIUzI1NiJ9...",
    "message": "User logged in successfully!",
    "userInfo": { user object }
  }
}

Error Response (401):
{
  "code": "INVALID_CREDENTIALS",
  "status": "fail",
  "msg": "Invalid email or password"
}

Error Response (400):
{
  "code": "INVALID_EMAIL",
  "status": "fail",
  "msg": "Email is required"
}
```

### 3️⃣ Signup (POST /auth/signup)
```bash
Request:
POST /auth/signup
Content-Type: application/json

{
  "email": "new@test.com",
  "password": "password123",
  "fullName": "User Name",
  "phone": "123456789",
  "role": "ROLE_ADMIN|ROLE_EMPLOYEE|ROLE_STORE_OWNER"
}

Success Response (200):
{
  "code": "0",
  "status": "success",
  "data": {
    "jwt": "eyJhbGciOiJIUzI1NiJ9...",
    "message": "User registered successfully!",
    "userInfo": { user object }
  }
}

Error Response (409):
{
  "code": "USER_EXISTS",
  "status": "fail",
  "msg": "User with this email already registered!"
}

Error Response (403):
{
  "code": "ADMIN_REGISTRATION_DENIED",
  "status": "fail",
  "msg": "You are not allowed to register as admin!"
}
```

### 4️⃣ Get User Profile (GET /api/user/profile)
```bash
Request:
GET /api/user/profile
Authorization: Bearer <token>

Success Response (200):
{
  "code": "0",
  "status": "success",
  "data": { user object }
}

Error Response (401):
{
  "code": "INVALID_TOKEN",
  "status": "fail",
  "msg": "Invalid or expired token"
}

Error Response (404):
{
  "code": "USER_NOT_FOUND",
  "status": "fail",
  "msg": "User not found"
}
```

### 5️⃣ Get All Stores (GET /api/store)
```bash
Request:
GET /api/store
Authorization: Bearer <token>

Success Response (200):
{
  "code": "0",
  "status": "success",
  "data": [
    { store object },
    { store object }
  ]
}

Error Response (401):
{
  "code": "INVALID_TOKEN",
  "status": "fail",
  "msg": "Invalid or expired token"
}
```

---

## Debugging Tips 🔍

### 1. Check Application Logs
```
WARN  com.kheng.pos.exception.GlobalExceptionHandler - AppException: User not found
ERROR com.kheng.pos.exception.GlobalExceptionHandler - Internal server error
java.lang.NullPointerException: ...
```

### 2. Verify Token Format
```
✅ CORRECT: Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3RAZ21haWwuY29tIn0...
❌ WRONG:   eyJhbGciOiJIUzI1NiJ9... (missing "Bearer ")
❌ WRONG:   Bearer invalid_token
```

### 3. Common Issues
| Issue | Solution |
|-------|----------|
| Always getting "User not found" | Check if user exists in DB and token is from that user |
| "Invalid token" | Token expired or malformed, login again |
| HTTP 500 instead of error code | Check application logs for stack trace |
| "CORS error" in frontend | Verify CORS config allows your domain |

---

## How Exception Handling Works ⚙️

```
1. Request comes in
   ↓
2. JwtValidator filter validates token
   → Throws AppException if invalid
   ↓
3. Controller method executes
   → Throws AppException on error
   ↓
4. GlobalExceptionHandler catches it
   → Logs the error
   → Returns structured error response with HTTP status
   ↓
5. Client receives proper error response
```

---

## Build & Deploy ✅

```bash
# Build
mvnw clean package

# Run
java -jar target/pos-0.0.1-SNAPSHOT.jar

# Or use Spring Boot
mvnw spring-boot:run
```

---

## Token Generation (Behind the Scenes)

```
1. User logs in successfully
2. JwtService.generateToken(authentication) creates JWT with:
   - email: user@test.com
   - authorities: ROLE_ADMIN,ROLE_STORE_OWNER
   - issuedAt: 2026-03-17T10:30:00
   - expiration: 2026-03-18T10:30:00 (24 hours)
3. JWT signed with secret: Lg2wNGuFaSqTJlke2THH7vLwl6IHmZ5o8Ftl7tGFaMF
4. Sent to client as: "Bearer <jwt_token>"
5. Client includes in Authorization header for all requests
```

---

## Success! 🎉

Your API now has:
✅ Proper error codes
✅ Correct HTTP status codes
✅ Structured error responses
✅ Full error logging
✅ Token validation
✅ Exception handling
✅ Production-ready!

