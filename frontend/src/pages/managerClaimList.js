import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import {
  getClaimList,
  approveClaim,
  rejectClaim,
} from "./utils/api/apiManager";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import ConfirmClaimModal from "./components/confirmClaimModal";
import MyStatusBadge from "./components/myStatusBadge";

function ManagerClaimList() {
  const [claimList, setClaimList] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedClaim, setSelectedClaim] = useState({});
  const [selectedAction, setSelectedAction] = useState("");
  const [comment, setComment] = useState("");
  const [showCommentAlert, setShowCommentAlert] = useState(false);

  useEffect(() => {
    if (getUserinfoFromLocal()) {
      getClaimList()
        .then((response) => response.data)
        .then((list) => {
          setClaimList(list);
        });
    }
  }, []);

  const handleUpdate = () => {
    console.log("handleUpdate");
    if (selectedAction === "APPROVE") {
      approveClaim({ id: selectedClaim.id, comment: comment }).then(
        (resp) => {}
      );
    } else {
      if (comment === "") {
        setShowCommentAlert(true);
        return;
      }
      rejectClaim({ id: selectedClaim.id, comment: comment }).then(
        (resp) => {}
      );
    }
    window.location.reload();
  };

  const handleClose = () => {
    setShowModal(false);
    setComment("");
    setShowCommentAlert(false);
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
      {claimList.length === 0 && (
        <p style={{ marginTop: "100px" }}>No pending claims for approval.</p>
      )}
      {claimList.map((userClaimArray, index) => (
        <div>
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
                    <MyStatusBadge status={value.status}></MyStatusBadge>
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
          </MyTable>
          <div style={{ marginTop: "20px" }}></div>
        </div>
      ))}
      <ConfirmClaimModal
        show={showModal}
        claim={selectedClaim}
        action={selectedAction}
        handleClose={handleClose}
        handleUpdate={handleUpdate}
        comment={comment}
        showCommentAlert={showCommentAlert}
        onCommentInput={(e) => {
          setComment(e.target.value);
        }}
      ></ConfirmClaimModal>
    </LoginCheckWrapper>
  );
}

export default ManagerClaimList;
