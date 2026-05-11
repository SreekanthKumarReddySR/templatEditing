import React from 'react';

export const Navbar = ({ user, onLogout }) => {
  return (
    <nav className="border-b border-gray-200 bg-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            <a href="/" className="flex items-center space-x-2">
              <div className="w-8 h-8 bg-gradient-to-r from-blue-500 to-purple-600 rounded-lg"></div>
              <span className="font-bold text-xl text-gray-900">ClassPlus</span>
            </a>
          </div>
          
          <div className="flex items-center space-x-6">
            <a href="/templates" className="text-gray-700 hover:text-gray-900 text-sm font-medium">
              Templates
            </a>
            <a href="/my-greetings" className="text-gray-700 hover:text-gray-900 text-sm font-medium">
              My Greetings
            </a>
            
            {user && (
              <>
                <div className="flex items-center space-x-3 border-l border-gray-200 pl-6">
                  {user.profilePictureUrl && (
                    <img
                      src={user.profilePictureUrl}
                      alt={user.name}
                      className="w-8 h-8 rounded-full"
                    />
                  )}
                  <span className="text-sm text-gray-700">{user.name}</span>
                  <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                    user.subscriptionTier === 'PREMIUM'
                      ? 'bg-yellow-100 text-yellow-800'
                      : 'bg-blue-100 text-blue-800'
                  }`}>
                    {user.subscriptionTier}
                  </span>
                </div>
                <button
                  onClick={onLogout}
                  className="text-gray-700 hover:text-gray-900 text-sm font-medium"
                >
                  Logout
                </button>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
