import React, { useEffect, useRef, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getEmployeeList } from "./utils/api/apiAdmin";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { Form, Row, Col, Button } from "react-bootstrap";
import { editEmployeeInfo, setEditDataOnLoad, getAllList } from "./utils/api/apiAdmin";
import { useSearchParams } from "react-router-dom";
import MyAlert from "./components/myAlert";
import { useNavigate } from 'react-router';

function AdminEmployeeEdit() {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const id = searchParams.get("id");

    const [showAlert, setShowAlert] = useState(false);
    const [alertMsg, setAlertMsg] = useState();
    const [bigList, setBigList] = useState([]);

    const [validated, setValidated] = useState(false);

    const formRef = useRef();

    const onFormSubmit = (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        console.log(id);
        if (form.checkValidity() === false) {
            event.stopPropagation();
            setValidated(true);
        } else {
            editEmployeeInfo({
                username: formRef.current.querySelector("#formUserName").value,
                password: formRef.current.querySelector("#formPassword").value,
                managerName: formRef.current.querySelector("#formManager").value,
                roleName: formRef.current.querySelector("#formRole").value,
                type: formRef.current.querySelector("#formType").value,
                annualLeaveEntitlement: formRef.current.querySelector("#formAnnual").value,
                medicalLeaveEntitlement: formRef.current.querySelector("#formMedical").value,
                compensationLeaveEntitlement: formRef.current.querySelector("#formCompensation").value,
                id: id
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

    const loadData = () => {
        // get options for manager field
        getAllList()
            .then((response) => response.data)
            .then((list) => {
                console.log(list);
                setBigList(list);
            });
        
        // pre-fill all available fields
        setEditDataOnLoad(id, formRef);
    };

    return (
        <LoginCheckWrapper allowRole={["ROLE_admin"]} runAfterCheck={loadData}>
            <MyNavBar></MyNavBar>
            <PageTitle title="Edit Employee"></PageTitle>


            <Form noValidate validated={validated} onSubmit={onFormSubmit} ref={formRef}>
                <Col md="6" className="mx-auto">
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formUserName">
                            <Form.Label>UserName</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="user name"
                            />{" "}
                        </Form.Group>
                        <Form.Group as={Col} controlId="formPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                required
                                type="password"
                                placeholder="password"
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
                            >
                                {Array.isArray(bigList[0]) &&
                                    bigList[0].map((value, index) => {
                                        return (
                                            <option key={index} value={value.managerName}>
                                                {value.managerName}
                                            </option>
                                        );
                                    })}
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
                            >
                                {Array.isArray(bigList[1]) &&
                                    bigList[1].map((value, index, array) => {
                                        return (
                                            <option key={index} value={value.roleName}>{value.roleName}</option>
                                        );
                                    })}
                            </Form.Select>
                        </Form.Group>
                    </Row>
                    <br></br>
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formType">
                            <Form.Label>Type</Form.Label>
                            <Form.Select
                                required
                                className="form-select"
                            >
                                <option value="" style={{ display: "none" }}>
                                    Select a type
                                </option>
                                <option value="Administrative">Administrative</option>
                                <option value="Professional">Professional</option>
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
                handleCLose={() => {
                    setShowAlert(false);
                    navigate('/admin/employee');
                }}
            ></MyAlert>
        </LoginCheckWrapper>
    );
}

export default AdminEmployeeEdit;