import React from 'react';

/**
 * Reusable Input component
 * @param {string} id - Unique identifier for the input element
 * @param {string} label - Label text for the input
 * @param {string} type - Input type (text, email, password, file, etc.) - default: 'text'
 * @param {string} value - Current input value (not used for file type)
 * @param {function} onChange - Callback function when value changes
 * @param {string} placeholder - Placeholder text (not used for file type)
 * @param {boolean} disabled - Whether the input is disabled
 * @param {boolean} required - Whether the input is required
 * @param {string} className - Additional CSS classes
 * @param {object} ...rest - Other standard input props
 */
function Input({
  id,
  label,
  type = 'text',
  value,
  onChange,
  placeholder,
  disabled = false,
  required = false,
  className = '',
  ...rest
}) {
  const baseInputClasses = 'w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500';
  const disabledClasses = disabled ? 'bg-gray-100 cursor-not-allowed opacity-60' : '';
  const inputClasses = `${baseInputClasses} ${disabledClasses} ${className}`.trim();
  const isFileInput = type === 'file';

  return (
    <div>
      {label && (
        <label htmlFor={id} className="block text-sm font-medium text-gray-700 mb-2">
          {label}
          {required && <span className="text-red-500 ml-1">*</span>}
        </label>
      )}
      <input
        id={id}
        type={type}
        value={isFileInput ? undefined : value}
        onChange={onChange}
        placeholder={isFileInput ? undefined : placeholder}
        disabled={disabled}
        required={required}
        className={inputClasses}
        {...rest}
      />
    </div>
  );
}

export default Input;

