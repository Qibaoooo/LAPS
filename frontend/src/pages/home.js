import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { getUserDetails } from "./utils/api/apiUserDetails";
import { getUserinfoFromLocal } from "./utils/userinfo";
import MyTable from "./components/myTable";
import { Button, Card, Col, Table } from "react-bootstrap";
import PageTitle from "./components/pageTitle";

function HomePage() {
  const [userDetails, setUserDetails] = useState([]);
  const [showYear, setShowYear] = useState("Current");
  const [roleId, setRoleId] = useState("");
  const [available, setAvailable] = useState({});
  const [used, setUsed] = useState({});

  const loadData = () => {
    let infoLocal = getUserinfoFromLocal();
    if (infoLocal) {
      getUserDetails()
        .then((resp) => resp.data)
        .then((data) => {
          console.log(data);
          setUserDetails(data);
          setUsed({
            annual: data.annualLeaveUsed,
            medical: data.medicalLeaveUsed,
            comp: data.compensationLeaveUsed,
          });
          setAvailable({
            annual: data.annualLeaveLeft,
            medical: data.medicalLeaveLeft,
            comp: data.compensationLeaveLeft,
          });
        });

      if (roleId !== infoLocal.roleId) {
        setRoleId(infoLocal.roleId);
      }
    }
  };

  const handleClickToggle = (e) => {
    e.preventDefault();
    if (showYear === "Current") {
      setDataForNextYear()
    } else {
      setDataForCurrentYear()
    }
    // console.log(showYear);
  };

  const setDataForNextYear = () => {
    setShowYear("Next");
    setUsed({
      annual: userDetails.annualLeaveUsedNextYear,
      medical: userDetails.medicalLeaveUsedNextYear,
      comp: userDetails.compensationLeaveUsedNextYear,
    });
    setAvailable({
      annual: userDetails.annualLeaveLeftNextYear,
      medical: userDetails.medicalLeaveLeftNextYear,
      comp: userDetails.compensationLeaveLeftNextYear,
    });
  };

  const setDataForCurrentYear = () => {
    setShowYear("Current");
    setUsed({
      annual: userDetails.annualLeaveUsed,
      medical: userDetails.medicalLeaveUsed,
      comp: userDetails.compensationLeaveUsed,
    });
    setAvailable({
      annual: userDetails.annualLeaveLeft,
      medical: userDetails.medicalLeaveLeft,
      comp: userDetails.compensationLeaveLeft,
    });
  };

  return (
    <LoginCheckWrapper runAfterCheck={loadData}>
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
                      <td colSpan="4">
                        <p>Showing {showYear} Year Data</p>
                        <Button
                          variant="primary"
                          size="sm"
                          style={{ width: '20%' }}
                          onClick={handleClickToggle}
                        >
                          Toggle
                        </Button>
                      </td>
                    </tr>
                    <tr>
                      <td>Type</td>
                      <td>Used</td>
                      <td>Available</td>
                      <td>Yearly Entitlement</td>
                    </tr>
                  </thead>
                  <tbody>
                    <tr style={{}}>
                      <td style={{}}>
                        <Card.Text>{`Annual Leave Entitlement`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{used.annual}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{available.annual}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.annualLeaveEntitlement}`}</Card.Text>
                      </td>
                    </tr>
                    <tr style={{}}>
                      <td style={{}}>
                        <Card.Text>{`Medical Leave Entitlement`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{used.medical}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{available.medical}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{`${userDetails.medicalLeaveEntitlement}`}</Card.Text>
                      </td>
                    </tr>
                    <tr style={{}}>
                      <td style={{}}>
                        <Card.Text>{`Compensation Leave Entitlement`}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{used.comp}</Card.Text>
                      </td>
                      <td>
                        <Card.Text>{available.comp}</Card.Text>
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
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '15%', marginRight: '50px' }}
                  onClick={() => {
                    window.location.href =
                      "/staff/leave/new";
                  }}
                >
                  New Leave
                </Button>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '15%', marginRight: '50px' }}
                  onClick={() => {
                    window.location.href =
                      "/staff/leave/list";
                  }}
                >
                  View Leave
                </Button>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '15%', marginRight: '50px' }}
                  onClick={() => {
                    window.location.href =
                      "/staff/leave/new";
                  }}
                >
                  New Claim
                </Button>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '15%' }}
                  onClick={() => {
                    window.location.href =
                      "/staff/claim/list";
                  }}
                >
                  View Claim
                </Button>
              </div>
            )}
            {roleId === "ROLE_manager" && (
              <div className="mt-3">
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '40%', marginRight: '50px' }}
                  onClick={() => {
                    window.location.href =
                    "/manager/leave/list";
                  }}
                >
                  View Suborinates' Leave Applications
                </Button>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '40%' }}
                  onClick={() => {
                    window.location.href =
                    "/manager/claim/list";
                  }}
                >
                  View Suborinates' OT Claims
                </Button>
                <br/><br/>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '40%', marginRight: '50px' }}
                  onClick={() => {
                    window.location.href =
                    "/manager/leave/history";
                  }}
                >
                  View Suborinates' Leave History
                </Button>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '40%' }}
                  onClick={() => {
                    window.location.href =
                    "/manager/claim/history";
                  }}
                >
                  View Suborinates' Claim History
                </Button>
                <br/><br/>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '40%' }}
                  onClick={() => {
                    window.location.href =
                    "/manager/reporting";
                  }}
                >
                  Generate Reports
                </Button>
              </div>
            )}
            {roleId === "ROLE_admin" && (
              <div className="mt-3">
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '20%', marginRight: '50px' }}
                  onClick={() => {
                    window.location.href =
                    "/admin/employee";
                  }}
                >
                  Manage Users
                </Button>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '20%' }}
                  onClick={() => {
                    window.location.href =
                    "/admin/role";
                  }}
                >
                  View Users
                </Button>
                <br/><br/>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '20%', marginRight: '50px' }}
                  onClick={() => {
                    window.location.href =
                    "/admin/role";
                  }}
                >
                  Manage Roles
                </Button>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '20%' }}
                  onClick={() => {
                    window.location.href =
                    "/admin/role/list";
                  }}
                >
                  View Roles
                </Button>
                <br/><br/>
                <Button
                  variant="primary"
                  size="sm"
                  style={{ width: '20%' }}
                  onClick={() => {
                    window.location.href =
                    "/admin/holidays";
                  }}
                >
                  Manage Holidays
                </Button>
              </div>
            )}
          </Card.Body>
        </Card>
      </div>
    </LoginCheckWrapper>
  );
}

export default HomePage;
