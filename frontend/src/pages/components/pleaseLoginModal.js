import React from 'react'
import { Modal, Button } from 'react-bootstrap';

function PleaseLoginModal({ show, handleGoToLogin }) {
    return (
      <>
        <Modal show={show}>
          <Modal.Body>Please login first.</Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={handleGoToLogin}>
              Go To Login Page
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    );
  }

export default PleaseLoginModal