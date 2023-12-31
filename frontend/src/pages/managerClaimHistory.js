import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import { getClaimHistory } from "./utils/api/apiManager";
import MyStatusBadge from "./components/myStatusBadge";
import { sortByOvertimeDate } from "./utils/sorting";

function ManagerClaimHistory() {
  const [claimList, setClaimList] = useState([]);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getClaimHistory()
        .then((response) => response.data)
        .then((list) => {
          list.map((userClaimArray, index) => {
            userClaimArray.sort(sortByOvertimeDate);
          });
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
      {claimList.map((userClaimArray, index) => (
        <MyTable key={index} foldable={true}>
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
            {userClaimArray.map((claim, index) => (
              <tr key={index}>
                <td>{claim.id}</td>
                <td>{claim.overtimeDate}</td>
                <td>{claim.overtimeTime}</td>
                <td>{claim.description}</td>
                <td>
                  <MyStatusBadge status={claim.status}></MyStatusBadge>
                </td>
                <td>{claim.comment ? claim.comment : `-`}</td>
              </tr>
            ))}
          </tbody>
        </MyTable>
      ))}
    </LoginCheckWrapper>
  );
}

export default ManagerClaimHistory;
