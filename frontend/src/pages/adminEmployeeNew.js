import React, { useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { Form, Row, Col, Button } from "react-bootstrap";
import { createNewEmployee } from "./utils/api/apiAdmin";
import MyAlert from "./components/myAlert";

function AdminEmployeeNew() {

    const [userName, setUserName] = useState();
    const [password, setPassword] = useState();
    const [managerName, setManagerName] = useState();
    const [roleName, setRoleName] = useState();
    const [annualLeaveEntitlement, setAnnualLeaveEntitlement] = useState();
    const [medicalLeaveEntitlement, setMedicalLeaveEntitlement] = useState();
    const [compensationLeaveEntitlement, setCompensationLeaveEntitlement] = useState();

    const [showAlert, setShowAlert] = useState(false);
    const [alertMsg, setAlertMsg] = useState();

    const onInputUN = ({ target: { value } }) => setUserName(value);
    const onInputPW = ({ target: { value } }) => setPassword(value);
    const onInputMN = ({ target: { value } }) => setManagerName(value);
    const onInputRN = ({ target: { value } }) => setRoleName(value);
    const onInputALE = ({ target: { value } }) => setAnnualLeaveEntitlement(value);
    const onInputMLE = ({ target: { value } }) => setMedicalLeaveEntitlement(value);
    const onInputCLE = ({ target: { value } }) => setCompensationLeaveEntitlement(value);

    const [validated, setValidated] = useState(false);


    const onFormSubmit = (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.stopPropagation();
            setValidated(true);
        } else {
            createNewEmployee({
                username: userName,
                password: password,
                managerName: managerName,
                roleName: roleName,
                annualLeaveEntitlement: annualLeaveEntitlement,
                medicalLeaveEntitlement: medicalLeaveEntitlement,
                compensationLeaveEntitlement: compensationLeaveEntitlement,

            }).then((response) => {
                if (response.status == 200) {
                    console.log(JSON.stringify(response.data));
                    setAlertMsg(JSON.stringify(response.data));
                    setShowAlert(true);
                }
            }).catch((error) => {
                setAlertMsg("error, please try again.");
                setShowAlert(true);
            });
        }
    };

    return (
        <LoginCheckWrapper>
            <MyNavBar></MyNavBar>
            <PageTitle title="Create New Employee"></PageTitle>

            <Form noValidate validated={validated} onSubmit={onFormSubmit}>
                <Col md="6" className="mx-auto">
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formUserName">
                            <Form.Label>UserName</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="user name"
                                onChange={onInputUN}
                            />{" "}
                        </Form.Group>
                        <Form.Group as={Col} controlId="formPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="password"
                                onChange={onInputPW}
                            />{" "}
                        </Form.Group>
                    </Row>
                    <br></br>
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formManager">
                            <Form.Label>Manager Name</Form.Label>
                            <Form.Select
                                required
                                className="form-select"
                                onChange={onInputMN}
                            >
                                <option value="AM">AM</option>
                                <option value="PM">PM</option>
                                <option value="WHOLEDAY">Entire Day</option>
                            </Form.Select>
                        </Form.Group>
                    </Row>
                    <br></br>
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formRole">
                            <Form.Label>Role</Form.Label>
                            <Form.Select
                                required
                                className="form-select"
                                onChange={onInputRN}
                            >
                                <option value="AM">AM</option>
                                <option value="PM">PM</option>
                                <option value="WHOLEDAY">Entire Day</option>
                            </Form.Select>
                        </Form.Group>
                    </Row>
                    <br></br>
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formAnnual">
                            <Form.Label>Annual Leave Entitlement</Form.Label>
                            <Form.Control
                                required
                                type="number"
                                placeholder="Annual Leave Entitlement"
                                onChange={onInputALE}
                            ></Form.Control>
                        </Form.Group>
                    </Row>
                    <br></br>
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formMedical">
                            <Form.Label>Medical Leave Entitlement</Form.Label>
                            <Form.Control
                                required
                                type="number"
                                placeholder="Medical Leave Entitlement"
                                onChange={onInputMLE}
                            />{" "}
                        </Form.Group>
                    </Row>
                    <br></br>
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formCompensation">
                            <Form.Label>Compensation Leave Entitlement</Form.Label>
                            <Form.Control
                                required
                                type="number"
                                placeholder="Compensation Leave Entitlement"
                                onChange={onInputCLE}
                            />{" "}
                        </Form.Group>
                    </Row>
                </Col>
                <br></br>
                <Button type="submit">Submit</Button>
            </Form>
            <MyAlert
                showAlert={showAlert}
                variant="info"
                msg1="Result:"
                msg2={alertMsg}
                handleCLose={() => setShowAlert(false)}
            ></MyAlert>
        </LoginCheckWrapper>
    );
}

export default AdminEmployeeNew;