import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import { getClaimHistory } from "./utils/api/apiManager";
import MyStatusBadge from "./components/myStatusBadge";

function ManagerClaimHistory() {
  const [claimList, setClaimList] = useState([]);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getClaimHistory()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setClaimList(list);
        });
    }
  };
  const namelist = claimList.map(
    (userClaimArray) =>
      userClaimArray[0].username.charAt(0).toUpperCase() +
      userClaimArray[0].username.slice(1)
  );
  return (
    <LoginCheckWrapper allowRole={["ROLE_manager"]} runAfterCheck={loadData}>
      <MyNavBar />
      <PageTitle title="Subordinates OT Claim History"></PageTitle>
      {claimList.map((userLeaveArray, index) => (
        <MyTable>
          <thead>
            <tr>
              <td colSpan={8}>
                <b>OT Claim for {namelist[index]}</b>
              </td>
            </tr>
            <tr>
              <th>ID</th>
              <th>Date</th>
              <th>Time</th>
              <th>Description</th>
              <th>Status</th>
              <th>Comment</th>
            </tr>
          </thead>
          <tbody>
            {userLeaveArray.map((claim, index) => (
              <tr key={index}>
                <td>{claim.id}</td>
                <td>{claim.date}</td>
                <td>{claim.time}</td>
                <td>{claim.description}</td>
                <td>
                  <MyStatusBadge status={claim.status}></MyStatusBadge>
                </td>
                <td>{claim.comment ? claim.comment : `-`}</td>
              </tr>
            ))}
          </tbody>
          <div style={{ marginTop: "20px" }}></div>
        </MyTable>
      ))}
    </LoginCheckWrapper>
  );
}

export default ManagerClaimHistory;
