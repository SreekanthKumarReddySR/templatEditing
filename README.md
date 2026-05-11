# ClassPlus Custom Greetings & Wishes App

A full-stack web application that enables users to create personalized greeting cards and wishes with profile picture overlays and easy sharing capabilities.

## 🎯 Features

### Core Features
- **User Authentication**: Email registration, login, Google OAuth, and guest mode
- **Template Library**: 100+ categorized templates (Birthday, Anniversary, Festivals, etc.)
- **Image Overlay**: Automatic overlay of user profile picture and name on templates
- **Personalization**: Custom text and image personalization with live preview
- **Social Sharing**: Share on WhatsApp, Instagram, Twitter, Email, and SMS
- **Premium Features**: Subscription-based access to exclusive premium templates
- **Responsive Design**: Beautiful UI optimized for mobile and desktop

## 🚀 Tech Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Database**: MongoDB Atlas (Cloud)
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security, BCrypt password encryption
- **Language**: Java 17

### Frontend
- **Framework**: React 18
- **Styling**: Tailwind CSS
- **State Management**: Zustand
- **HTTP Client**: Axios
- **Routing**: React Router v6
- **Build Tool**: Vite
- **Canvas Rendering**: HTML2Canvas for image processing

### DevOps & Deployment
- **Version Control**: Git/GitHub
- **Deployment**: Render (Backend & Frontend)
- **CI/CD**: GitHub Actions (ready for setup)

## 📋 Prerequisites
- Java 17 or higher
git clone https://github.com/yourusername/classplus-greetings-app.git
cd classplus-greetings-app
```

### 2. Backend Setup

#### Install Dependencies & Run

```bash
cd backend

# Maven will automatically download dependencies
# The pom.xml includes all required libraries

# Run the application
mvn spring-boot:run
```

The backend will start at `http://localhost:8080/api`

#### Database Configuration

The MongoDB connection string is already configured in `application.yml`:
```yaml
spring.data.mongodb.uri: mongodb+srv://sreekanthreddy:1234@cluster0.dsvnbbm.mongodb.net/?appName=Cluster0
```

### 3. Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will start at `http://localhost:3000`

### 4. Access the Application

- **Home Page**: http://localhost:3000
- **Login**: http://localhost:3000/login
- **Register**: http://localhost:3000/register
- **Templates**: http://localhost:3000/templates (requires login)

## 📚 API Documentation

### Authentication Endpoints

```
POST /api/auth/register - Register new user
POST /api/auth/login - Login with email/password
POST /api/auth/google - Google OAuth login
POST /api/auth/guest - Guest login
GET /api/auth/me - Get current user profile
```

### Template Endpoints

```
GET /api/templates - Get all templates
GET /api/templates/category/{category} - Get templates by category
GET /api/templates/premium - Get premium templates
GET /api/templates/free - Get free templates
GET /api/templates/{id} - Get specific template
```

### Greeting Endpoints

```
POST /api/greetings/create - Create personalized greeting
POST /api/greetings/share - Share greeting
GET /api/greetings/my-greetings - Get user's greetings
GET /api/greetings/{greetingId} - Get specific greeting
GET /api/greetings/share-link/{greetingId} - Generate share link
```

### Subscription Endpoints

```
POST /api/subscriptions/create?planType={MONTHLY|ANNUAL} - Create subscription
GET /api/subscriptions/my-subscription - Get user's subscription
POST /api/subscriptions/cancel - Cancel subscription
```

## 🏗️ Project Structure

```
classplus-greetings-app/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/classplus/greetings/
│   │   │   │   ├── ClassPlusGreetingsApplication.java
│   │   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── service/             # Business Logic
│   │   │   │   ├── repository/          # Data Access
│   │   │   │   ├── model/               # Entity Models
│   │   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── security/            # JWT & Security
│   │   │   │   └── util/                # Utility Classes
│   │   │   └── resources/
│   │   │       └── application.yml      # Configuration
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/                  # Reusable Components
│   │   ├── pages/                       # Page Components
│   │   ├── services/                    # API Services
│   │   ├── store/                       # Zustand Store
│   │   ├── utils/                       # Utility Functions
│   │   ├── App.jsx                      # Main App Component
│   │   ├── main.jsx                     # Entry Point
│   │   └── index.css                    # Global Styles
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.js
│   └── postcss.config.js
├── .gitignore
└── README.md
```

## 🎨 UI/UX Design Principles

