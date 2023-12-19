import React, {useState} from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Form, Row, Col, Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";

function StaffLeaveNew() {
  const [fromDate, setFromDate] = useState();
  const [toDate, setToDate] = useState();
  const [leaveType, setLeaveType] = useState();
  const [description, setDescription] = useState();


  const onFormSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    
  };


  
  const loadData = () =>{
    setFromDate(new Date());
    setToDate(new Date());
    
  }

  return (
    <LoginCheckWrapper
      allowRole={["ROLE_manager", "ROLE_staff"]}
      runAfterCheck={loadData}>
      <MyNavBar></MyNavBar>
      <PageTitle title="Create New Leave Application"></PageTitle>

      <Form onSubmit={onFormSubmit}>
        <Col md="6" className="mx-auto">
          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="leaveType">
              <Form.Label>Leave Type</Form.Label>
              <Form.Select
                required
                className="form-select"
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
                value = {fromDate}
              />{" "}
            </Form.Group>

            <Form.Group as={Col} controlId="toDate">
              <Form.Label>End Date</Form.Label>
              <Form.Control
                required
                type="date"
                placeholder="date"
                value = {toDate}
              />{" "}
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
                
              ></Form.Control>
            </Form.Group>
          </Row>
        </Col>
        <br></br>
        <Button type="submit">Submit</Button>
      </Form>
    </LoginCheckWrapper>
  );
}

export default StaffLeaveNew;
