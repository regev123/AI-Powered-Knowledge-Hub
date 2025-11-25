import apiClient from './apiClient';

export const documentApi = {
  /**
   * Upload a document file
   * @param {File} file - The file to upload
   * @returns {Promise} Response with document data
   */
  upload: async (file) => {
    const formData = new FormData();
    formData.append('file', file);
    
    const response = await apiClient.post('/documents/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  },

  /**
   * Get all documents
   * @returns {Promise} List of documents
   */
  getAll: async () => {
    const response = await apiClient.get('/documents');
    return response.data;
  },

  /**
   * Delete a document by ID
   * @param {number} documentId - The ID of the document to delete
   * @returns {Promise} Response data
   */
  delete: async (documentId) => {
    const response = await apiClient.delete(`/documents/${documentId}`);
    return response.data;
  },

  /**
   * View a document file in the browser
   * @param {number} documentId - The ID of the document to view
   * @returns {Promise} Opens the file in a new tab
   */
  view: async (documentId) => {
    const baseURL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
    const url = `${baseURL}/documents/${documentId}/view`;
    // Open file in new tab for viewing
    window.open(url, '_blank');
  },
};

export const questionApi = {
  /**
   * Ask a question about a specific document
   * @param {number|string} documentId - The ID of the document
   * @param {string} question - The question to ask
   * @returns {Promise} Response with answer
   */
  ask: async (documentId, question) => {
    const response = await apiClient.post('/documents/ask', {
      documentId: Number(documentId),
      question: question
    });
    return response.data;
  },
};

export const userApi = {
  getAll: async () => {
    const response = await apiClient.get('/users');
    return response.data;
  },

  create: async (userData) => {
    const response = await apiClient.post('/users', userData);
    return response.data;
  },

  updateRole: async (id, role) => {
    const response = await apiClient.put('/users/role', { id, role });
    return response.data;
  },
};

