import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { getClaimList, deleteClaim } from "./utils/api/apiStaff";
import { getUserinfoFromLocal } from "./utils/userinfo";
import MyTable from "./components/myTable";
import { Button } from "react-bootstrap";
import MyStatusBadge from "./components/myStatusBadge";
import RedirectionModal from "./components/redirectionModal";

function StaffClaimList() {
  const [claimList, setClaimList] = useState([]);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [chosenClaim, setChosenClaim] = useState({});

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getClaimList()
        .then((response) => response.data)
        .then((list) => {
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
      <MyTable>
        <thead>
          <tr>
            <th>id</th>
            <th>date</th>
            <th>time</th>
            <th>description</th>
            <th>status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {claimList.map((claim, index) => {
            return (
              <tr key={index}>
                <td>{claim.id}</td>
                <td>{claim.date}</td>
                <td>{claim.time}</td>
                <td>{claim.description}</td>
                <td>
                  <MyStatusBadge status={claim.status}></MyStatusBadge>
                </td>
                <td>
                  <Button
                    variant="secondary"
                    size="sm"
                    onClick={() => {
                      window.location.href =
                        "/staff/claim/edit/?id=" + claim.id;
                    }}
                  >
                    Update
                  </Button>
                  {["APPLIED", "UPDATED"].includes(claim.status) && (
                    <a
                      href=""
                      style={{ marginLeft: "10px" }}
                      onClick={(e) => {
                        e.preventDefault();
                        setChosenClaim(claim);
                        setShowDeleteModal(true);
                      }}
                    >
                      delete
                    </a>
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
          deleteClaim(chosenClaim);
          window.location.reload();
        }}
        headerMsg={"Confirm delete claim " + chosenClaim.id + " ?"}
        buttonMsg={"DELETE"}
        enableCloseButton={true}
        handleClose={() => {
          setShowDeleteModal(false);
        }}
      ></RedirectionModal>
    </LoginCheckWrapper>
  );
}

export default StaffClaimList;
