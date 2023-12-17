import React from "react";
import {
  Modal,
  Button,
  Table,
  Stack,
  Form,
  FloatingLabel,
} from "react-bootstrap";

function ConfirmClaimModal({
  show,
  showCommentAlert,
  onCommentInput,
  claim,
  action,
  handleUpdate,
  handleClose,
}) {
  return (
    <>
      <Modal show={show}>
        <Modal.Header>
          <Modal.Title>Confirm to update this claim?</Modal.Title>
        </Modal.Header>
        <Table style={{maxWidth:"80%"}}>
          <tbody >
            {Object.keys(claim).map((v, i) => {
              return (
                <tr>
                  <td>{v.toUpperCase()}</td>
                  <td>{claim[v]}</td>
                </tr>
              );
            })}
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
                />
              </FloatingLabel>
            </Stack>
            <Stack style={{ textAlign: "end" }}>
              <span>Action:</span>
              <h5 style={{}}>{action}</h5>
            </Stack>
          </Stack>
          {showCommentAlert && (
            <p className="text-warning">
              Comment is compulsory for rejecting a claim
            </p>
          )}
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
