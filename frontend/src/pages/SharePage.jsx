import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { greetingAPI } from '../services/api';
import { shareModalAPI } from '../services/api';
import Navbar from '../components/Navbar';
import ShareModal from '../components/ShareModal';
import { useAuthStore } from '../store/store';

export const SharePage = () => {
  const { greetingId } = useParams();
  const [greeting, setGreeting] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const { user, logout } = useAuthStore();

  useEffect(() => {
    const fetchGreeting = async () => {
      try {
        const response = await greetingAPI.getGreeting(greetingId);
        if (response.data.success) {
          setGreeting(response.data.data);
        }
      } catch (err) {
        setError('Failed to load greeting');
      } finally {
        setLoading(false);
      }
    };

    fetchGreeting();
  }, [greetingId]);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <div className="w-12 h-12 border-4 border-blue-200 border-t-blue-500 rounded-full animate-spin mx-auto mb-4"></div>
          <p className="text-gray-600">Loading greeting...</p>
        </div>
      </div>
    );
  }

  if (error || !greeting) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <h1 className="text-2xl font-bold text-gray-900 mb-4">Greeting Not Found</h1>
          <p className="text-gray-600 mb-6">{error}</p>
          <a href="/" className="text-blue-600 hover:text-blue-700 font-semibold">
            Go back to home
          </a>
        </div>
      </div>
    );
  }

  return (
    <div>
      <Navbar user={user} onLogout={logout} />

      <div className="min-h-screen bg-gray-50 py-12">
        <div className="max-w-3xl mx-auto px-4">
          <div className="bg-white rounded-lg shadow-lg overflow-hidden">
            {/* Greeting Image */}
            <div className="aspect-video bg-gray-200 flex items-center justify-center">
              <img
                src={greeting.personalizedImageUrl}
                alt="Greeting"
                className="w-full h-full object-cover"
              />
            </div>

            {/* Content */}
            <div className="p-8">
              <h1 className="text-3xl font-bold text-gray-900 mb-4">
                Check out this beautiful greeting!
              </h1>
              <p className="text-gray-600 mb-6">
                {greeting.viewCount} people have viewed this greeting
              </p>

              {/* Share Buttons */}
              <div className="space-y-3">
                <a
                  href={`https://wa.me/?text=${encodeURIComponent('Check out this beautiful greeting! ' + window.location.href)}`}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="block w-full bg-green-500 text-white font-semibold py-3 rounded-lg hover:bg-green-600 transition text-center"
                >
                  Share on WhatsApp
                </a>

                <a
                  href={`mailto:?subject=Check out my greeting&body=${encodeURIComponent(window.location.href)}`}
                  className="block w-full bg-blue-500 text-white font-semibold py-3 rounded-lg hover:bg-blue-600 transition text-center"
                >
                  Share via Email
                </a>

                <button
                  onClick={() => navigator.clipboard.writeText(window.location.href)}
                  className="w-full border border-gray-300 text-gray-700 font-semibold py-3 rounded-lg hover:bg-gray-50 transition"
                >
                  Copy Link
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SharePage;
