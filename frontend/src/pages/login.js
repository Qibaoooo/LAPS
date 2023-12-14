import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import { Alert, Container, Stack } from "react-bootstrap";
import MyNavBar from "./components/myNavBar";
import { signup } from "./utils/apiAuth";
import { setUserinfo } from "./utils/userinfo"

function LoginPage() {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();
  const onInputUN = ({ target: { value } }) => setUsername(value);
  const onInputPW = ({ target: { value } }) => setPassword(value);

  const onFormSubmit = async (e) => {
    e.preventDefault();

    const response = await signup(username, password);
    if (response.status == 200) {      
      console.log(JSON.stringify(response.data.username))
      setUserinfo(response.data)
      // window.location.reload()
      window.location.href = "/staff"
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
