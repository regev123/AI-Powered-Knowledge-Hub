import React from 'react';

/**
 * Reusable Textarea component
 * @param {string} id - Unique identifier for the textarea element
 * @param {string} label - Label text for the textarea
 * @param {string} value - Current textarea value
 * @param {function} onChange - Callback function when value changes
 * @param {string} placeholder - Placeholder text
 * @param {number} rows - Number of rows (default: 4)
 * @param {boolean} disabled - Whether the textarea is disabled
 * @param {boolean} required - Whether the textarea is required
 * @param {string} className - Additional CSS classes
 * @param {object} ...rest - Other standard textarea props
 */
function Textarea({
  id,
  label,
  value,
  onChange,
  placeholder,
  rows = 4,
  disabled = false,
  required = false,
  className = '',
  ...rest
}) {
  const baseTextareaClasses = 'w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 resize-y';
  const disabledClasses = disabled ? 'bg-gray-100 cursor-not-allowed opacity-60' : '';
  const textareaClasses = `${baseTextareaClasses} ${disabledClasses} ${className}`.trim();

  return (
    <div>
      {label && (
        <label htmlFor={id} className="block text-sm font-medium text-gray-700 mb-2">
          {label}
          {required && <span className="text-red-500 ml-1">*</span>}
        </label>
      )}
      <textarea
        id={id}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        rows={rows}
        disabled={disabled}
        required={required}
        className={textareaClasses}
        {...rest}
      />
    </div>
  );
}

export default Textarea;

