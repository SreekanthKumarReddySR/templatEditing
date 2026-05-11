import React, { useState } from 'react';
import { useUIStore } from '../store/store';

export const PremiumPopup = ({ onClose, onSubscribe }) => {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg shadow-xl max-w-md w-full mx-4 overflow-hidden">
        {/* Header */}
        <div className="bg-gradient-to-r from-blue-500 to-purple-600 px-6 py-8">
          <h2 className="text-2xl font-bold text-white mb-2">Unlock Premium</h2>
          <p className="text-blue-100">Get access to all exclusive templates</p>
        </div>

        {/* Content */}
        <div className="px-6 py-6">
          <div className="space-y-4 mb-6">
            <div className="flex items-center space-x-3">
              <div className="w-5 h-5 bg-green-500 rounded-full flex items-center justify-center">
                <span className="text-white text-xs">✓</span>
              </div>
              <span className="text-gray-700">Access to 100+ premium templates</span>
            </div>
            <div className="flex items-center space-x-3">
              <div className="w-5 h-5 bg-green-500 rounded-full flex items-center justify-center">
                <span className="text-white text-xs">✓</span>
              </div>
              <span className="text-gray-700">Unlimited greeting creation</span>
            </div>
            <div className="flex items-center space-x-3">
              <div className="w-5 h-5 bg-green-500 rounded-full flex items-center justify-center">
                <span className="text-white text-xs">✓</span>
              </div>
              <span className="text-gray-700">Priority support</span>
            </div>
          </div>

          {/* Pricing */}
          <div className="space-y-3 mb-6">
            <div className="border border-gray-200 rounded-lg p-4 hover:border-blue-500 cursor-pointer transition">
              <div className="flex justify-between items-center mb-2">
                <span className="font-semibold text-gray-900">Monthly</span>
                <span className="text-2xl font-bold text-blue-600">₹299</span>
              </div>
              <p className="text-sm text-gray-500">Renews monthly</p>
            </div>
            
            <div className="border border-gray-200 rounded-lg p-4 hover:border-purple-500 cursor-pointer transition ring-2 ring-yellow-300 ring-opacity-50">
              <div className="flex justify-between items-center mb-2">
                <span className="font-semibold text-gray-900">Annual</span>
                <span className="text-2xl font-bold text-purple-600">₹2,499</span>
              </div>
              <p className="text-sm text-gray-500">Save ₹3,081 annually</p>
            </div>
          </div>

          {/* Buttons */}
          <div className="space-y-3">
            <button
              onClick={() => onSubscribe('ANNUAL')}
              className="w-full bg-gradient-to-r from-blue-500 to-purple-600 text-white font-semibold py-3 rounded-lg hover:shadow-lg transition transform hover:scale-105"
            >
              Subscribe Now
            </button>
            <button
              onClick={onClose}
              className="w-full border border-gray-300 text-gray-700 font-semibold py-3 rounded-lg hover:bg-gray-50 transition"
            >
              Maybe Later
            </button>
          </div>
        </div>

        {/* Close Button */}
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-gray-400 hover:text-gray-600"
        >
          ✕
        </button>
      </div>
    </div>
  );
};

export default PremiumPopup;
