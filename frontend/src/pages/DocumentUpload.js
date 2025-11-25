import React, { useState, useEffect } from 'react';
import { useUser } from '../context/UserContext';
import { USER_ROLES } from '../utils/constants';
import UploadFileForm from '../components/layout/UploadFileForm';
import DocumentList from '../components/layout/DocumentList';
import Notification from '../components/common/Notification';
import { documentApi } from '../services/api';

function DocumentUpload() {
  const { currentUser } = useUser();
  const [selectedFile, setSelectedFile] = useState(null);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [uploadLoading, setUploadLoading] = useState(false);
  const [deleteLoading, setDeleteLoading] = useState({});
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(true);

  const isAdmin = currentUser?.role === USER_ROLES.ADMIN;

  // Fetch documents on component mount
  useEffect(() => {
    if (isAdmin) {
      const fetchDocuments = async () => {
        try {
          setLoading(true);
          setError('');
          const fetchedDocuments = await documentApi.getAll();
          setDocuments(fetchedDocuments);
        } catch (err) {
          const errorMessage = err.response?.data?.message || err.message || 'Unknown error';
          setError(`Failed to load documents: ${errorMessage}`);
          console.error('Error fetching documents:', err);
        } finally {
          setLoading(false);
        }
      };

      fetchDocuments();
    } else {
      // If not admin, set loading to false immediately
      setLoading(false);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setSelectedFile(file);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setUploadLoading(true);

    // Validation
    if (!selectedFile) {
      setError('Please select a file to upload');
      setUploadLoading(false);
      return;
    }

    try {
      // Upload file to backend
      const uploadedDocument = await documentApi.upload(selectedFile);
      
      // Add the new document to the list
      setDocuments([...documents, uploadedDocument]);
      setSuccess(`Document "${uploadedDocument.name}" uploaded successfully!`);
      
      // Reset file selection and form
      setSelectedFile(null);
      e.target.reset();
    } catch (err) {
      setError('Failed to upload document: ' + (err.response?.data?.message || err.message || 'Unknown error'));
    } finally {
      setUploadLoading(false);
    }
  };

  const handleDelete = async (documentId) => {
    if (!window.confirm('Are you sure you want to delete this document?')) {
      return;
    }

    try {
      setDeleteLoading({ ...deleteLoading, [documentId]: true });
      setError('');
      await documentApi.delete(documentId);
      
      // Remove document from list
      setDocuments(documents.filter(doc => doc.id !== documentId));
      setSuccess('Document deleted successfully!');
    } catch (err) {
      setError('Failed to delete document: ' + (err.response?.data?.message || err.message || 'Unknown error'));
    } finally {
      setDeleteLoading({ ...deleteLoading, [documentId]: false });
    }
  };

  const handleView = async (documentId) => {
    try {
      setError('');
      await documentApi.view(documentId);
    } catch (err) {
      setError('Failed to view document: ' + (err.response?.data?.message || err.message || 'Unknown error'));
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h2 className="text-2xl font-bold text-gray-800 mb-8">Document Upload</h2>

      <Notification type="error" message={error} onDismiss={() => setError('')} />
      <Notification type="success" message={success} onDismiss={() => setSuccess('')} />

      {/* Info message for non-admin users */}
      {!isAdmin && (
        <Notification
          type="info"
          message="Only administrators can access document management. Please contact an administrator for document management operations."
          duration={0}
        />
      )}

      {/* Upload Form and Documents List - Only visible for ADMIN */}
      {isAdmin && (
        <>
          <UploadFileForm
            selectedFile={selectedFile}
            onFileChange={handleFileChange}
            onSubmit={handleSubmit}
            loading={uploadLoading}
          />
          {loading ? (
            <div className="bg-white rounded-lg shadow-md p-8 text-center">
              <p className="text-gray-500">Loading documents...</p>
            </div>
          ) : (
            <DocumentList
              documents={documents}
              isAdmin={isAdmin}
              onDelete={handleDelete}
              onView={handleView}
              deleteLoading={deleteLoading}
            />
          )}
        </>
      )}
    </div>
  );
}

export default DocumentUpload;
