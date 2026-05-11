import React from 'react';

export const ShareModal = ({ onClose, onShare, shareLinks }) => {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg shadow-xl max-w-md w-full mx-4">
        {/* Header */}
        <div className="border-b border-gray-200 px-6 py-4">
          <h2 className="text-xl font-bold text-gray-900">Share Your Greeting</h2>
        </div>

        {/* Content */}
        <div className="px-6 py-6">
          <div className="space-y-3">
            <button
              onClick={() => onShare('whatsapp')}
              className="w-full flex items-center space-x-3 p-3 border border-gray-200 rounded-lg hover:bg-green-50 hover:border-green-300 transition"
            >
              <div className="w-10 h-10 bg-green-500 rounded-lg flex items-center justify-center text-white text-lg">
                💬
              </div>
              <div className="flex-1 text-left">
                <p className="font-semibold text-gray-900">WhatsApp</p>
                <p className="text-xs text-gray-500">Share to WhatsApp</p>
              </div>
            </button>

            <button
              onClick={() => onShare('email')}
              className="w-full flex items-center space-x-3 p-3 border border-gray-200 rounded-lg hover:bg-blue-50 hover:border-blue-300 transition"
            >
              <div className="w-10 h-10 bg-blue-500 rounded-lg flex items-center justify-center text-white text-lg">
                ✉️
              </div>
              <div className="flex-1 text-left">
                <p className="font-semibold text-gray-900">Email</p>
                <p className="text-xs text-gray-500">Share via Email</p>
              </div>
            </button>

            <button
              onClick={() => onShare('twitter')}
              className="w-full flex items-center space-x-3 p-3 border border-gray-200 rounded-lg hover:bg-blue-50 hover:border-blue-300 transition"
            >
              <div className="w-10 h-10 bg-blue-400 rounded-lg flex items-center justify-center text-white text-lg">
                𝕏
              </div>
              <div className="flex-1 text-left">
                <p className="font-semibold text-gray-900">Twitter</p>
                <p className="text-xs text-gray-500">Share to Twitter</p>
              </div>
            </button>

            <button
              onClick={() => onShare('instagram')}
              className="w-full flex items-center space-x-3 p-3 border border-gray-200 rounded-lg hover:bg-pink-50 hover:border-pink-300 transition"
            >
              <div className="w-10 h-10 bg-gradient-to-r from-purple-500 to-pink-500 rounded-lg flex items-center justify-center text-white text-lg">
                📷
              </div>
              <div className="flex-1 text-left">
                <p className="font-semibold text-gray-900">Instagram</p>
                <p className="text-xs text-gray-500">Share to Instagram</p>
              </div>
            </button>

            <div className="border border-dashed border-gray-300 rounded-lg p-4 mt-4">
              <p className="text-xs text-gray-500 mb-2">Direct Link</p>
              <div className="flex items-center space-x-2">
                <input
                  type="text"
                  value={shareLinks?.directLink || ''}
                  readOnly
                  className="flex-1 px-3 py-2 border border-gray-200 rounded text-sm text-gray-600"
                />
                <button
                  onClick={() => navigator.clipboard.writeText(shareLinks?.directLink)}
                  className="px-3 py-2 bg-gray-100 hover:bg-gray-200 rounded text-sm font-medium transition"
                >
                  Copy
                </button>
              </div>
            </div>
          </div>
        </div>

        {/* Footer */}
        <div className="border-t border-gray-200 px-6 py-4">
          <button
            onClick={onClose}
            className="w-full text-gray-700 font-semibold py-2 hover:bg-gray-50 rounded-lg transition"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default ShareModal;
