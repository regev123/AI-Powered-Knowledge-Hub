import React from 'react';

/**
 * Reusable Select component
 * @param {string} id - Unique identifier for the select element
 * @param {string} label - Label text for the select
 * @param {string} value - Current selected value
 * @param {function} onChange - Callback function when value changes
 * @param {array} options - Array of options: [{value: 'val', label: 'Label'}, ...]
 * @param {string} placeholder - Placeholder text (optional)
 * @param {boolean} disabled - Whether the select is disabled
 * @param {string} className - Additional CSS classes
 * @param {object} ...rest - Other standard select props
 */
function Select({
  id,
  label,
  value,
  onChange,
  options = [],
  placeholder,
  disabled = false,
  className = '',
  ...rest
}) {
  const baseSelectClasses = 'w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500';
  const disabledClasses = disabled ? 'bg-gray-100 cursor-not-allowed opacity-60' : '';
  const selectClasses = `${baseSelectClasses} ${disabledClasses} ${className}`.trim();

  return (
    <div>
      {label && (
        <label htmlFor={id} className="block text-sm font-medium text-gray-700 mb-2">
          {label}
        </label>
      )}
      <select
        id={id}
        value={value}
        onChange={onChange}
        disabled={disabled}
        className={selectClasses}
        {...rest}
      >
        {placeholder && (
          <option value="" disabled>
            {placeholder}
          </option>
        )}
        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    </div>
  );
}

export default Select;

