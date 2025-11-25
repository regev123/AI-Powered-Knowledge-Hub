import React from 'react';
import Table from '../common/Table';
import Button from '../common/Button';

/**
 * Document List component
 * @param {array} documents - Array of document objects
 * @param {boolean} isAdmin - Whether the current user is an admin
 * @param {function} onDelete - Callback function when a document is deleted
 * @param {function} onView - Callback function when a document is viewed
 * @param {object} deleteLoading - Object with document IDs as keys and loading state as values
 */
function DocumentList({ documents, isAdmin, onDelete, onView, deleteLoading = {} }) {
  const columns = [
    {
      key: 'name',
      header: 'Document Name',
      render: (document) => (
        <div className="text-sm font-medium text-gray-900">{document.name}</div>
      ),
    },
    {
      key: 'type',
      header: 'Type',
      render: (document) => (
        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
          {document.type}
        </span>
      ),
    },
    {
      key: 'fileName',
      header: 'File Name',
      render: (document) => (
        <div className="text-sm text-gray-500">{document.fileName}</div>
      ),
    },
    {
      key: 'uploadedBy',
      header: 'Uploaded By',
      render: (document) => (
        <div className="text-sm text-gray-500">{document.uploadedBy}</div>
      ),
    },
    {
      key: 'uploadDate',
      header: 'Upload Date',
      render: (document) => (
        <div className="text-sm text-gray-500">
          {new Date(document.uploadDate).toLocaleDateString()}
        </div>
      ),
    },
  ];

  // Add Actions column if user is admin
  if (isAdmin) {
    columns.push({
      key: 'actions',
      header: 'Actions',
      render: (document) => (
        <div className="flex space-x-2">
          <Button
            variant="primary"
            onClick={() => onView(document.id)}
            className="px-3 py-1"
          >
            View
          </Button>
          <Button
            variant="danger"
            onClick={() => onDelete(document.id)}
            className="px-3 py-1"
            disabled={deleteLoading[document.id]}
            loading={deleteLoading[document.id]}
            loadingText="Deleting..."
          >
            Delete
          </Button>
        </div>
      ),
    });
  }

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <div className="px-6 py-4 border-b border-gray-200">
        <h3 className="text-lg font-semibold text-gray-800">Uploaded Documents</h3>
      </div>
      <Table
        columns={columns}
        data={documents}
        keyExtractor={(document) => document.id}
        emptyMessage={`No documents uploaded yet. ${isAdmin ? 'Upload your first document above.' : ''}`}
      />
    </div>
  );
}

export default DocumentList;

