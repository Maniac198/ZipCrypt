// src/App.js
import React, { useState } from "react";
import FileUpload from "./components/FileUpload";
import ResultDisplay from "./components/ResultDisplay";
import axios from "axios";
import "./App.css";

function App() {
  const [responseData, setResponseData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleFileUpload = async (file) => {
    const formData = new FormData();
    formData.append("file", file);

    setLoading(true);
    setError(null);
    setResponseData(null);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/compress-encrypt",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      setResponseData(response.data);
    } catch (err) {
      setError("Error processing file. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="App">
      <div className="container">
        <div className="header">
          <h1>File Compression & Encryption</h1>
          <p>Upload a file to compress and encrypt using advanced algorithms.</p>
        </div>

        <div className="upload-section">
          <FileUpload onFileUpload={handleFileUpload} />
        </div>

        <div className="status-section">
          {loading && <p className="loading">Processing file, please wait...</p>}
          {error && <p className="error">{error}</p>}
          {responseData && <ResultDisplay response={responseData} />}
        </div>
      </div>
    </div>
  );
}

export default App;
