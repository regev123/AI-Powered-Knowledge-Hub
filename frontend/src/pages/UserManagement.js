import React, { useState } from 'react';
import UserForm from '../components/layout/UserForm';
import UserList from '../components/layout/UserList';
import Notification from '../components/common/Notification';
import { userApi } from '../services/api';
import { useUser } from '../context/UserContext';
import { USER_ROLES } from '../utils/constants';

function UserManagement() {
  const { users, addUser, updateUser, currentUser } = useUser();
  const [success, setSuccess] = useState('');
  const [error, setError] = useState('');

  const isAdmin = currentUser?.role === USER_ROLES.ADMIN;

  const handleCreateUser = (newUser) => {
    addUser(newUser);
  };

  const handleRoleChange = async (userId, newRole) => {
    try {
      setError('');
      const updatedUser = await userApi.updateRole(userId, newRole);
      updateUser(updatedUser);
      setSuccess('User role updated successfully!');
    } catch (err) {
      setError('Failed to update user role: ' + (err.response?.data?.message || err.message || 'Unknown error'));
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h2 className="text-2xl font-bold text-gray-800 mb-8">User Management</h2>

      <Notification type="error" message={error} onDismiss={() => setError('')} />
      <Notification type="success" message={success} onDismiss={() => setSuccess('')} />

      {/* Info message for non-admin users */}
      {!isAdmin && (
        <Notification
          type="info"
          message="Only administrators can access user management. Please contact an administrator for user management operations."
          duration={0}
        />
      )}

      {/* Create User Form - Only visible for ADMIN */}
      {isAdmin && (
        <>
          <UserForm onCreateUser={handleCreateUser} />
          <UserList users={users} onRoleChange={handleRoleChange} />
        </>
      )}
    </div>
  );
}

export default UserManagement;

