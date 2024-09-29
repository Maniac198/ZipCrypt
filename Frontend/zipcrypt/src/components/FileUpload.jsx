// src/components/FileUpload.js
import React, { useState } from "react";

function FileUpload({ onFileUpload }) {
  const [file, setFile] = useState(null);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (file) {
      onFileUpload(file);
    } else {
      alert("Please select a file first.");
    }
  };

  return (
    <form className="file-upload-form" onSubmit={handleSubmit}>
      <input
        type="file"
        accept=".txt"
        onChange={handleFileChange}
        className="file-input"
      />
      <button type="submit" className="submit-btn">
        Upload & Process
      </button>
    </form>
  );
}

export default FileUpload;
