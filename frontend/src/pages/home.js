import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { getUserDetails } from "./utils/api/apiUserDetails";
import { getUserinfoFromLocal } from "./utils/userinfo";
import MyTable from "./components/myTable";
import { Card, Col } from "react-bootstrap";
import PageTitle from "./components/pageTitle";

function HomePage() {
  const [userDetails, setUserDetails] = useState([]);
  const [showLeaves, setShowLeaves] = useState(false);
  const [roleId, setRoleId] = useState("");

  useEffect(() => {
    let infoLocal = getUserinfoFromLocal();
    if (infoLocal) {
      getUserDetails()
        .then((resp) => resp.data)
        .then((data) => setUserDetails(data));

      if (roleId !== infoLocal.roleId) {
        setRoleId(infoLocal.roleId);
      }
    }
  }, []);

  return (
    <LoginCheckWrapper>
      <MyNavBar></MyNavBar>
      <PageTitle title="Home Page"></PageTitle>
      <div className="d-flex justify-content-center">
        <Card className="w-50">
          <Card.Body>
            <Card.Title>{userDetails.username}</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">
              {userDetails.role}
            </Card.Subtitle>
            {roleId === "ROLE_staff" && (
              <Card.Subtitle className="mb-2 text-muted">
                {`Manager: ${userDetails.manager}`}
              </Card.Subtitle>
            )}
            {roleId !== "ROLE_admin" && (
              <div>
                <Card.Text>{`Annual Leave Entitlement: ${userDetails.annualLeaveEntitlement}`}</Card.Text>
                <Card.Text>{`Medical Leave Entitlement: ${userDetails.medicalLeaveEntitlement}`}</Card.Text>
                <Card.Text>{`Compensation Leave Entitlement: ${userDetails.compensationLeaveEntitlement}`}</Card.Text>
              </div>
            )}
            {roleId === "ROLE_staff" && (
              <div className="mt-3">
              <Card.Link href="/staff/leave/new"> New Leave </Card.Link>
                <Card.Link href="/staff/claim/new"> New Claim </Card.Link>
              </div>
            )}
            {roleId === "ROLE_manager" && (
              <div className="mt-3">
                <Card.Link href="/?"> View Suborinates Leaves </Card.Link>
                <br />
                <Card.Link href="/?"> New Suborinates Claims </Card.Link>
              </div>
            )}
            {roleId === "ROLE_admin" && (
              <div className="mt-3">
              <Card.Link href="/?"> Manage Roles </Card.Link>
                <br />
                <Card.Link href="/?"> Manage Users </Card.Link>
              </div>
            )}
          </Card.Body>
        </Card>
      </div>
    </LoginCheckWrapper>
  );
}

export default HomePage;
