import React from 'react';

/**
 * AnswerDisplay component for displaying AI-generated answers
 * @param {boolean} loading - Whether the answer is being loaded
 * @param {string} answer - The answer text to display
 * @param {string} selectedDocumentId - The ID of the selected document
 * @param {Array} documents - Array of document objects with id and name properties
 */
function AnswerDisplay({ loading, answer, selectedDocumentId, documents }) {
  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <h3 className="text-lg font-semibold text-gray-800 mb-4">Answer</h3>
      
      {loading ? (
        <div className="flex items-center justify-center py-12">
          <div className="text-center">
            <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600 mb-4"></div>
            <p className="text-gray-600">Analyzing document and generating answer...</p>
          </div>
        </div>
      ) : answer ? (
        <div className="space-y-4">
          <div className="bg-blue-50 border-l-4 border-blue-500 p-4 rounded">
            <div className="flex items-start">
              <div className="flex-shrink-0">
                <svg className="h-5 w-5 text-blue-500" fill="currentColor" viewBox="0 0 20 20">
                  <path fillRule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clipRule="evenodd" />
                </svg>
              </div>
              <div className="ml-3 flex-1">
                <p className="text-sm text-blue-700 whitespace-pre-wrap">{answer}</p>
              </div>
            </div>
          </div>
          
          {selectedDocumentId && (
            <div className="text-xs text-gray-500 mt-4">
              <p>Answer based on: <span className="font-medium">
                {documents.find(doc => doc.id.toString() === selectedDocumentId)?.name || 'Selected document'}
              </span></p>
            </div>
          )}
        </div>
      ) : (
        <div className="text-center py-12 text-gray-500">
          <svg className="mx-auto h-12 w-12 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
          </svg>
          <p>Your answer will appear here after you ask a question.</p>
        </div>
      )}
    </div>
  );
}

export default AnswerDisplay;

