import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Badge } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import { getClaimHistory } from "./utils/api/apiManager";

function ManagerClaimHistory() {
  const [claimList, setClaimList] = useState([]);

  useEffect(() => {
    if (getUserinfoFromLocal()) {
      getClaimHistory()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setClaimList(list);
        });
    }
  }, []);
  const namelist = claimList.map(
    (userClaimArray) =>
      userClaimArray[0].username.charAt(0).toUpperCase() +
      userClaimArray[0].username.slice(1)
  );
  return (
    <LoginCheckWrapper>
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
                  <Badge>{claim.status}</Badge>
                </td>
                <td>{claim.comment ? claim.comment: `-` }</td>
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
