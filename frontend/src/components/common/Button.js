import React from 'react';

/**
 * Reusable Button component
 * @param {string} type - Button type (button, submit, reset) - default: 'button'
 * @param {string} variant - Button style variant (primary, secondary, danger) - default: 'primary'
 * @param {boolean} disabled - Whether the button is disabled
 * @param {boolean} loading - Whether the button is in loading state
 * @param {string} loadingText - Text to show when loading (default: 'Loading...')
 * @param {string} children - Button content/text
 * @param {string} className - Additional CSS classes
 * @param {function} onClick - Click handler function
 * @param {object} ...rest - Other standard button props
 */
function Button({
  type = 'button',
  variant = 'primary',
  disabled = false,
  loading = false,
  loadingText = 'Loading...',
  children,
  className = '',
  onClick,
  ...rest
}) {
  const baseButtonClasses = 'px-4 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 transition-colors duration-200 disabled:opacity-50 disabled:cursor-not-allowed';
  
  const variantClasses = {
    primary: 'bg-blue-600 text-white hover:bg-blue-700 focus:ring-blue-500',
    secondary: 'bg-gray-600 text-white hover:bg-gray-700 focus:ring-gray-500',
    danger: 'bg-red-600 text-white hover:bg-red-700 focus:ring-red-500',
  };

  const buttonClasses = `${baseButtonClasses} ${variantClasses[variant] || variantClasses.primary} ${className}`.trim();

  return (
    <button
      type={type}
      disabled={disabled || loading}
      onClick={onClick}
      className={buttonClasses}
      {...rest}
    >
      {loading ? loadingText : children}
    </button>
  );
}

export default Button;

