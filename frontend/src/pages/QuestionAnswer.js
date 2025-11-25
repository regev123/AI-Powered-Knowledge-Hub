import React, { useState, useEffect } from 'react';
import Notification from '../components/common/Notification';
import QuestionForm from '../components/layout/QuestionForm';
import AnswerDisplay from '../components/layout/AnswerDisplay';
import { documentApi, questionApi } from '../services/api';

function QuestionAnswer() {
  const [selectedDocumentId, setSelectedDocumentId] = useState('');
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(false);
  const [documentsLoading, setDocumentsLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  // Fetch documents on component mount
  useEffect(() => {
    const fetchDocuments = async () => {
      try {
        setDocumentsLoading(true);
        setError('');
        const fetchedDocuments = await documentApi.getAll();
        setDocuments(fetchedDocuments || []);
        
        // Auto-select first document if available
        if (fetchedDocuments && fetchedDocuments.length > 0 && !selectedDocumentId) {
          setSelectedDocumentId(fetchedDocuments[0].id.toString());
        }
      } catch (err) {
        const errorMessage = err.response?.data?.message || err.message || 'Unknown error';
        setError(`Failed to load documents: ${errorMessage}`);
        console.error('Error fetching documents:', err);
      } finally {
        setDocumentsLoading(false);
      }
    };

    fetchDocuments();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleDocumentChange = (e) => {
    setSelectedDocumentId(e.target.value);
    // Clear answer when document changes
    setAnswer('');
  };

  const handleQuestionChange = (e) => {
    setQuestion(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setAnswer('');

    // Validation
    if (!selectedDocumentId) {
      setError('Please select a document');
      return;
    }

    if (!question.trim()) {
      setError('Please enter a question');
      return;
    }

    setLoading(true);

    try {
      const response = await questionApi.ask(selectedDocumentId, question.trim());
      setAnswer(response.answer || 'No answer received');
      setSuccess('Answer received successfully!');
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.message || 'Unknown error';
      setError(`Failed to get answer: ${errorMessage}`);
      console.error('Error asking question:', err);
    } finally {
      setLoading(false);
    }
  };

  // Prepare document options for Select component
  const documentOptions = documents.map(doc => ({
    value: doc.id.toString(),
    label: `${doc.name} (${doc.type})`
  }));

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h2 className="text-2xl font-bold text-gray-800 mb-8">Ask Questions About Documents</h2>

      <Notification type="error" message={error} onDismiss={() => setError('')} />
      <Notification type="success" message={success} onDismiss={() => setSuccess('')} />

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Left Column: Question Form */}
        <QuestionForm
          selectedDocumentId={selectedDocumentId}
          onDocumentChange={handleDocumentChange}
          question={question}
          onQuestionChange={handleQuestionChange}
          onSubmit={handleSubmit}
          loading={loading}
          documentOptions={documentOptions}
          documentsLoading={documentsLoading}
          documentsLength={documents.length}
        />

        {/* Right Column: Answer Display */}
        <AnswerDisplay
          loading={loading}
          answer={answer}
          selectedDocumentId={selectedDocumentId}
          documents={documents}
        />
      </div>
    </div>
  );
}

export default QuestionAnswer;
