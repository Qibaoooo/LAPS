import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getLeaveList } from "./utils/api/apiStaff";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import MyStatusBadge from "./components/myStatusBadge";

function StaffLeaveList() {
  const [leaveList, setLeaveList] = useState([]);

  useEffect(() => {
    if (getUserinfoFromLocal()) {
      getLeaveList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setLeaveList(list);
        });
    }
  }, []);

  return (
    <LoginCheckWrapper>
      <MyNavBar />
      <PageTitle title="Staff Leave Application List"></PageTitle>
      <MyTable>
        <thead>
          <tr>
            <th>#</th>
            <th>From Date</th>
            <th>To Date</th>
            <th>Type</th>
            <th>Description</th>
            <th>Status</th>
            <th>Update</th>
            <th>Cancel</th>
          </tr>
        </thead>
        <tbody>
          {leaveList.map((value, index, array) => {
            return (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{value.fromDate}</td>
                <td>{value.toDate}</td>
                <td>{value.type}</td>
                <td>{value.description}</td>
                <td>
                  <MyStatusBadge status={value.status}></MyStatusBadge>
                </td>
                <td>
                  <Button variant="secondary" size="sm">
                    Update
                  </Button>
                </td>
                <td>
                  <Button variant="danger" size="sm">
                    Cancel
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

export default StaffLeaveList;
