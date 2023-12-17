import React from "react";
import { Modal, Button, Table } from "react-bootstrap";

function ConfirmClaimModal({ show, claim, action, handleUpdate, handleClose }) {
  return (
    <>
      <Modal show={show}>
        <Modal.Header>
          <Modal.Title>Confirm to update this claim?</Modal.Title>
        </Modal.Header >
        <Table className="mx-3">
          {Object.keys(claim).map((v, i) => {
            return (
              <tr>
                <td>{v.toUpperCase()}</td>
                <td>{claim[v]}</td>
              </tr>
            );
          })}
        </Table>
        <Modal.Body style={{ textAlign: "end" }}>
          <span>Action:</span>
          <h5 style={{}}>{action}</h5>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleUpdate}>
            Update
          </Button>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default ConfirmClaimModal;
