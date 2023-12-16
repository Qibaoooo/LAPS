import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getLeaveList } from "./utils/api/apiManager";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Badge, Button, Col, Table } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";

function ViewLeaveList() {
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
      <PageTitle title="Subordinates Leave Application List"></PageTitle>
      <MyTable>
        <thead>
          <tr>
            <th>#</th>
            <th>From Date</th>
            <th>To Date</th>
            <th>Type</th>
            <th>Description</th>
            <th>Status</th>
            <th>Approve</th>
            <th>Reject</th>
          </tr>
        </thead>
        <tbody>
            {leaveList.map((userLeaveArray, userIndex) => (
                userLeaveArray.map((value, index) => (
                <tr key={`${userIndex}-${index}`}>
                    <td>{value.id}</td>
                    <td>{value.fromDate}</td>
                    <td>{value.toDate}</td>
                    <td>{value.type}</td>
                <td>{value.description}</td>
                <td><Badge>{value.status}</Badge></td>
                <td>
                  <Button variant="secondary" size="sm">
                    Approve
                  </Button>
                </td>
                <td>
                    <Button variant="danger" size="sm">
                 Reject
                  </Button>
                </td>
                </tr>
                ))
             ))}
        </tbody>
      </MyTable>
    </LoginCheckWrapper>
  );
}

export default ViewLeaveList;
