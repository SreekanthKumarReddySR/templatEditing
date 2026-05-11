import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useAuthStore } from './store/store';
import { authAPI } from './services/api';

// Pages
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import TemplatesPage from './pages/TemplatesPage';
import SharePage from './pages/SharePage';

// Protected Route Component
const ProtectedRoute = ({ children, isAuthenticated }) => {
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  return children;
};

export function App() {
  const { token, setUser, isAuthenticated } = useAuthStore();

  useEffect(() => {
    // Fetch user data if token exists
    if (token) {
      const fetchUserData = async () => {
        try {
          const response = await authAPI.getCurrentUser();
          if (response.data.success) {
            setUser(response.data.data);
          }
        } catch (error) {
          console.error('Failed to fetch user data', error);
        }
      };
      fetchUserData();
    }
  }, [token, setUser]);

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route
          path="/templates"
          element={
            <ProtectedRoute isAuthenticated={isAuthenticated}>
              <TemplatesPage />
            </ProtectedRoute>
          }
        />
        <Route path="/share/:greetingId" element={<SharePage />} />
        <Route path="/" element={<Navigate to="/templates" replace />} />
      </Routes>
    </Router>
  );
}

export default App;
