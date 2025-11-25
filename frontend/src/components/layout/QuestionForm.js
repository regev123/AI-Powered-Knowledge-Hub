import React from 'react';
import Select from '../common/Select';
import Textarea from '../common/Textarea';
import Button from '../common/Button';

/**
 * QuestionForm component for asking questions about documents
 * @param {string} selectedDocumentId - The ID of the selected document
 * @param {function} onDocumentChange - Callback when document selection changes
 * @param {string} question - Current question text
 * @param {function} onQuestionChange - Callback when question text changes
 * @param {function} onSubmit - Callback when form is submitted
 * @param {boolean} loading - Whether the form is in loading state
 * @param {Array} documentOptions - Array of document options for the select dropdown
 * @param {boolean} documentsLoading - Whether documents are being loaded
 * @param {number} documentsLength - Number of available documents
 */
function QuestionForm({
  selectedDocumentId,
  onDocumentChange,
  question,
  onQuestionChange,
  onSubmit,
  loading,
  documentOptions,
  documentsLoading,
  documentsLength
}) {
  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <h3 className="text-lg font-semibold text-gray-800 mb-4">Ask a Question</h3>
      
      <form onSubmit={onSubmit} className="space-y-4">
        <Select
          id="document-select"
          label="Select Document"
          value={selectedDocumentId}
          onChange={onDocumentChange}
          options={documentOptions}
          placeholder={documentsLoading ? "Loading documents..." : "Choose a document to ask about"}
          disabled={documentsLoading || documentsLength === 0}
          required
        />

        {documentsLength === 0 && !documentsLoading && (
          <p className="text-sm text-gray-500 italic">
            No documents available. Please upload documents first.
          </p>
        )}

        <div>
          <Textarea
            id="question-input"
            label="Your Question"
            value={question}
            onChange={onQuestionChange}
            placeholder="Enter your question about the selected document..."
            rows={6}
            required
            disabled={loading || !selectedDocumentId}
          />
          <p className="mt-1 text-xs text-gray-500">
            Ask specific questions about the document content, and AI will provide answers based on the document.
          </p>
        </div>

        <div className="flex justify-end">
          <Button
            type="submit"
            variant="primary"
            disabled={loading || !selectedDocumentId || !question.trim() || documentsLength === 0}
            loading={loading}
            loadingText="Getting answer..."
          >
            Ask Question
          </Button>
        </div>
      </form>
    </div>
  );
}

export default QuestionForm;

