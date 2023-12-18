import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getEmployeeList } from "./utils/api/apiAdmin";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Badge, Button, Col, Table } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";

function AdminEmployeeList() {
  const [employeeList, setEmployeeList] = useState([]);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getEmployeeList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setEmployeeList(list);
        });
    }
  };

  return (
    <LoginCheckWrapper allowRole={["ROLE_admin"]} runAfterCheck={loadData}>
      <MyNavBar />
      <PageTitle title="Employee List"></PageTitle>
      <MyTable>
        <thead>
          <tr>
            <th>User Id</th>
            <th>User Name</th>
            <th>Manager Id</th>
            <th style={{ textAlign: "center", verticalAlign: "middle" }}>
              Role
            </th>
            <th>Annual Leave Entitlement</th>
            <th>Medical Leave Entitlement</th>
            <th>Compensation Leave Entitlement</th>
            <th style={{ textAlign: "center", verticalAlign: "middle" }}>
              Edit
            </th>
            <th style={{ textAlign: "center", verticalAlign: "middle" }}>
              Delete
            </th>
          </tr>
        </thead>
        <tbody>
          {employeeList.map((value, index, array) => {
            return (
              <tr key={index}>
                <td>{value.Id}</td>
                <td>{value.name}</td>
                <td>{value.managerId}</td>
                <td>{value.role}</td>
                <td>{value.annualLeaveEntitlement}</td>
                <td>{value.medicalLeaveEntitlement}</td>
                <td>{value.compensationLeaveEntitlement}</td>
                <td>
                  <Button variant="secondary" size="sm">
                    Edit
                  </Button>
                </td>
                <td>
                  <Button variant="danger" size="sm">
                    Delete
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

export default AdminEmployeeList;
