# File Processor Project

## Overview
The **File Processor Project** is a web application that accepts a text file, compresses its contents using the **Huffman coding algorithm**, and encrypts the compressed data using **AES encryption**. This project demonstrates the integration of a **Spring Boot** backend with a **React** frontend to handle file uploads and processing.

## Technologies Used
- **Spring Boot**: For creating the backend REST API.
- **React**: For building the user interface.
- **Huffman Coding**: For compressing the text data.
- **AES Encryption**: For encrypting the compressed data.

## Project Structure
```
/src
  /main
    /java
      /com/example/fileprocessor
        FileProcessorApplication.java
        /controller
          FileController.java
        /service
          FileService.java
          CompressionService.java
          EncryptionService.java
        /util
          HuffmanCoding.java
          AESUtil.java
    /resources
      application.properties
```

## Setup Instructions

### Prerequisites
1. **Java 17** or above
2. **Maven** (for building the Spring Boot application)
3. **Node.js** and **npm** (for the React frontend)

### Backend Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Maniac198/ZipCrypt.git
   cd https://github.com/Maniac198/ZipCrypt.git
   ```

2. **Navigate to the Backend Directory**:
   ```bash
   cd backend
   ```

3. **Build and Run the Spring Boot Application**:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

### Frontend Setup

1. **Navigate to the Frontend Directory**:
   ```bash
   cd frontend
   ```

2. **Install Dependencies**:
   ```bash
   npm install
   ```

3. **Run the React Application**:
   ```bash
   npm start
   ```
   The application will be available at `http://localhost:5173`.

## API Endpoints

### Compress and Encrypt File
- **URL**: `http://localhost:8080/api/compress-encrypt`
- **Method**: `POST`
- **Request Type**: `multipart/form-data`
- **Form Data**:
  - `file`: The text file to be processed.

### Example Response
```json
{
    "original": "Hello, this is a test file!",
    "compressed": "110101010...",
    "encrypted": "U2FsdGVkX19KSmFsaM..."
}
```

## Testing with Postman

1. Open Postman and create a new `POST` request.
2. Use the URL: `http://localhost:8080/api/compress-encrypt`.
3. In the Body tab, select `form-data`.
4. Set the key to `file` and choose your text file.
5. Click **Send** and check the response.

## Conclusion
This project demonstrates the use of modern web technologies to perform file processing tasks. The integration of Spring Boot and React provides a solid foundation for building web applications.

## License
This project is licensed under the MIT License.
