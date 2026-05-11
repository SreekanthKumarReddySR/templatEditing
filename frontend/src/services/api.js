import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
});

// Add token to requests
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Handle responses
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth Service
export const authAPI = {
  register: (data) => api.post('/auth/register', data),
  login: (data) => api.post('/auth/login', data),
  googleAuth: (data) => api.post('/auth/google', data),
  guestLogin: () => api.post('/auth/guest'),
  getCurrentUser: () => api.get('/auth/me'),
};

// Template Service
export const templateAPI = {
  getAllTemplates: () => api.get('/templates'),
  getTemplatesByCategory: (category) => api.get(`/templates/category/${category}`),
  getPremiumTemplates: () => api.get('/templates/premium'),
  getFreeTemplates: () => api.get('/templates/free'),
  getTemplateById: (id) => api.get(`/templates/${id}`),
};

// Greeting Service
export const greetingAPI = {
  createGreeting: (data) => api.post('/greetings/create', data),
  shareGreeting: (data) => api.post('/greetings/share', data),
  getUserGreetings: () => api.get('/greetings/my-greetings'),
  getGreeting: (id) => api.get(`/greetings/${id}`),
  getShareLink: (id) => api.get(`/greetings/share-link/${id}`),
};

// Subscription Service
export const subscriptionAPI = {
  createSubscription: (planType) => api.post('/subscriptions/create', null, { params: { planType } }),
  getMySubscription: () => api.get('/subscriptions/my-subscription'),
  cancelSubscription: () => api.post('/subscriptions/cancel'),
};

export default api;
