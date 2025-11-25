import React from 'react';

/**
 * Reusable Table component
 * @param {array} columns - Array of column definitions: [{key: 'id', header: 'Header', render: (item) => <td>...</td>}]
 * @param {array} data - Array of data objects to display
 * @param {function} keyExtractor - Function to extract unique key from data item: (item) => item.id
 * @param {string} emptyMessage - Message to display when data is empty
 * @param {string} className - Additional CSS classes for the table wrapper
 */
function Table({ columns, data, keyExtractor, emptyMessage = 'No data available.', className = '' }) {
  if (!data || data.length === 0) {
    return (
      <div className={`px-6 py-8 text-center text-gray-500 ${className}`}>
        {emptyMessage}
      </div>
    );
  }

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-50">
          <tr>
            {columns.map((column) => (
              <th
                key={column.key}
                className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                {column.header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {data.map((item) => (
            <tr key={keyExtractor(item)} className="hover:bg-gray-50">
              {columns.map((column) => (
                <td key={column.key} className="px-6 py-4 whitespace-nowrap">
                  {column.render(item)}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Table;

