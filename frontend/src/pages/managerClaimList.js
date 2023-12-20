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
import { sortByOvertimeDate } from "./utils/sorting";

function ManagerClaimList() {
  const [claimList, setClaimList] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedClaim, setSelectedClaim] = useState({});
  const [selectedAction, setSelectedAction] = useState("");
  const [comment, setComment] = useState("");
  const [showCommentAlert, setShowCommentAlert] = useState(false);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getClaimList()
        .then((response) => response.data)
        .then((list) => {
          list.map((userClaimArray, index) => {
            userClaimArray.sort(sortByOvertimeDate);
          });
          setClaimList(list);
        });
    }
  };

  const handleUpdate = () => {
    console.log("handleUpdate");
    if (selectedAction === "APPROVE") {
      approveClaim(selectedClaim, comment).then((resp) => {
        window.location.reload();
      });
    } else {
      if (comment === "") {
        setShowCommentAlert(true);
        return;
      }
      rejectClaim(selectedClaim, comment).then((resp) => {
        window.location.reload();
      });
    }
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
    <LoginCheckWrapper allowRole={["ROLE_manager"]} runAfterCheck={loadData}>
      <MyNavBar />
      <PageTitle title="Subordinates Compensastion Claim List"></PageTitle>
      {claimList.length === 0 && (
        <p style={{ marginTop: "100px" }}>No pending claims for approval.</p>
      )}
      {claimList.map((userClaimArray, index) => (
        <div>
          <MyTable key={index} foldable={true}>
            <thead >
              <tr style={{ position:"sticky", top: "0px"}}>
                <td colSpan={8} style={{ fontSize: "16px" }}>
                  <b>Compensation Claim for {namelist[index]}</b>
                </td>
              </tr>
              <tr>
                <th>NO.</th>
                <th>Applied By</th>
                <th>Description</th>
                <th>Time</th>
                <th>Date</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody style={{ textAlign: "center", verticalAlign: "middle" }}>
              {userClaimArray.map((value, index) => (
                <tr key={index}>
                  <td width="5%">{value.id}</td>
                  <td width="10%">{value.username}</td>
                  <td width="25%">{value.description}</td>
                  <td width="5%">{value.time}</td>
                  <td width="15%">{value.date}</td>
                  <td width="20%">
                    <MyStatusBadge status={value.status}></MyStatusBadge>
                  </td>
                  <td width="20%" style={{ textAlign:"center" }}>
                    <Button
                      style={{ width: '40%' , marginRight:'5px'}}
                      variant="success"
                      size="sm"
                      onClick={() => {
                        setShowModal(true);
                        setSelectedClaim(value);
                        setSelectedAction("APPROVE");
                      }}
                    >
                      Approve
                    </Button>
                    <Button
                      style={{ width: '40%' }}
                      variant="danger"
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
