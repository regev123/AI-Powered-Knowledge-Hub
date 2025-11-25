import React, { createContext, useContext, useState, useEffect } from 'react';
import { userApi } from '../services/api';
import { setUserProvider } from '../services/apiClient';

const UserContext = createContext();

export function UserProvider({ children }) {
  const [users, setUsers] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch users from backend API
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        setLoading(true);
        setError(null);
        const fetchedUsers = await userApi.getAll();
        setUsers(fetchedUsers);
        
        // Set first user as current user if available and no current user is set
        if (fetchedUsers.length > 0) {
          setCurrentUser(prev => prev || fetchedUsers[0]);
        }
      } catch (err) {
        setError('Failed to load users: ' + (err.message || 'Unknown error'));
        console.error('Error fetching users:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  // Update currentUser when users list changes (e.g., role change)
  useEffect(() => {
    if (currentUser) {
      const updatedUser = users.find(user => user.id === currentUser.id);
      if (updatedUser && updatedUser.role !== currentUser.role) {
        setCurrentUser(updatedUser);
      }
    }
  }, [users, currentUser]);

  const handleUserChange = (userId) => {
    // Convert to number if it's a string (HTML select values are always strings)
    const id = typeof userId === 'string' ? Number(userId) : userId;
    const selectedUser = users.find(user => user.id === id);
    if (selectedUser) {
      setCurrentUser(selectedUser);
    }
  };

  const addUser = (newUser) => {
    setUsers([...users, newUser]);
  };

  const updateUser = (updatedUser) => {
    setUsers(users.map(user => 
      user.id === updatedUser.id ? updatedUser : user
    ));
  };

  // Set up API client to use current user for headers
  // Update whenever currentUser changes
  useEffect(() => {
    setUserProvider(currentUser);
  }, [currentUser]);

  const value = {
    users,
    setUsers,
    currentUser,
    setCurrentUser,
    loading,
    error,
    handleUserChange,
    addUser,
    updateUser,
  };

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
}

export function useUser() {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error('useUser must be used within a UserProvider');
  }
  return context;
}