### Design System
- **Color Scheme**: 
  - Primary: Blue (#3B82F6)
  - Secondary: Purple (#8B5CF6)
  - Accent: Yellow for premium features

### Components
- **Borders**: Thin, subtle borders (1px) for clean look
- **Spacing**: Consistent spacing following 4px grid system
- **Typography**: Clear hierarchy with appropriate font sizes
- **Icons**: Emoji-based icons for better engagement
- **Animations**: Smooth transitions and hover effects

### Pages

#### 1. **Authentication Pages**
- Login & Registration with gradient backgrounds
- Guest login option
- Professional card-based layouts

#### 2. **Templates Page**
- Grid layout with responsive design
- Filter by Free/Premium/All
- Live preview with hover effects
- Premium badge with unlock functionality

#### 3. **Personalization Page**
- Two-column layout (form & preview)
- Real-time preview update
- Profile picture circle overlay
- Custom message support

#### 4. **Share Page**
- Social media share buttons
- Direct link sharing
- View count tracking
- Beautiful greeting display

## 🔐 Security Features

- JWT-based authentication with 24-hour token expiry
- BCrypt password hashing
- CORS configured for cross-origin requests
- Authorization checks for premium features
- Input validation on all endpoints
- SQL injection prevention (MongoDB)

## 📱 Responsive Design

- Mobile-first approach
- Optimized for screens: 320px, 768px, 1024px, 1280px
- Touch-friendly buttons and inputs
- Flexible grid layouts

## 🚀 Deployment to Render

### Backend Deployment

1. **Push to GitHub**
```bash
git add .
git commit -m "Initial commit"
git push origin main
```

2. **Create Render Service**
- Go to [render.com](https://render.com)
- Sign in with GitHub
- Create new "Web Service"
- Connect repository
- Configure:
  - Build Command: `mvn clean install`
  - Start Command: `java -jar target/greetings-app-1.0.0.jar`
  - Add environment variables (if needed)

### Frontend Deployment

1. **Build the frontend**
```bash
cd frontend
npm run build
```

2. **Deploy to Render**
- Create new "Static Site"
- Connect GitHub repository
- Build Command: `npm install && npm run build`
- Publish Directory: `dist`

## 📊 Database Schema

### Users Collection
```javascript
{
  _id: ObjectId,
  name: String,
  email: String (unique),
  password: String (hashed),
  profilePictureUrl: String,
  authProvider: String,
  subscriptionTier: String,
  subscriptionExpiryDate: Date,
  createdAt: Date,
  updatedAt: Date
}
```

### Templates Collection
```javascript
{
  _id: ObjectId,
  title: String,
  category: String,
  imageUrl: String,
  imageThumbnailUrl: String,
  isPremium: Boolean,
  likes: Number,
  shares: Number,
  createdAt: Date
}
```

### SharedGreetings Collection
```javascript
{
  _id: ObjectId,
  userId: String,
  templateId: String,
  personalizationData: String (JSON),
  personalizedImageUrl: String,
  sharedWith: String,
  recipientInfo: String,
  viewCount: Number,
  sharedAt: Date
}
```

### Subscriptions Collection
```javascript
{
  _id: ObjectId,
  userId: String,
  planType: String,
  amount: Number,
  startDate: Date,
  expiryDate: Date,
  status: String,
  createdAt: Date
}
```

## 🧪 Testing

### Backend Testing
```bash
cd backend
mvn test
```

### Frontend Testing
```bash
cd frontend
npm test
```

## 📈 Advanced Concepts Implemented

### Backend
1. **Layered Architecture**: Controller → Service → Repository pattern
2. **JWT Authentication**: Stateless authentication with token-based approach
3. **MongoDB Aggregation**: Efficient database queries
4. **Dependency Injection**: Spring's DI container for loose coupling
5. **Exception Handling**: Global exception handler for consistent error responses
6. **Logging**: SLF4J with structured logging

### Frontend
1. **State Management**: Zustand for global state with minimal boilerplate
2. **Component Composition**: Reusable components with props-based design
3. **Custom Hooks**: React hooks for code reuse
4. **Axios Interceptors**: Request/response interceptors for authentication
5. **Lazy Loading**: Image optimization with thumbnail fallbacks
6. **Canvas API**: Real-time image overlay rendering

## 🐛 Troubleshooting

### MongoDB Connection Issues
- Verify MongoDB Atlas credentials
- Check IP whitelist on MongoDB Atlas
- Ensure network connectivity

### CORS Errors
- Backend CORS is configured to allow all origins
- Check browser console for detailed error messages

### Build Failures
- Ensure Java 17+ is installed: `java -version`
- Clear Maven cache: `mvn clean install`
- Clear npm cache: `npm cache clean --force`

## 📝 Future Enhancements

1. **Image Processing**
   - Server-side image overlay using ImageMagick
   - Better quality personalized images
   - Batch processing for multiple users

2. **Analytics**
   - User engagement tracking
   - Template popularity metrics
   - Revenue analytics dashboard

3. **Features**
   - Video greetings
   - Animated templates
   - Bulk sharing
   - Scheduled sending

4. **Performance**
   - Redis caching for frequently accessed templates
   - CDN integration for image delivery
   - Websocket for real-time updates

## 📄 License

This project is provided as an internship assignment for ClassPlus.

## 👥 Contributors

- Your Name - Full Stack Developer

## 📧 Support

For issues and questions, please create an issue in the GitHub repository.

---

**Happy Greeting Creating! 🎉**

Made with ❤️ for ClassPlus
#   t e m p l a t E d i t i n g 
 
 #   t e m p l a t E d i t i n g 
 
 