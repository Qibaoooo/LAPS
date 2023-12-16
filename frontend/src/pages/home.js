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
  const [showManager, setShowManager] = useState(false);

  useEffect(() => {
    let infoLocal = getUserinfoFromLocal();
    if (infoLocal) {
      getUserDetails()
        .then((resp) => resp.data)
        .then((data) => setUserDetails(data));

      if (infoLocal.roleId !== "ROLE_admin" && showLeaves === false) {
        setShowLeaves(true);
      }
      if (infoLocal.roleId === "ROLE_staff" && showManager === false) {
        setShowManager(true);
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
            {showManager && (
              <Card.Subtitle className="mb-2 text-muted">
                {`Manager: ${userDetails.manager}`}
              </Card.Subtitle>
            )}
            {showLeaves && (
              <div>
                <Card.Text>{`Annual Leave Entitlement: ${userDetails.annualLeaveEntitlement}`}</Card.Text>
                <Card.Text>{`Medical Leave Entitlement: ${userDetails.medicalLeaveEntitlement}`}</Card.Text>
                <Card.Text>{`Compensation Leave Entitlement: ${userDetails.compensationLeaveEntitlement}`}</Card.Text>
                <Card.Link href="/staff/leave/new"> New Leave </Card.Link>
                <Card.Link href="/staff/claim/new"> New Claim </Card.Link>
              </div>
            )}
          </Card.Body>
        </Card>
      </div>
    </LoginCheckWrapper>
  );
}

export default HomePage;
