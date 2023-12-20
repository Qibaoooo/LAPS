import React, { useState,useRef } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { Form, Row, Col, Button } from "react-bootstrap";
import MyAlert from "./components/myAlert";
import { editRole, setRoleDataOnLoad } from "./utils/api/apiAdmin";
import { useSearchParams } from "react-router-dom";
import { useNavigate } from 'react-router';

function AdminRoleEdit(){
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const id = searchParams.get("id");

    const[role, setRole]=useState({});

    // const[name,setName]=useState();
    // const[description,setDescription]=useState();

    const [showAlert, setShowAlert] = useState(false);
    const [alertMsg, setAlertMsg] = useState();

    // const onInputRN=({target:{value}})=> setName(value);
    // const onInputDS=({target:{value}})=> setDescription(value);

    const [validated, setValidated] = useState(false);

    const formRef = useRef();

    const loadData = () => {
        setRoleDataOnLoad(id, formRef, setRole);
      };

    const onFormSubmit = (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        console.log(id);
        if (form.checkValidity() === false) {
          event.stopPropagation();
          setValidated(true);
        } else {
            editRole({
                name: formRef.current.querySelector("#formName").value,
                description:formRef.current.querySelector("#formDescription").value,            
                id:role.id,          
            })
             .then((response)=>{
                if (response.status==200){
                    console.log(JSON.stringify(response.data));
                    setAlertMsg(JSON.stringify(response.data));
                    setShowAlert(true);
                }
             })
             .catch((error)=>{
                setAlertMsg("error,please try again.");
                setShowAlert(true);
             });
    }
};

 return(
    <LoginCheckWrapper
        
        allowRole={["ROLE_admin"]}
        runAfterCheck={loadData}
    >
        <MyNavBar></MyNavBar>
        <PageTitle title="Edit Role"></PageTitle>

        <Form
          noValidate
          validated={validated}
          onSubmit={onFormSubmit}
          ref={formRef}
        >
          <Col md="6" className="mx-auto">
          <Row className="mx-5" style={{ textAlign: "left" }}>
            <Form.Group as={Col} controlId="formName">
              <Form.Label>Role Name</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="role name"
                //onChange={onInputRN}
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
                placeholder="Details for role"
                //onChange={onInputDS}
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
        handleCLose={() => {
          setShowAlert(false);
          navigate('/admin/role');
      }}
      ></MyAlert>
    </LoginCheckWrapper>
   );
}

export default AdminRoleEdit;
    
 