import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { UserProvider, useUser } from './context/UserContext';
import Navbar from './components/layout/Navbar';
import DocumentUpload from './pages/DocumentUpload';
import QuestionAnswer from './pages/QuestionAnswer';
import UserManagement from './pages/UserManagement';

function AppContent() {
  const { users, currentUser, loading, error, handleUserChange } = useUser();

  if (loading) {
    return (
      <div className="min-h-screen bg-white flex items-center justify-center">
        <div className="text-gray-600">Loading users...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-white flex items-center justify-center">
        <div className="text-red-600">{error}</div>
      </div>
    );
  }

  return (
    <Router>
      <div className="min-h-screen bg-white">
        <Navbar 
          users={users}
          currentUser={currentUser}
          onUserChange={handleUserChange}
        />
        <Routes>
          <Route path="/" element={<Navigate to="/upload" replace />} />
          <Route path="/upload" element={<DocumentUpload />} />
          <Route path="/questions" element={<QuestionAnswer />} />
          <Route path="/users" element={<UserManagement />} />
        </Routes>
      </div>
    </Router>
  );
}

function App() {
  return (
    <UserProvider>
      <AppContent />
    </UserProvider>
  );
}

export default App;

