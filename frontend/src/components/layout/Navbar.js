import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import Select from '../common/Select';

function Navbar({ users, currentUser, onUserChange }) {
  const location = useLocation();

  const isActive = (path) => location.pathname === path;

  return (
    <nav className="bg-white shadow-md border-b border-gray-200">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo/Title */}
          <div className="flex items-center">
            <Link to="/upload" className="text-2xl font-bold text-gray-800 hover:text-blue-600 transition-colors">
              AI-Powered Knowledge Hub
            </Link>
          </div>

          {/* Navigation Links and User Selector */}
          <div className="flex items-center space-x-4">
            {/* Navigation Links */}
            <div className="flex space-x-1">
              <Link
                to="/"
                className={`px-4 py-2 text-sm font-medium rounded-md transition-colors duration-200 ${
                  isActive('/') || isActive('/upload')
                    ? 'text-blue-600 bg-blue-50'
                    : 'text-gray-700 hover:text-blue-600 hover:bg-gray-50'
                }`}
              >
                Upload Document
              </Link>
              <Link
                to="/questions"
                className={`px-4 py-2 text-sm font-medium rounded-md transition-colors duration-200 ${
                  isActive('/questions')
                    ? 'text-blue-600 bg-blue-50'
                    : 'text-gray-700 hover:text-blue-600 hover:bg-gray-50'
                }`}
              >
                Ask Questions
              </Link>
              <Link
                to="/users"
                className={`px-4 py-2 text-sm font-medium rounded-md transition-colors duration-200 ${
                  isActive('/users')
                    ? 'text-blue-600 bg-blue-50'
                    : 'text-gray-700 hover:text-blue-600 hover:bg-gray-50'
                }`}
              >
                User Management
              </Link>
            </div>

            {/* User Selector */}
            <div className="flex items-center space-x-2 border-l border-gray-200 pl-4">
              <label htmlFor="user-select" className="text-sm font-medium text-gray-700">
                Current User:
              </label>
              <div className="min-w-[180px]">
                <Select
                  id="user-select"
                  value={currentUser?.id || ''}
                  onChange={(e) => onUserChange(e.target.value)}
                  options={users.map((user) => ({
                    value: user.id,
                    label: `${user.name} (${user.role})`
                  }))}
                  className="px-3 py-1.5 text-sm w-auto"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;

