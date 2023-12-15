import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { getUserDetails } from "./utils/api/apiUserDetails";
import { getUserinfo } from "./utils/userinfo";
import MyTable from "./components/myTable";
import { Card, Col } from "react-bootstrap";
import PageTitle from "./components/pageTitle";

function HomePage() {
  const [userDetails, setUserDetails] = useState([]);
  const [showLeaves, setShowLeaves] = useState(false);

  useEffect(() => {
    if (getUserinfo()) {
      getUserDetails()
        .then((resp) => resp.data)
        .then((data) => setUserDetails(data));

      setShowLeaves(getUserinfo().role !== "ROLE_admin");
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
            {showLeaves && (
              <div>
                <Card.Text>
                  <p>{`Annual Leave Entitlement: ${userDetails.annualLeaveEntitlement}`}</p>
                  <p>{`Medical Leave Entitlement: ${userDetails.medicalLeaveEntitlement}`}</p>
                  <p>{`Compensation Leave Entitlement: ${userDetails.compensationLeaveEntitlement}`}</p>
                </Card.Text>
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

// {"role":"Staff","compensationLeaveEntitlement":0,
// "annualLeaveEntitlement":14,"managerId":274,
// "medicalLeaveEntitlement":0}
