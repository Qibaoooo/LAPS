import React, { useEffect, useState, useRef } from "react";
import MyNavBar from "./components/myNavBar";
import { setLeaveDataOnLoad, editLeave } from "./utils/api/apiStaff";
import { Form, Row, Col, Button } from "react-bootstrap";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { useSearchParams } from "react-router-dom";
import MyAlert from "./components/myAlert";

function StaffLeaveEdit() {
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id");

  // Assuming the leave application has fields like fromDate, toDate, type, and description
  const [fromDate, setFromDate] = useState();
  const [toDate, setToDate] = useState();
  const [leaveType, setLeaveType] = useState();
  const [description, setDescription] = useState();

  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();

  const onInputFromDate = ({ target: { value } }) => setFromDate(value);
  const onInputToDate = ({ target: { value } }) => setToDate(value);
  const onInputType = ({ target: { value } }) => setLeaveType(value);
  const onInputDescription = ({ target: { value } }) => setDescription(value);

  const [validated, setValidated] = useState(false);

  const formRef = useRef();

  const loadData = () => {
    setLeaveDataOnLoad(id, formRef); 
  };

  const onFormSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.stopPropagation();
      setValidated(true);
    } else {
      if (fromDate > toDate) {
        setAlertMsg("The start date must be before the end date. Please try again.");
        setShowAlert(true);
        return; // Stop the form submission
      }
      editLeave({ 
        fromDate: formRef.current.querySelector("#formFromDate").value,
        toDate: formRef.current.querySelector("#formToDate").value,
        type: formRef.current.querySelector("#formType").value,
        description: formRef.current.querySelector("#formDescription").value,
        id: id,
      })
        .then((response) => {
          if (response.status === 200) {
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
  };

  return (
    <LoginCheckWrapper
      allowRole={["ROLE_manager", "ROLE_staff"]}
      runAfterCheck={loadData}
    >
      <MyNavBar />
      <PageTitle title="Edit Leave Application"></PageTitle>

      <Form noValidate validated={validated} onSubmit={onFormSubmit} ref={formRef}>
        <Col md="6" className="mx-auto">
          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formFromDate">
              <Form.Label>Date From</Form.Label>
              <Form.Control
                required
                type="date"
                placeholder="Start Date"
                onChange={onInputFromDate}
                value={fromDate}
              />
            </Form.Group>

            <Form.Group as={Col} controlId="formToDate" >
              <Form.Label>Date To</Form.Label>
              <Form.Control
                required
                type="date"
                placeholder="End Date"
                onChange={onInputToDate}
                value={toDate}
              />
            </Form.Group>
          </Row>
          <br />

          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formType">
              <Form.Label>Leave Type</Form.Label>
              <Form.Select required onChange={onInputType}>
                <option value="AnnualLeave">Annual</option>
                <option value="MedicalLeave">Medical</option>
                <option value="CompensationLeave">Compensation</option>
              </Form.Select>
            </Form.Group>
          </Row>
          <br />

          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formDescription">
              <Form.Label>Description</Form.Label>
              <Form.Control
                required
                as="textarea"
                placeholder="Details of your leave"
                onChange={onInputDescription}
                value={description}
              />
            </Form.Group>
          </Row>
        </Col>
        <br />

        <Button type="submit">Submit</Button>
      </Form>
      <MyAlert showAlert={showAlert} variant="info" msg1="Result:" msg2={alertMsg} handleCLose={() => setShowAlert(false)}></MyAlert>
    </LoginCheckWrapper>
  );
}

export default StaffLeaveEdit;
