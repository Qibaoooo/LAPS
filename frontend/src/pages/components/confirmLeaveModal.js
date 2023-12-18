import React from "react";
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
    entitilementLeft,
    entitlementResult
}) {
    return (
        <>
            <Modal show={show}>
                <Modal.Header>
                    <Modal.Title>Confirm to update this leave application?</Modal.Title>
                </Modal.Header>
                <Table style={{ maxWidth: "80%" }}>
                    <tbody >
                        {Object.keys(leave).map((v) => {
                            return (
                                <tr>
                                    <td>{v.toUpperCase()}</td>
                                    <td>{leave[v]}</td>
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
                                    defaultValue={entitlementResult === 'true' ? '' : 'No Enough Leave Days!'}
                                />
                            </FloatingLabel>
                        </Stack>
                        <Stack style={{ textAlign: "end" }}>
                            <span>Action:</span>
                            <h5 style={{}}>{action}</h5>
                        </Stack>
                        <Stack style={{ textAlign: "end" }}>
                            <span>Left Entitlement:</span>
                            <h5 style={{}}>{entitilementLeft}</h5>
                        </Stack>
                    </Stack>
                    {showCommentAlert && (
                        <p className="text-warning">
                            Comment is compulsory for rejecting a leave application
                        </p>
                    )}
                </Modal.Body>
                <Modal.Footer>
                    {entitlementResult === 'true' && (
                        <Button variant="primary" onClick={handleUpdate}>
                            Update
                        </Button>)}
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default ConfirmLeaveModal;
