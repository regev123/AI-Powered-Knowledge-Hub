// Custom hook for document management
// This will be implemented when we connect to the backend

import { useState, useEffect } from 'react';

export function useDocuments() {
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // TODO: Implement document fetching logic

  return {
    documents,
    loading,
    error,
    setDocuments,
  };
}

