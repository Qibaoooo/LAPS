import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import { getClaimList } from "./utils/api/apiStaff";
import { getUserinfoFromLocal } from "./utils/userinfo";
import MyTable from "./components/myTable";
import { Button } from "react-bootstrap";

function StaffClaimList() {
  const [claimList, setClaimList] = useState([]);

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
            <th>Update</th>
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
                <td>{claim.status}</td>
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
                </td>
              </tr>
            );
          })}
        </tbody>
      </MyTable>
    </LoginCheckWrapper>
  );
}

export default StaffClaimList;
