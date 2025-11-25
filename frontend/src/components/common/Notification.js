import React, { useEffect } from 'react';

/**
 * Reusable Notification component for displaying success, error, and info messages
 * @param {string} type - 'success', 'error', or 'info'
 * @param {string} message - The message to display
 * @param {function} onDismiss - Callback function called when notification is dismissed
 * @param {number} duration - Auto-dismiss duration in milliseconds (default: 3000, 0 = no auto-dismiss)
 */
function Notification({ type = 'success', message, onDismiss, duration = 3000 }) {
  useEffect(() => {
    if (message && duration > 0) {
      const timer = setTimeout(() => {
        if (onDismiss) {
          onDismiss();
        }
      }, duration);

      return () => clearTimeout(timer);
    }
  }, [message, duration, onDismiss]);

  if (!message) {
    return null;
  }

  const isError = type === 'error';
  const isInfo = type === 'info';
  
  const bgColor = isError ? 'bg-red-50' : isInfo ? 'bg-blue-50' : 'bg-green-50';
  const borderColor = isError ? 'border-red-200' : isInfo ? 'border-blue-200' : 'border-green-200';
  const textColor = isError ? 'text-red-700' : isInfo ? 'text-blue-700' : 'text-green-700';

  return (
    <div className={`mt-4 p-3 ${bgColor} border ${borderColor} rounded-md ${textColor} text-sm`}>
      {message}
    </div>
  );
}

export default Notification;

