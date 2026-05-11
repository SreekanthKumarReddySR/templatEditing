import React from 'react';

export const TemplateCard = ({ template, onSelect, isPremium, isSelected }) => {
  return (
    <div
      onClick={onSelect}
      className={`group relative rounded-lg overflow-hidden shadow-md hover:shadow-xl transition cursor-pointer transform hover:scale-105 ${
        isSelected ? 'ring-2 ring-blue-500' : ''
      }`}
    >
      {/* Image */}
      <div className="relative h-48 bg-gray-200 overflow-hidden">
        <img
          src={template.imageThumbnailUrl || template.imageUrl}
          alt={template.title}
          className="w-full h-full object-cover group-hover:scale-110 transition duration-300"
        />

        {/* Premium Badge */}
        {template.isPremium && (
          <div className="absolute top-2 right-2 bg-yellow-400 text-yellow-900 px-3 py-1 rounded-full text-xs font-bold flex items-center space-x-1">
            <span>👑</span>
            <span>Premium</span>
          </div>
        )}

        {/* Overlay on Hover */}
        <div className="absolute inset-0 bg-black bg-opacity-0 group-hover:bg-opacity-30 transition"></div>
      </div>

      {/* Content */}
      <div className="bg-white p-4">
        <h3 className="font-semibold text-gray-900 text-sm mb-1 truncate">
          {template.title}
        </h3>
        <p className="text-xs text-gray-500 mb-3 capitalize">{template.category}</p>

        <div className="flex items-center justify-between text-xs text-gray-600">
          <div className="flex items-center space-x-3">
            <span className="flex items-center space-x-1">
              <span>❤️</span>
              <span>{template.likes}</span>
            </span>
            <span className="flex items-center space-x-1">
              <span>📤</span>
              <span>{template.shares}</span>
            </span>
          </div>
        </div>

        {isSelected && (
          <div className="mt-3 pt-3 border-t border-gray-200 text-center">
            <span className="text-xs font-semibold text-blue-600">✓ Selected</span>
          </div>
        )}
      </div>
    </div>
  );
};

export default TemplateCard;
