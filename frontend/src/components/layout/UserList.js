import React from 'react';
import { USER_ROLES } from '../../utils/constants';
import Table from '../common/Table';
import RoleBadge from '../common/RoleBadge';
import Select from '../common/Select';

function UserList({ users, onRoleChange }) {
  const columns = [
    {
      key: 'name',
      header: 'Name',
      render: (user) => (
        <div className="text-sm font-medium text-gray-900">{user.name}</div>
      ),
    },
    {
      key: 'role',
      header: 'Role',
      render: (user) => <RoleBadge role={user.role} />,
    },
    {
      key: 'changeRole',
      header: 'Change Role',
      render: (user) => (
        <Select
          id={`role-select-${user.id}`}
          value={user.role}
          onChange={(e) => onRoleChange(user.id, e.target.value)}
          options={[
            { value: USER_ROLES.ADMIN, label: 'ADMIN' },
            { value: USER_ROLES.USER, label: 'USER' },
          ]}
          className="px-3 py-1.5 text-sm w-auto"
        />
      ),
    },
  ];

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <div className="px-6 py-4 border-b border-gray-200">
        <h3 className="text-lg font-semibold text-gray-800">All Users</h3>
      </div>
      <Table
        columns={columns}
        data={users}
        keyExtractor={(user) => user.id}
        emptyMessage="No users found. Create your first user above."
      />
    </div>
  );
}

export default UserList;

