import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { getUserDetails } from "./utils/api/apiUserDetails";
import { getUserinfoFromLocal } from "./utils/userinfo";
import MyTable from "./components/myTable";
import { Card, Col, Table } from "react-bootstrap";
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
        .then((data) => {
          console.log(data)
          setUserDetails(data);
        });

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
                <Table className="homeTable" bordered>
                  <thead>
                    <tr>
                      <td>Type</td>
                      <td>Available</td>
                      <td>Used</td>
                      <td>Yearly Entitlement</td>
                    </tr>
                  </thead>
                  <tbody>
                    <tr style={{}}>
                      <td style={{  }}>
                        <Card.Text>{`Annual Leave Entitlement`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.annualLeaveUsed}`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.annualLeaveLeft}`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.annualLeaveEntitlement}`}</Card.Text>
                      </td>
                    </tr>
                    <tr style={{}}>
                      <td style={{  }}>
                        <Card.Text>{`Medical Leave Entitlement`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.medicalLeaveUsed}`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.medicalLeaveLeft}`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.medicalLeaveEntitlement}`}</Card.Text>
                      </td>
                    </tr>
                    <tr style={{}}>
                      <td style={{  }}>
                        <Card.Text>{`Compensation Leave Entitlement`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.compensationLeaveUsed}`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.compensationLeaveLeft}`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.compensationLeaveEntitlement}`}</Card.Text>
                      </td>
                    </tr>
                  </tbody>
                </Table>
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
                <Card.Link href="/manager/leave/list">
                  {" "}
                  View Suborinates Leave Applications{" "}
                </Card.Link>
                <br />
                <br />
                <Card.Link href="/manager/claim/list">
                  {" "}
                  View Suborinates OT Claims{" "}
                </Card.Link>
              </div>
            )}
            {roleId === "ROLE_admin" && (
              <div className="mt-3">
                <Card.Link href="/admin/role"> Manage Roles </Card.Link>
                <Card.Link href="/admin/employee"> Manage Users </Card.Link>
              </div>
            )}
          </Card.Body>
        </Card>
      </div>
    </LoginCheckWrapper>
  );
}

export default HomePage;
