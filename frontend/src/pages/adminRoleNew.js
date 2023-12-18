import React, {useState} from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { Form, Row, Col, Button } from "react-bootstrap";
import { createNewRole } from "./utils/api/apiAdmin";
import MyAlert from "./components/myAlert";

function AdminRoleNew(){

    
    const[name,setName]=useState();
    const[description,setDescription]=useState();

    const [showAlert, setShowAlert] = useState(false);
    const [alertMsg, setAlertMsg] = useState();

    const onInputRN=({target:{value}})=> setName(value);
    const onInputDS=({target:{value}})=> setDescription(value);

    const [validated, setValidated] = useState(false);

    const onFormSubmit = (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        
        if (form.checkValidity() === false) {
          event.stopPropagation();
          setValidated(true);
        } else {
            createNewRole({
                rolename: name,
                description: description,

            }).then((response)=>{
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

    return(
        <LoginCheckWrapper>
            <MyNavBar></MyNavBar>
            <PageTitle title="Create New Role"></PageTitle>

            <Form noValidate validated={validated} onSubmit={onFormSubmit}>
                <Col md="6" className="mx-auto">
                    <Row className="mx-5" style={{ textAlign: "left" }}>
                        <Form.Group as={Col} controlId="formRoleName">
                            <Form.Label>RoleName</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="role name"
                                onChange={onInputRN}
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
                                    placeholder="description"
                                    onChange={onInputDS}
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
          handleCLose={() => setShowAlert(false)}
        ></MyAlert>
    </LoginCheckWrapper>
  );
}

export default AdminRoleNew;






