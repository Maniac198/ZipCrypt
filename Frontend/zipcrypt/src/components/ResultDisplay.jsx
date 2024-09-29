// src/components/ResultDisplay.js
import React from "react";

function ResultDisplay({ response }) {
  return (
    <div className="result-display">
      <h2>Processed Result</h2>
      <div className="result-box">
        <pre>{JSON.stringify(response, null, 2)}</pre>
      </div>
    </div>
  );
}

export default ResultDisplay;
