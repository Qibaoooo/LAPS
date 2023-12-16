import React from "react";
import { Alert, Container, Button } from "react-bootstrap";

function MyAlert({ variant, msg1, msg2, showAlert, handleCLose }) {
  return (
    <div>
      <Container className="col-md-4 mt-5">
        <Alert show={showAlert} variant={variant}>
          <p>{msg1}</p>
          <p>{msg2}</p>
          <div className="">
            <Button
              onClick={handleCLose}
              variant={`outline-${variant}`}
            >
              Close
            </Button>
          </div>
        </Alert>
      </Container>
    </div>
  );
}

export default MyAlert;
