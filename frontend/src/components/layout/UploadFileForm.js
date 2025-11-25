import React from 'react';
import Input from '../common/Input';
import Button from '../common/Button';

/**
 * Upload File Form component
 * @param {File} selectedFile - The currently selected file
 * @param {function} onFileChange - Callback when file is selected
 * @param {function} onSubmit - Callback when form is submitted
 * @param {boolean} loading - Whether the form is in loading state
 */
function UploadFileForm({ selectedFile, onFileChange, onSubmit, loading }) {
  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(e);
  };

  return (
    <div className="bg-white rounded-lg shadow-md p-6 mb-8">
      <h3 className="text-lg font-semibold text-gray-800 mb-4">Upload New Document</h3>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <Input
            id="file-input"
            label="Select File"
            type="file"
            onChange={onFileChange}
            accept=".pdf,.txt,.doc,.docx"
            required
          />
          {selectedFile && (
            <p className="mt-1 text-xs text-gray-500">
              Selected: {selectedFile.name}
            </p>
          )}
        </div>
        <div className="flex justify-end">
          <Button
            type="submit"
            variant="primary"
            disabled={loading || !selectedFile}
            loading={loading}
            loadingText="Uploading..."
          >
            Upload Document
          </Button>
        </div>
        <p className="mt-2 text-xs text-gray-500">
          Document name and type (Policy, Report, or Manual) will be automatically identified by the system.
        </p>
      </form>
    </div>
  );
}

export default UploadFileForm;

