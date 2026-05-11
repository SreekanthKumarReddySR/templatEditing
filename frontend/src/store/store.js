import { create } from 'zustand';

export const useAuthStore = create((set) => ({
  user: null,
  token: localStorage.getItem('token') || null,
  isAuthenticated: !!localStorage.getItem('token'),
  isLoading: false,

  setUser: (user) => set({ user }),
  setToken: (token) => {
    if (token) {
      localStorage.setItem('token', token);
    } else {
      localStorage.removeItem('token');
    }
    set({ token, isAuthenticated: !!token });
  },
  setLoading: (isLoading) => set({ isLoading }),
  logout: () => {
    localStorage.removeItem('token');
    set({ user: null, token: null, isAuthenticated: false });
  },
}));

export const useTemplateStore = create((set) => ({
  templates: [],
  selectedTemplate: null,
  isLoading: false,
  filter: 'all', // all, free, premium

  setTemplates: (templates) => set({ templates }),
  setSelectedTemplate: (template) => set({ selectedTemplate: template }),
  setLoading: (isLoading) => set({ isLoading }),
  setFilter: (filter) => set({ filter }),
}));

export const useGreetingStore = create((set) => ({
  currentGreeting: null,
  myGreetings: [],
  isLoading: false,
  personalizationData: {
    userName: '',
    userProfileImageUrl: '',
    customText: '',
  },

  setCurrentGreeting: (greeting) => set({ currentGreeting: greeting }),
  setMyGreetings: (greetings) => set({ myGreetings: greetings }),
  setLoading: (isLoading) => set({ isLoading }),
  setPersonalizationData: (data) => set({ personalizationData: data }),
}));

export const useUIStore = create((set) => ({
  showPremiumPopup: false,
  showShareModal: false,
  showSubscriptionModal: false,
  notification: null,

  setShowPremiumPopup: (show) => set({ showPremiumPopup: show }),
  setShowShareModal: (show) => set({ showShareModal: show }),
  setShowSubscriptionModal: (show) => set({ showSubscriptionModal: show }),
  setNotification: (notification) => set({ notification }),
}));
