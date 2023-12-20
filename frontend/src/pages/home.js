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
                        <a href="" onClick={handleClickToggle}>
                          toggle
                        </a>
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
