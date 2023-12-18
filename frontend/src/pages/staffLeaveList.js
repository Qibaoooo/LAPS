import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getLeaveList } from "./utils/api/apiStaff";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import MyStatusBadge from "./components/myStatusBadge";
import LeaveApplication from "./components/leaveApplication";

function StaffLeaveList() {
  const [leaveList, setLeaveList] = useState([]);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getLeaveList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setLeaveList(list);
        });
    }
  };

  return (
    <LoginCheckWrapper
      allowRole={["ROLE_manager", "ROLE_staff"]}
      runAfterCheck={loadData}
    >
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
          {leaveList.map((value, index, array) => (
            <LeaveApplication leaveapplication = {value} index = {index} key = {index}/>
          ))}
        </tbody>
      </MyTable>
    </LoginCheckWrapper>
  );
}

export default StaffLeaveList;
