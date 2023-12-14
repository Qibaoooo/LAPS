import React, { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Cookies from "js-cookie";
import { Alert, Container, Stack } from "react-bootstrap";
import MyNavBar from "./components/myNavBar";
import { json } from "react-router-dom";

function LoginPage() {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();
  const onInputUN = ({ target: { value } }) => setUsername(value);
  const onInputPW = ({ target: { value } }) => setPassword(value);

  const onFormSubmit = async (e) => {
    e.preventDefault();
    console.log(username);
    console.log(password);

    const data = new URLSearchParams();
    
    data.append("username", username);
    data.append("password", password);
    const response = await fetch(
      "http://localhost:8080/api/login/authenticate",
      {
        method: "POST",
        mode: "cors",
        credentials: "include",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "Access-Control-Allow-Credentials": true,
          "Access-Control-Allow-Origin": "http://localhost:3000",
          "Access-Control-Allow-Methods": "GET,POST,PATCH,OPTIONS",
        },
        referrer: "http://localhost:8080/login",
        body: data,
      }
    );
    if (response.status == 200) {
      let info = JSON.stringify(await response.json())
      
      console.log(info)
      Cookies.set("LAPS_USERINFO", info)
      // how to parse this later on other pages:
      // let userinfo = JSON.parse(Cookies.get("LAPS_USERINFO")).
      
      // window.location.reload()
      // window.location.href = "/staff"

    } else {
      setAlertMsg(await response.text())
      setShowAlert(true);
    }
  };

  return (
    <div>
      <MyNavBar></MyNavBar>
      <Form className="mx-5">
        <Stack gap={3} className="col-sm-4 mx-auto">
          <Form.Group as={Col} controlId="formGridUsername">
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="text"
              onChange={onInputUN}
              placeholder="Enter Username"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              onChange={onInputPW}
              placeholder="Password"
            />
          </Form.Group>
          <Button variant="primary" type="submit" onClick={onFormSubmit}>
            Submit
          </Button>
        </Stack>
        <Container className="m-5">
          <Alert show={showAlert} variant="warning">
            <p>Login failed, error msg: </p>
            <p>{alertMsg}</p>
            <div className="">
              <Button
                onClick={() => setShowAlert(false)}
                variant="outline-warning"
              >
                Close
              </Button>
            </div>
          </Alert>
        </Container>
      </Form>
    </div>
  );
}

export default LoginPage;
