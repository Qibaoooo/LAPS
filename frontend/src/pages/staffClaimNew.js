import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { Form, Row, Col, Button } from "react-bootstrap";
import { createNewClaim } from "./utils/api/apiStaff";
import MyAlert from "./components/myAlert";
import { useNavigate } from "react-router-dom";

function StaffClaimNew() {
  const navigate = useNavigate();
  const [claimDate, setClaimDate] = useState();
  const [claimTime, setClaimTime] = useState();
  const [description, setDescription] = useState();

  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();

  const onInputDT = ({ target: { value } }) => setClaimDate(value);
  const onInputTM = ({ target: { value } }) => setClaimTime(value);
  const onInputDS = ({ target: { value } }) => setDescription(value);

  const [validated, setValidated] = useState(false);

  const loadData = () => {
    // set the value of OT time to AM on page load.
    setClaimTime("AM");
  };

  const onFormSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.stopPropagation();
      setValidated(true);
    } else {
      createNewClaim({
        description: description,
        overtimeTime: claimTime,
        overtimeDate: claimDate,
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
    <LoginCheckWrapper
      allowRole={["ROLE_manager", "ROLE_staff"]}
      runAfterCheck={loadData}
    >
      <MyNavBar></MyNavBar>
      <PageTitle title="Create New Compensation Claim"></PageTitle>

      <Form noValidate validated={validated} onSubmit={onFormSubmit}>
        <Col md="6" className="mx-auto">
          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formDate">
              <Form.Label>Date</Form.Label>
              <Form.Control
                required
                type="date"
                placeholder="date"
                onChange={onInputDT}
              />{" "}
            </Form.Group>
            <Form.Group as={Col} controlId="formTime">
              <Form.Label>Time</Form.Label>
              <Form.Select
                required
                className="form-select"
                onChange={onInputTM}
              >
                <option value="AM">AM</option>
                <option value="PM">PM</option>
                <option value="WHOLEDAY">Entire Day</option>
              </Form.Select>
            </Form.Group>
          </Row>
          <br></br>
          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formDescription">
              <Form.Label>Description</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="Details for your claim"
                onChange={onInputDS}
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
        handleCLose={() => navigate("/staff/claim/list")}
      ></MyAlert>
    </LoginCheckWrapper>
  );
}

export default StaffClaimNew;
