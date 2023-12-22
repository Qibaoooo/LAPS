import React, { useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Form, Row, Col, Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import { createLeave } from "./utils/api/apiStaff";
import MyAlert from "./components/myAlert";
import { checkIfWeekend } from "./utils/dateUtil";

function StaffLeaveNew() {
  const currentDate = new Date().toISOString().split("T")[0];
  const [fromDate, setFromDate] = useState(currentDate);
  const [toDate, setToDate] = useState(currentDate);
  const [leaveType, setLeaveType] = useState();
  const [description, setDescription] = useState();

  const [validated, setValidated] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const [showWeekendAlert, setShowWeekendAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();

  const onInputFromDate = ({ target: { value } }) => {
    if (checkIfWeekend(value)) {
      setShowWeekendAlert(true);
    } else {
      setFromDate(value);
      setShowWeekendAlert(false);
    }
  };
  const onInputToDate = ({ target: { value } }) => {
    if (checkIfWeekend(value)) {
      setShowWeekendAlert(true);
    } else {
      setToDate(value);
      setShowWeekendAlert(false);
    }
  };
  
  const onInputType = ({ target: { value } }) => setLeaveType(value);
  const onInputDescription = ({ target: { value } }) => setDescription(value);

  const loadData = () => {
    setLeaveType("MedicalLeave");
  };

  const onFormSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.stopPropagation();
      setValidated(true);
    } else {
      if (fromDate > toDate) {
        setAlertMsg(
          "The start date must be before the end date. Please try again."
        );
        setShowAlert(true);
        return; // Stop the form submission
      } else {
        createLeave({
          description: description,
          toDate: toDate,
          fromDate: fromDate,
          leaveapplicationtype: leaveType,
        })
          .then((response) => {
            if (response.status == 200) {
              console.log(JSON.stringify(response.data));
              setAlertMsg(JSON.stringify(response.data));
              setShowAlert(true);
            }
          })
          .catch((error) => {
            if (error.response && error.response.status === 400) {
              // Handle 400 response
              setAlertMsg(JSON.stringify(error.response.data));
              setShowAlert(true);
            } else {
              // Handle other errors
              setAlertMsg("Error, please try again.");
              setShowAlert(true);
            }
          });
      }
    }
  };

  const closeButton = () => {
    setShowAlert(false);
    window.location.href = "list";
  };

  return (
    <LoginCheckWrapper
      allowRole={["ROLE_manager", "ROLE_staff"]}
      runAfterCheck={loadData}
    >
      <MyNavBar></MyNavBar>
      <PageTitle title="Create New Leave Application"></PageTitle>

      <Form noValidate validated={validated} onSubmit={onFormSubmit}>
        <Col md="6" className="mx-auto">
          <Row className="mx-5" style={{ textAlign: "left" }}>
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
              </Form.Select>
            </Form.Group>
          </Row>

          <br></br>

          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="fromDate">
              <Form.Label>Start Date</Form.Label>
              <Form.Control
                required
                type="date"
                placeholder="date"
                value={fromDate}
                onChange={onInputFromDate}
              />
            </Form.Group>

            <Form.Group as={Col} controlId="toDate">
              <Form.Label>End Date</Form.Label>
              <Form.Control
                required
                type="date"
                placeholder="date"
                value={toDate}
                onChange={onInputToDate}
              />
            </Form.Group>
          </Row>

          <br></br>
          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formDescription">
              <Form.Label>Description</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="Details for your leave"
                onChange={onInputDescription}
              ></Form.Control>
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
        handleCLose={closeButton}
      ></MyAlert>
      <MyAlert
        showAlert={showWeekendAlert}
        variant="warning"
        msg1="Invalid date"
        msg2="FromDate/ToDate cannot be a weekend day."
        handleCLose={() => {}}
        showReturnTo={false}
      ></MyAlert>
    </LoginCheckWrapper>
  );
}

export default StaffLeaveNew;
