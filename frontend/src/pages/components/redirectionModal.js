import React from "react";
import { Modal, Button } from "react-bootstrap";

function RedirectionModal({
  show,
  handleButtonClick,
  headerMsg,
  buttonMsg,
  enableCloseButton = false,
  handleClose = ()=>{}
}) {
  return (
    <>
      <Modal show={show}>
        <Modal.Body>{headerMsg}</Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleButtonClick}>
            {buttonMsg}
          </Button>
          {enableCloseButton && <Button variant="secondary" onClick={handleClose}>Close</Button>}
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default RedirectionModal;
