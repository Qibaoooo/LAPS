import React, { useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { getClaimList, deleteClaim, editClaim } from "./utils/api/apiStaff";
import { getUserinfoFromLocal } from "./utils/userinfo";
import MyTable from "./components/myTable";
import { Button } from "react-bootstrap";
import MyStatusBadge from "./components/myStatusBadge";
import RedirectionModal from "./components/redirectionModal";
import { sortByOvertimeDate } from "./utils/sorting";

function StaffClaimList() {
  const [claimList, setClaimList] = useState([]);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [chosenClaim, setChosenClaim] = useState({});

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getClaimList()
        .then((response) => response.data)
        .then((list) => {
          list.sort(sortByOvertimeDate);
          setClaimList(list);
        });
    }
  };


  return (
    <LoginCheckWrapper
      allowRole={["ROLE_manager", "ROLE_staff"]}
      runAfterCheck={loadData}
    >
      <MyNavBar></MyNavBar>
      <PageTitle title={"Compensation Claim List"}></PageTitle>
      <MyTable >
        <thead>
          <tr>
            <th>NO.</th>
            <th>date</th>
            <th>time</th>
            <th>description</th>
            <th>status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody style={{ textAlign: "center", verticalAlign: "middle" }}>
          {claimList.map((claim, index) => {
            return (
              <tr key={index}>
                <td>{claim.id}</td>
                <td>{claim.overtimeDate}</td>
                <td>{claim.overtimeTime}</td>
                <td>{claim.description}</td>
                <td>
                  <MyStatusBadge status={claim.status}></MyStatusBadge>
                </td>
                <td>
                  {["APPROVED"].includes(claim.status) && (
                    <Button
                      variant="secondary"
                      size="sm"
                      onClick={(e) => {
                        e.preventDefault();
                        setChosenClaim(claim);
                        setShowCancelModal(true);
                      }}
                    >
                      Cancel
                    </Button>
                  )}
                  {["APPLIED", "UPDATED"].includes(claim.status) && (
                      <Button
                        style={{marginRight:"10px"}}
                        variant="secondary"
                        size="sm"
                        onClick={() => {
                          window.location.href =
                            "/staff/claim/edit/?id=" + claim.id;
                        }}
                      >
                        Update
                      </Button>
                  )}
                  {["APPLIED", "UPDATED"].includes(claim.status) && (
                    <Button
                      variant="danger"
                      size="sm"
                      onClick={(e) => {
                        e.preventDefault();
                        setChosenClaim(claim);
                        setShowDeleteModal(true);
                      }}
                    >
                      Delete
                    </Button>
                  )}
                </td>
              </tr>
            );
          })}
        </tbody>
      </MyTable>
      <RedirectionModal
        show={showDeleteModal}
        handleButtonClick={() => {
          deleteClaim(chosenClaim).then((r) => {
            window.location.reload();
          });
        }}
        headerMsg={"Confirm delete claim " + chosenClaim.id + " ?"}
        buttonMsg={"DELETE"}
        enableCloseButton={true}
        handleClose={() => {
          setShowDeleteModal(false);
          setChosenClaim({});
        }}
      ></RedirectionModal>
      <RedirectionModal
        show={showCancelModal}
        handleButtonClick={() => {
          chosenClaim.status = "CANCELLED";
          editClaim(chosenClaim).then((r) => {
            window.location.reload();
          });
        }}
        headerMsg={"Confirm cancel claim " + chosenClaim.id + " ?"}
        buttonMsg={"CONFIRM"}
        enableCloseButton={true}
        handleClose={() => {
          setShowCancelModal(false);
          setChosenClaim({});
        }}
      ></RedirectionModal>
    </LoginCheckWrapper>
  );
}

export default StaffClaimList;
