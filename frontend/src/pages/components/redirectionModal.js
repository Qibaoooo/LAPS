import React from 'react'
import { Modal, Button } from 'react-bootstrap';

function RedirectionModal({ show, handleButtonClick, headerMsg, buttonMsg }) {
    return (
      <>
        <Modal show={show}>
          <Modal.Body>{headerMsg}</Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={handleButtonClick}>
              {buttonMsg}
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    );
  }

export default RedirectionModal