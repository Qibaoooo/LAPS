import React, { useState } from "react";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import MyNavBar from "./components/myNavBar";
import PageTitle from "./components/pageTitle";
import { Button, Col, Form, Row } from "react-bootstrap";
import MyAlert from "./components/myAlert";
import { getLeaveReport } from "./utils/api/apiManager";

function ManagerReporting() {
  const [validated, setValidated] = useState(false);
  const [fromDate, setFromDate] = useState();
  const [toDate, setToDate] = useState();
  const [leaveType, setLeaveType] = useState("MedicalLeave");

  const [showLeaveFormAlert, setShowLeaveFormAlert] = useState(false);
  const [showClaimFormAlert, setShowClaimFormAlert] = useState(false);

  const loadData = () => {};
  const onFormSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.stopPropagation();
      setValidated(true);
    } else {
      console.log(fromDate);
      console.log(toDate);
      console.log(leaveType);
      if (fromDate > toDate) {
        console.log("can't");
        setShowLeaveFormAlert(true);
        return;
      }

      getLeaveReport(fromDate, toDate, leaveType);
      setShowLeaveFormAlert(false);

    }
  };
  const onInputFDT = ({ target: { value } }) => setFromDate(value);
  const onInputTDT = ({ target: { value } }) => setToDate(value);
  const onInputType = ({ target: { value } }) => setLeaveType(value);

  return (
    <div>
      <LoginCheckWrapper allowRole={["ROLE_manager"]} runAfterCheck={loadData}>
        <MyNavBar />
        <PageTitle title="Generate Manager Reports"></PageTitle>
        <br></br>
        <br></br>
        <Form noValidate validated={validated} onSubmit={onFormSubmit}>
          <h5>Report: Employee on Leave During Selected Periods</h5>
          <Col md="5" className="mx-auto">
            <Row className="m-3" style={{ textAlign: "left" }}>
              <Form.Group as={Col} controlId="formFromDate">
                <Form.Label>FromDate</Form.Label>
                <Form.Control
                  required
                  type="date"
                  placeholder="date"
                  onChange={onInputFDT}
                />
              </Form.Group>
              <Form.Group as={Col} controlId="formToDate">
                <Form.Label>ToDate</Form.Label>
                <Form.Control
                  required
                  type="date"
                  placeholder="date"
                  onChange={onInputTDT}
                />
              </Form.Group>
            </Row>
            <Row className="m-3" style={{ textAlign: "left" }}>
              <Form.Group as={Col} controlId="leaveType">
                <Form.Label>Leave Type</Form.Label>
                <Form.Select
                  required
                  className="form-select"
                  onChange={onInputType}
                >
                  <option value="MedicalLeave">Medical Leave</option>
                  <option value="AnnualLeave">Annual Leave</option>
                  <option value="CompensationLeave">Compensation Leave</option>
                  <option value="All">All</option>
                </Form.Select>
              </Form.Group>
            </Row>
            <Button type="submit">Download</Button>
          </Col>
          <MyAlert
            variant="warning"
            msg1="Wrong input"
            msg2="FromDate cannot be later than ToDate"
            showAlert={showLeaveFormAlert}
            handleCLose={() => {}}
            showReturnTo={false}
          ></MyAlert>
          <MyAlert
            variant="warning"
            msg1=""
            msg2=""
            showAlert={showClaimFormAlert}
            handleCLose={() => {}}
            showReturnTo={false}
          ></MyAlert>
        </Form>
      </LoginCheckWrapper>
    </div>
  );
}

export default ManagerReporting;
