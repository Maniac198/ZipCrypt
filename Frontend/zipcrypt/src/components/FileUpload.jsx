import React, { useState } from "react";
import axios from "axios";

const FileUpload = () => {
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");
  const [encryptedFileUrl, setEncryptedFileUrl] = useState(null);
  const [decryptedFileUrl, setDecryptedFileUrl] = useState(null); // New state for decrypted file URL
  const [decryptedText, setDecryptedText] = useState("");

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      setMessage("Please select a file.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post("http://localhost:8080/api/compress-encrypt", formData);
      setMessage(response.data[0]);
      setEncryptedFileUrl(response.data[1]);
      setDecryptedFileUrl(null); // Reset decrypted file URL
    } catch (error) {
      setMessage("File upload failed.");
    }
  };

  const handleDecryptionSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      setMessage("Please select an encrypted file to decrypt.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      // Make a request to decrypt the file
      const response = await axios.post("http://localhost:8080/api/decrypt", formData, {
        responseType: 'blob' // Specify response type to handle file download
      });

      // Create a URL for the decrypted file
      const downloadUrl = window.URL.createObjectURL(new Blob([response.data]));
      setDecryptedFileUrl(downloadUrl); // Set the decrypted file URL
      setDecryptedText(""); // Reset decrypted text
      setMessage("File decrypted successfully. You can download it now.");
    } catch (error) {
      setMessage("Decryption failed.");
    }
  };

  return (
    <div>
      <h2>File Upload</h2>
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={handleFileChange} />
        <button type="submit">Upload</button>
      </form>
      {message && <p>{message}</p>}
      {encryptedFileUrl && (
        <div>
          <p>Download the encrypted file:</p>
          <a href={`http://localhost:8080${encryptedFileUrl}`} download>
            Download Encrypted File
          </a>
        </div>
      )}

      <h2>Decrypt File</h2>
      <form onSubmit={handleDecryptionSubmit}>
        <input type="file" onChange={handleFileChange} />
        <button type="submit">Decrypt</button>
      </form>
      {decryptedFileUrl && (
        <div>
          <p>Download the decrypted file:</p>
          <a href={decryptedFileUrl} download="decrypted_output.txt">
            Download Decrypted File
          </a>
        </div>
      )}
      {decryptedText && (
        <div>
          <h3>Decrypted Original Text:</h3>
          <p>{decryptedText}</p>
        </div>
      )}
    </div>
  );
};

export default FileUpload;
