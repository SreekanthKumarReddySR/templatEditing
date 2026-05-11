import React, { useState, useEffect } from 'react';
import { templateAPI, greetingAPI, subscriptionAPI } from '../services/api';
import { useAuthStore, useGreetingStore, useUIStore } from '../store/store';
import Navbar from '../components/Navbar';
import TemplateCard from '../components/TemplateCard';
import ImagePreview from '../components/ImagePreview';
import PremiumPopup from '../components/PremiumPopup';
import ShareModal from '../components/ShareModal';

export const TemplatesPage = () => {
  const { user, logout, token } = useAuthStore();
  const { personalizationData, setPersonalizationData } = useGreetingStore();
  const { showPremiumPopup, setShowPremiumPopup, showShareModal, setShowShareModal } = useUIStore();

  const [templates, setTemplates] = useState([]);
  const [selectedTemplate, setSelectedTemplate] = useState(null);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('all');
  const [step, setStep] = useState('templates'); // templates, personalize, preview
  const [userName, setUserName] = useState(user?.name || '');
  const [userProfileImageUrl, setUserProfileImageUrl] = useState(user?.profilePictureUrl || '');
  const [customText, setCustomText] = useState('');
  const [createdGreeting, setCreatedGreeting] = useState(null);

  useEffect(() => {
    fetchTemplates();
  }, [filter]);

  const fetchTemplates = async () => {
    setLoading(true);
    try {
      let response;
      if (filter === 'free') {
        response = await templateAPI.getFreeTemplates();
      } else if (filter === 'premium') {
        response = await templateAPI.getPremiumTemplates();
      } else {
        response = await templateAPI.getAllTemplates();
      }

      if (response.data.success) {
        setTemplates(response.data.data);
      }
    } catch (error) {
      console.error('Failed to fetch templates', error);
    } finally {
      setLoading(false);
    }
  };

  const handleTemplateSelect = (template) => {
    if (template.isPremium && user?.subscriptionTier !== 'PREMIUM') {
      setShowPremiumPopup(true);
      return;
    }
    setSelectedTemplate(template);
    setStep('personalize');
  };

  const handleCreateGreeting = async () => {
    if (!selectedTemplate || !userName.trim()) {
      alert('Please fill in all required fields');
      return;
    }

    try {
      const response = await greetingAPI.createGreeting({
        templateId: selectedTemplate.id,
        userName,
        userProfileImageUrl,
        customText,
      });

      if (response.data.success) {
        setCreatedGreeting(response.data.data);
        setStep('preview');
      }
    } catch (error) {
      console.error('Failed to create greeting', error);
      alert('Failed to create greeting');
    }
  };

  const handleShare = async (platform) => {
    if (!createdGreeting) return;

    try {
      await greetingAPI.shareGreeting({
        greetingId: createdGreeting.greetingId,
        platform: platform.toUpperCase(),
      });

      setShowShareModal(false);
      alert(`Greeting shared successfully on ${platform}!`);
    } catch (error) {
      console.error('Failed to share greeting', error);
      alert('Failed to share greeting');
    }
  };

  const handleSubscribe = async (planType) => {
    try {
      const response = await subscriptionAPI.createSubscription(planType);
      if (response.data.success) {
        alert('Subscription created successfully!');
        setShowPremiumPopup(false);
        // Reload user data
        window.location.reload();
      }
    } catch (error) {
      console.error('Failed to create subscription', error);
      alert('Failed to create subscription');
    }
  };

  if (!token) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <p className="text-xl text-gray-600 mb-4">Please log in to continue</p>
          <a href="/login" className="text-blue-600 hover:text-blue-700 font-semibold">
            Go to login
          </a>
        </div>
      </div>
    );
  }

  return (
    <div>
      <Navbar user={user} onLogout={logout} />

      <div className="min-h-screen bg-gray-50">
        {/* Step 1: Templates */}
        {step === 'templates' && (
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
            {/* Header */}
            <div className="mb-10">
              <h1 className="text-4xl font-bold text-gray-900 mb-4">Choose a Template</h1>
              <p className="text-gray-600 mb-6">Select from our collection of beautiful greeting templates</p>

              {/* Filters */}
              <div className="flex space-x-3">
                {['all', 'free', 'premium'].map((f) => (
                  <button
                    key={f}
                    onClick={() => setFilter(f)}
                    className={`px-4 py-2 rounded-lg font-medium transition ${
                      filter === f
                        ? 'bg-blue-500 text-white'
                        : 'bg-white text-gray-700 border border-gray-300 hover:border-blue-300'
                    }`}
                  >
                    {f.charAt(0).toUpperCase() + f.slice(1)}
                  </button>
                ))}
              </div>
            </div>

            {/* Templates Grid */}
            {loading ? (
              <div className="flex justify-center items-center h-64">
                <div className="text-gray-600">Loading templates...</div>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                {templates.map((template) => (
                  <TemplateCard
                    key={template.id}
                    template={template}
                    onSelect={() => handleTemplateSelect(template)}
                    isPremium={template.isPremium}
                    isSelected={selectedTemplate?.id === template.id}
                  />
                ))}
              </div>
            )}

            {!loading && templates.length === 0 && (
              <div className="text-center py-12">
                <p className="text-gray-600">No templates found</p>
              </div>
            )}
          </div>
        )}

        {/* Step 2: Personalize */}
        {step === 'personalize' && selectedTemplate && (
          <div className="max-w-4xl mx-auto px-4 py-12">
            <button
              onClick={() => setStep('templates')}
              className="mb-6 text-blue-600 hover:text-blue-700 font-semibold flex items-center space-x-2"
            >
              <span>←</span>
              <span>Back to Templates</span>
            </button>

            <div className="bg-white rounded-lg shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-900 mb-6">Personalize Your Greeting</h2>

              <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                {/* Form */}
                <div className="space-y-6">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">Your Name</label>
                    <input
                      type="text"
                      value={userName}
                      onChange={(e) => setUserName(e.target.value)}
                      placeholder="Enter your name"
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">Profile Picture URL</label>
                    <input
                      type="url"
                      value={userProfileImageUrl}
                      onChange={(e) => setUserProfileImageUrl(e.target.value)}
                      placeholder="https://example.com/photo.jpg"
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">Custom Message (Optional)</label>
                    <textarea
                      value={customText}
                      onChange={(e) => setCustomText(e.target.value)}
                      placeholder="Add a personal message..."
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 h-24 resize-none"
                    />
                  </div>

                  <button
                    onClick={handleCreateGreeting}
                    className="w-full bg-gradient-to-r from-blue-500 to-purple-600 text-white font-semibold py-3 rounded-lg hover:shadow-lg transition"
                  >
                    Preview Greeting
                  </button>
                </div>

                {/* Preview */}
                <div>
                  <p className="text-sm font-medium text-gray-700 mb-4">Live Preview</p>
                  <div className="border border-gray-300 rounded-lg overflow-hidden">
                    <img
                      src={selectedTemplate.imageUrl}
                      alt="Template Preview"
                      className="w-full h-auto"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* Step 3: Preview */}
        {step === 'preview' && createdGreeting && (
          <div className="max-w-4xl mx-auto px-4 py-12">
            <button
              onClick={() => setStep('personalize')}
              className="mb-6 text-blue-600 hover:text-blue-700 font-semibold flex items-center space-x-2"
            >
              <span>←</span>
              <span>Back to Edit</span>
            </button>

            <div className="bg-white rounded-lg shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-900 mb-6">Your Greeting is Ready!</h2>

              <div className="mb-8">
                <img
                  src={createdGreeting.personalizedImageUrl}
                  alt="Your Greeting"
                  className="w-full max-h-96 object-cover rounded-lg"
                />
              </div>

              <div className="flex space-x-4">
                <button
                  onClick={() => setShowShareModal(true)}
                  className="flex-1 bg-purple-600 text-white font-semibold py-3 rounded-lg hover:bg-purple-700 transition"
                >
                  📤 Share Greeting
                </button>
                <button
                  onClick={() => setStep('templates')}
                  className="flex-1 border border-gray-300 text-gray-700 font-semibold py-3 rounded-lg hover:bg-gray-50 transition"
                >
                  Create Another
                </button>
              </div>
            </div>
          </div>
        )}
      </div>

      {/* Modals */}
      {showPremiumPopup && (
        <PremiumPopup
          onClose={() => setShowPremiumPopup(false)}
          onSubscribe={handleSubscribe}
        />
      )}

      {showShareModal && (
        <ShareModal
          onClose={() => setShowShareModal(false)}
          onShare={handleShare}
          shareLinks={{ directLink: createdGreeting?.personalizedImageUrl || '' }}
        />
      )}
    </div>
  );
};

export default TemplatesPage;
