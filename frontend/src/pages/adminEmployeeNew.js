import React, { useState, useEffect } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { getUserinfoFromLocal } from "./utils/userinfo";
import PageTitle from "./components/pageTitle";
import { Form, Row, Col, Button } from "react-bootstrap";
import { createNewEmployee, getAllList } from "./utils/api/apiAdmin";
import MyAlert from "./components/myAlert";
import { useNavigate } from "react-router";

function AdminEmployeeNew() {
  const navigate = useNavigate();
  const [userName, setUserName] = useState();
  const [password, setPassword] = useState();
  const [roleName, setRoleName] = useState();
  const [type, setEmployeeType] = useState();
  const [managerName, setManagerName] = useState();
  const [annualLeaveEntitlement, setAnnualLeaveEntitlement] = useState();
  const [medicalLeaveEntitlement, setMedicalLeaveEntitlement] = useState(60);
  const [compensationLeaveEntitlement, setCompensationLeaveEntitlement] =
    useState();

  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();

  const onInputUN = ({ target: { value } }) => setUserName(value);
  const onInputPW = ({ target: { value } }) => setPassword(value);
  const onInputMN = ({ target: { value } }) => setManagerName(value);
  const onInputRN = ({ target: { value } }) => setRoleName(value);
  const onInputET = ({ target: { value } }) => setEmployeeType(value);
  const onInputALE = ({ target: { value } }) =>
    setAnnualLeaveEntitlement(value);
  const onInputMLE = ({ target: { value } }) =>
    setMedicalLeaveEntitlement(value);
  const onInputCLE = ({ target: { value } }) =>
    setCompensationLeaveEntitlement(value);

  const [validated, setValidated] = useState(false);
  const [bigList, setBigList] = useState([]);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getAllList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setBigList(list);
        });
    }
  };

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
        type: type,
        annualLeaveEntitlement: annualLeaveEntitlement,
        medicalLeaveEntitlement: medicalLeaveEntitlement,
        compensationLeaveEntitlement: compensationLeaveEntitlement,
      })
        .then((response) => {
          if (response.status == 200) {
            console.log(JSON.stringify(response.data));
            setAlertMsg(JSON.stringify(response.data));
            setShowAlert(true);
          }
        })
        .catch((error) => {
          setAlertMsg("error, please try again.");
          setShowAlert(true);
        });
    }
  };

  return (
    <LoginCheckWrapper allowRole={["ROLE_admin"]} runAfterCheck={loadData}>
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
              />
            </Form.Group>
            <Form.Group as={Col} controlId="formPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="password"
                onChange={onInputPW}
              />
            </Form.Group>
          </Row>
          <br></br>
          {roleName !== "Manager" && roleName !== "Admin" && (
            <Row className="mx-5" style={{ textAlign: "left" }}>
              <Form.Group as={Col} controlId="formManager">
                <Form.Label>Manager Name</Form.Label>
                <Form.Select
                  required
                  className="form-select"
                  onChange={onInputMN}
                >
                  <option value="" style={{ display: "none" }}>
                    Select Manager Name
                  </option>
                  {Array.isArray(bigList[0]) &&
                    bigList[0].map((value) => {
                      return (
                        <option value={value.managerName}>
                          {value.managerName}
                        </option>
                      );
                    })}
                </Form.Select>
              </Form.Group>
            </Row>
          )}
          <br></br>
          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formRole">
              <Form.Label>Role</Form.Label>
              <Form.Select
                required
                className="form-select"
                onChange={onInputRN}
              >
                <option value="" style={{ display: "none" }}>
                  Select a role
                </option>
                {Array.isArray(bigList[1]) &&
                  bigList[1].map((value, index, array) => {
                    return (
                      <option value={value.roleName}>{value.roleName}</option>
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
                onChange={onInputET}
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
                min={"0"}
                max={"365"}
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
                defaultValue="60"
                type="number"
                min={"0"}
                max={"365"}
                placeholder="Medical Leave Entitlement"
                onChange={onInputMLE}
              />
            </Form.Group>
          </Row>
          <br></br>
          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formCompensation">
              <Form.Label>Compensation Leave Entitlement</Form.Label>
              <Form.Control
                required
                type="number"
                min={"0"}
                max={"365"}
                placeholder="Compensation Leave Entitlement"
                onChange={onInputCLE}
              />
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
          navigate("/admin/employee");
        }}
      ></MyAlert>
    </LoginCheckWrapper>
  );
}

export default AdminEmployeeNew;
