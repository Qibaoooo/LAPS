import React, { useEffect } from "react";
import {
  Modal,
  Button,
  Table,
  Stack,
  Form,
  FloatingLabel,
} from "react-bootstrap";

function ConfirmLeaveModal({
  show,
  showCommentAlert,
  onCommentInput,
  leave,
  action,
  handleUpdate,
  handleClose,
  entitlementList,
  setComment,
  comment,
}) {

  return (
    <>
      <Modal show={show}>
        <Modal.Header>
          <Modal.Title>Confirm to update this leave application?</Modal.Title>
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
              <td>STATUS</td>
              <td>{leave.status}</td>
            </tr>
          </tbody>
        </Table>
        <Modal.Body>
          <Stack direction="horizontal">
            <Stack>
              <FloatingLabel controlId="floatingTextarea2" label="Comments">
                <Form.Control
                  as="textarea"
                  placeholder="Leave a comment here"
                  style={{ height: "100px" }}
                  onInput={onCommentInput}
                  defaultValue={comment}
                />
              </FloatingLabel>
            </Stack>
            <Stack style={{ textAlign: "end" }}>
              <span style={{ textAlign: "end" }}>Action:</span>
              <h5 style={{}}>{action}</h5>
              {/**
               * Hide this first becoz bug.
               */}
              {/* <span style={{ textAlign: "left" }}>Entitlement Left:</span>
              <h5 style={{}}>{entitlementList.left}</h5> */}
            </Stack>
          </Stack>
          {showCommentAlert && (
            <p className="text-warning">
              Comment is compulsory for rejecting a leave application
            </p>
          )}
        </Modal.Body>
        <Modal.Footer>
          {(action === "REJECT" || entitlementList.result === "true") && (
            <Button variant="primary" onClick={handleUpdate}>
              Update
            </Button>
          )}
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default ConfirmLeaveModal;
