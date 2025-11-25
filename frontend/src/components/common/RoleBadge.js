import React from 'react';
import { USER_ROLES } from '../../utils/constants';

function RoleBadge({ role, showLabel = false }) {
  const getRoleBadgeColor = (role) => {
    return role === USER_ROLES.ADMIN 
      ? 'bg-purple-100 text-purple-800' 
      : 'bg-blue-100 text-blue-800';
  };

  return (
    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getRoleBadgeColor(role)}`}>
      {showLabel ? `Current Role: ${role}` : role}
    </span>
  );
}

export default RoleBadge;

