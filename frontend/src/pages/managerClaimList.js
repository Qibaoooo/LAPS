import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getClaimList, approveClaim, rejectClaim } from "./utils/api/apiManager";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Badge, Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import ConfirmClaimModal from "./components/confirmClaimModal";

function ManagerClaimList() {
  const [claimList, setClaimList] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedClaim, setSelectedClaim] = useState({});
  const [selectedAction, setSelectedAction] = useState("");

  useEffect(() => {
    if (getUserinfoFromLocal()) {
      getClaimList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setClaimList(list);
        });
    }
  }, []);

  const handleUpdate = () => {
    console.log("handleUpdate");
    if (selectedAction === "APPROVE") {
      approveClaim(selectedClaim.id).then((resp) => {});
    } else {
      rejectClaim(selectedClaim.id).then((resp) => {});
    }
    window.location.reload();
  };

  const handleClose = () => {
    setShowModal(false);
  };

  const namelist = claimList.map(
    (userLeaveArray) =>
      userLeaveArray[0].username.charAt(0).toUpperCase() +
      userLeaveArray[0].username.slice(1)
  );

  return (
    <LoginCheckWrapper>
      <MyNavBar />
      <PageTitle title="Subordinates Compensastion Claim List"></PageTitle>
      {claimList.map((userClaimArray, index) => (
        <MyTable key={index}>
          <thead>
            <tr>
              <td colSpan={8}>
                <b>Compensation Claim for {namelist[index]}</b>
              </td>
            </tr>
            <tr>
              <th>ID</th>
              <th>Applied By</th>
              <th>Description</th>
              <th>Time</th>
              <th>Date</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {userClaimArray.map((value, index) => (
              <tr key={index}>
                <td>{value.id}</td>
                <td>{value.username}</td>
                <td>{value.description}</td>
                <td>{value.time}</td>
                <td>{value.date}</td>
                <td>
                  <Badge>{value.status}</Badge>
                </td>
                <td style={{ textAlign: "end" }}>
                  <Button
                    variant="primary"
                    size="sm"
                    onClick={() => {
                      setShowModal(true);
                      setSelectedClaim(value);
                      setSelectedAction("APPROVE");
                    }}
                  >
                    Approve
                  </Button>
                  <div className="m-1"></div>
                  <Button
                    variant="secondary"
                    size="sm"
                    onClick={() => {
                      setShowModal(true);
                      setSelectedClaim(value);
                      setSelectedAction("REJECT");
                    }}
                  >
                    Reject
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
          <tbody style={{ marginTop: "20px" }}></tbody>
        </MyTable>
      ))}
      <ConfirmClaimModal
        show={showModal}
        claim={selectedClaim}
        action={selectedAction}
        handleClose={handleClose}
        handleUpdate={handleUpdate}
      ></ConfirmClaimModal>
    </LoginCheckWrapper>
  );
}

export default ManagerClaimList;
