import React from "react";
import {
  Modal,
  Button,
  Table,
} from "react-bootstrap";

function LeaveDetailsModal({ show, leave, handleClose }) {
  return (
    <>
      <Modal show={show}>
        <Modal.Header>
          <Modal.Title>Leave Application Details</Modal.Title>
        </Modal.Header>
        <Table style={{ maxWidth: "80%" }}>
          <tbody>
            <tr>
              <td>ID</td>
              <td>{leave.id}</td>
            </tr>
            <tr>
              <td>NAME</td>
              <td>
                {leave &&
                  leave.username &&
                  leave.username.charAt(0).toUpperCase() +
                    leave.username.slice(1)}
              </td>
            </tr>
            <tr>
              <td>TYPE</td>
              <td>{leave.type}</td>
            </tr>
            <tr>
              <td>FROM</td>
              <td>{leave.fromDate}</td>
            </tr>
            <tr>
              <td>TO</td>
              <td>{leave.toDate}</td>
            </tr>
            <tr>
              <td>DESCRIPTION</td>
              <td>{leave.description}</td>
            </tr>
            <tr>
              <td>COMMENT</td>
              <td>{leave.comment}</td>
            </tr>
            <tr>
              <td>STATUS</td>
              <td>{leave.status}</td>
            </tr>
          </tbody>
        </Table>
        <Modal.Body>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default LeaveDetailsModal;
