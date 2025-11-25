import React, { useState } from 'react';
import { USER_ROLES } from '../../utils/constants';
import { userApi } from '../../services/api';
import Notification from '../common/Notification';
import Select from '../common/Select';
import Input from '../common/Input';
import Button from '../common/Button';

function UserForm({ onCreateUser }) {
  const [newUserName, setNewUserName] = useState('');
  const [newUserRole, setNewUserRole] = useState(USER_ROLES.USER);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    // Validation
    if (!newUserName.trim()) {
      setError('User name is required');
      setLoading(false);
      return;
    }

    try {
      // Create new user via API
      const newUser = await userApi.create({
        name: newUserName.trim(),
        role: newUserRole,
      });

      onCreateUser(newUser);
      setSuccess(`User "${newUser.name}" created successfully!`);
      setNewUserName('');
      setNewUserRole(USER_ROLES.USER);
    } catch (err) {
      setError('Failed to create user: ' + (err.response?.data?.message || err.message || 'Unknown error'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white rounded-lg shadow-md p-6 mb-8">
      <h3 className="text-lg font-semibold text-gray-800 mb-4">Create New User</h3>
      <form onSubmit={handleSubmit}>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <Input
              id="user-name"
              label="User Name"
              type="text"
              value={newUserName}
              onChange={(e) => setNewUserName(e.target.value)}
              placeholder="Enter user name"
            />
          </div>
          <div>
            <Select
              id="user-role"
              label="Role"
              value={newUserRole}
              onChange={(e) => setNewUserRole(e.target.value)}
              options={[
                { value: USER_ROLES.ADMIN, label: 'ADMIN' },
                { value: USER_ROLES.USER, label: 'USER' },
              ]}
            />
          </div>
          <div className="flex items-end">
            <Button
              type="submit"
              variant="primary"
              disabled={loading}
              loading={loading}
              loadingText="Creating..."
              className="w-full"
            >
              Create User
            </Button>
          </div>
        </div>
      </form>
      <Notification type="error" message={error} onDismiss={() => setError('')} />
      <Notification type="success" message={success} onDismiss={() => setSuccess('')} />
    </div>
  );
}

export default UserForm;

