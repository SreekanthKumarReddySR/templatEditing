import React, { useRef } from 'react';
import html2canvas from 'html2canvas';

export const ImagePreview = ({ template, userName, userProfileImageUrl, customText, onShare }) => {
  const canvasRef = useRef(null);
  const [previewImage, setPreviewImage] = React.useState(null);

  React.useEffect(() => {
    // Draw overlay on canvas
    if (canvasRef.current && template) {
      const canvas = canvasRef.current;
      const ctx = canvas.getContext('2d');

      // Load template image
      const img = new Image();
      img.crossOrigin = 'anonymous';
      img.src = template.imageUrl;
      img.onload = () => {
        // Set canvas size
        canvas.width = img.width;
        canvas.height = img.height;

        // Draw template image
        ctx.drawImage(img, 0, 0);

        // Draw semi-transparent overlay
        ctx.fillStyle = 'rgba(0, 0, 0, 0.3)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        // Draw user profile picture (circular)
        if (userProfileImageUrl) {
          const profileImg = new Image();
          profileImg.crossOrigin = 'anonymous';
          profileImg.src = userProfileImageUrl;
          profileImg.onload = () => {
            const radius = 60;
            const x = canvas.width / 2;
            const y = canvas.height / 2 - 80;

            // Circle
            ctx.beginPath();
            ctx.arc(x, y, radius, 0, Math.PI * 2);
            ctx.fillStyle = 'white';
            ctx.fill();
            ctx.lineWidth = 3;
            ctx.strokeStyle = 'rgba(255, 255, 255, 0.8)';
            ctx.stroke();

            // Image
            ctx.save();
            ctx.beginPath();
            ctx.arc(x, y, radius - 3, 0, Math.PI * 2);
            ctx.clip();
            ctx.drawImage(profileImg, x - radius, y - radius, radius * 2, radius * 2);
            ctx.restore();

            drawText();
          };
        } else {
          drawText();
        }

        function drawText() {
          // Draw name
          ctx.font = 'bold 32px Arial, sans-serif';
          ctx.fillStyle = 'white';
          ctx.textAlign = 'center';
          ctx.shadowColor = 'rgba(0, 0, 0, 0.8)';
          ctx.shadowBlur = 10;
          ctx.shadowOffsetX = 2;
          ctx.shadowOffsetY = 2;

          const nameY = userProfileImageUrl ? canvas.height / 2 + 20 : canvas.height / 2 - 50;
          ctx.fillText(userName || 'Your Name', canvas.width / 2, nameY);

          // Draw custom text if provided
          if (customText) {
            ctx.font = '20px Arial, sans-serif';
            ctx.fillText(customText, canvas.width / 2, nameY + 50);
          }

          // Save preview
          setPreviewImage(canvas.toDataURL('image/png'));
        }
      };
    }
  }, [template, userName, userProfileImageUrl, customText]);

  const handleDownload = async () => {
    if (canvasRef.current) {
      const image = canvasRef.current.toDataURL('image/png');
      const link = document.createElement('a');
      link.href = image;
      link.download = `greeting-${Date.now()}.png`;
      link.click();
    }
  };

  return (
    <div className="space-y-4">
      <div className="border border-gray-200 rounded-lg overflow-hidden bg-gray-50">
        <canvas
          ref={canvasRef}
          className="w-full max-h-96"
          style={{ display: 'block', margin: '0 auto' }}
        />
      </div>

      <div className="flex space-x-3">
        <button
          onClick={handleDownload}
          className="flex-1 bg-blue-500 text-white font-semibold py-2 rounded-lg hover:bg-blue-600 transition flex items-center justify-center space-x-2"
        >
          <span>⬇️</span>
          <span>Download</span>
        </button>
        <button
          onClick={onShare}
          className="flex-1 bg-purple-600 text-white font-semibold py-2 rounded-lg hover:bg-purple-700 transition flex items-center justify-center space-x-2"
        >
          <span>📤</span>
          <span>Share</span>
        </button>
      </div>
    </div>
  );
};

export default ImagePreview;
