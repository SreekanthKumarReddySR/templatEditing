# ClassPlus Greetings App - Local Setup Guide

## Quick Start (5 Minutes)

### Prerequisites
- Java 17+
- Node.js 16+
- MongoDB Atlas (connection string provided in application.yml)

### Step 1: Start Frontend (Port 3000)
```bash
cd frontend
npm install
npm run dev
```
Open: http://localhost:3000

### Step 2: Start Backend (Port 8080)
```bash
cd backend
mvn spring-boot:run
```
Backend ready: http://localhost:8080/api/templates

## Testing Flow

### 1. **User Registration & Authentication**
```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "Test@123456"
}
```

Response: JWT token for login

### 2. **User Login**
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "Test@123456"
}
```

Response: JWT token

### 3. **Guest Login**
```bash
POST http://localhost:8080/api/auth/guest
```

Response: Guest JWT token

### 4. **Get Current User**
```bash
GET http://localhost:8080/api/auth/me
Authorization: Bearer {token}
```

### 5. **Get All Templates**
```bash
GET http://localhost:8080/api/templates
```

### 6. **Get Free Templates**
```bash
GET http://localhost:8080/api/templates/free
```

### 7. **Get Premium Templates**
```bash
GET http://localhost:8080/api/templates/premium
```

### 8. **Create Personalized Greeting**
```bash
POST http://localhost:8080/api/greetings/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "templateId": "template-id-here",
  "userName": "John",
  "userProfileImageUrl": "https://example.com/avatar.jpg",
  "customText": "Happy Birthday!"
}
```

### 9. **Share Greeting**
```bash
POST http://localhost:8080/api/greetings/share
Authorization: Bearer {token}
Content-Type: application/json

{
  "greetingId": "greeting-id-here",
  "platform": "WHATSAPP",
  "recipientPhone": "+919876543210"
}
```

### 10. **Subscribe to Premium**
```bash
POST http://localhost:8080/api/subscriptions/create?planType=MONTHLY
Authorization: Bearer {token}
```

## Frontend Features Available

1. **Login/Register Page** - Create account or login
2. **Templates Page** - Browse free/premium templates
3. **Template Selection** - Choose template, customize with name and message
4. **Image Preview** - Live canvas preview of personalized greeting
5. **Share Modal** - Share via WhatsApp, Email, Instagram, Twitter
6. **My Greetings** - View shared greetings history
7. **Premium Popup** - Subscribe to unlock premium templates

## Key API Endpoints

| Method | Endpoint | Auth Required | Purpose |
|--------|----------|---------------|---------|
| POST | /api/auth/register | No | Create new account |
| POST | /api/auth/login | No | User login |
| POST | /api/auth/guest | No | Guest access |
| GET | /api/auth/me | Yes | Get current user |
| GET | /api/templates | No | List all templates |
| GET | /api/templates/free | No | List free templates |
| GET | /api/templates/premium | No | List premium templates |
| GET | /api/templates/{id} | No | Get single template |
| POST | /api/greetings/create | Yes | Create greeting |
| POST | /api/greetings/share | Yes | Share greeting |
| GET | /api/greetings/my-greetings | Yes | User's greetings |
| POST | /api/subscriptions/create | Yes | Create subscription |
| GET | /api/subscriptions/my-subscription | Yes | Get subscription |
| POST | /api/subscriptions/cancel | Yes | Cancel subscription |

## Troubleshooting

### Backend won't start
- Ensure Java 17 is installed: `java -version`
- Check MongoDB connection string in `backend/src/main/resources/application.yml`
- Port 8080 is available: `netstat -ano | findstr :8080`

### Frontend won't start
- Ensure Node.js is installed: `node -v`
- Clear npm cache: `npm cache clean --force`
- Delete node_modules and reinstall: `rm -r node_modules && npm install`

### MongoDB connection issues
- Verify connection string in application.yml
- Check IP whitelist on MongoDB Atlas
- Ensure cluster is active

## Test Credentials

Use these to test:
- Email: `test@example.com`
- Password: `Test@123456`

Or register new account in UI.
