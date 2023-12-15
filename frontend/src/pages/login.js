import React, { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import { Alert, Container, Stack } from "react-bootstrap";
import MyNavBar from "./components/myNavBar";
import { login } from "./utils/api/apiAuth";
import { getUserinfo, setUserinfo } from "./utils/userinfo";

function LoginPage() {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState();
  const onInputUN = ({ target: { value } }) => setUsername(value);
  const onInputPW = ({ target: { value } }) => setPassword(value);

  useEffect(() => {
    if (getUserinfo()) {
      // alr logged in, redirect
      if (getUserinfo().role === "staff") {
        window.location.href = "/staff";
      } else {
        window.location.href = "/";
      }
    }
  }, []);

  const onFormSubmit = async (e) => {
    e.preventDefault();
    login(username, password)
      .then((response) => {
        if (response.status == 200) {
          console.log(JSON.stringify(response.data.username));
          alert(JSON.stringify(response.data))
          setUserinfo(response.data);
          window.location.href = "/staff";
        } else {
          setAlertMsg(JSON.stringify(response));
          setShowAlert(true);
        }
      })
      .catch((error) => {
        if (error.response.status == 401) {
          setAlertMsg("incorrect username/password.");
          setShowAlert(true);
        } else {
          setAlertMsg("error, please try again.");
          setShowAlert(true);
        }
      });
  };

  return (
    <div>
      <MyNavBar></MyNavBar>
      <Form className="mx-5">
        <Stack gap={3} className="col-sm-4 mx-auto mt-5">
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
          <div>
            <Button
              type="submit"
              onClick={onFormSubmit}
              style={{ maxWidth: "100px" }}
            >
              Login
            </Button>
          </div>
        </Stack>
        <Container className="col-md-4 mt-5">
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
