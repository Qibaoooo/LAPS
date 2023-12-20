import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getEmployeeList, deleteEmployee } from "./utils/api/apiAdmin";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Badge, Button, Col, Table } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import RedirectionModal from "./components/redirectionModal";

function AdminEmployeeList() {
  let userinfo;

  const [employeeList, setEmployeeList] = useState([]);
  const [chosenEmployee, setChosenEmployee] = useState([]);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [user, setUser] = useState([]);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getEmployeeList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setEmployeeList(list);
        });
      userinfo = getUserinfoFromLocal();
      setUser(userinfo);
    }
  };

  return (
    <LoginCheckWrapper allowRole={["ROLE_admin"]} runAfterCheck={loadData}>
      <MyNavBar />
      <PageTitle title="Employee List"></PageTitle>
      <MyTable>
        <thead>
          <tr>
            <th width="5%">User Id</th>
            <th width="5%">User Name</th>
            <th width="5%">Manager Id</th>
            <th width="10%" style={{ textAlign: "center", verticalAlign: "middle" }}>
              Role
            </th>
            <th width="15%" style={{ textAlign: "center", verticalAlign: "middle" }}>
              Type
            </th>
            <th width="15%">Annual Leave Entitlement</th>
            <th width="15%">Medical Leave Entitlement</th>
            <th width="20%">Compensation Leave Entitlement</th>
            <th width="10%" style={{ textAlign: "center", verticalAlign: "middle" }}>
              Edit
            </th>
            <th width="10%" style={{ textAlign: "center", verticalAlign: "middle" }}>
              Delete
            </th>
          </tr>
        </thead>
        <tbody>
          {employeeList.map((value, index, array) => {
            return (
              <tr key={index}>
                <td>{value.id}</td>
                <td>{value.name}</td>
                <td>{value.managerId}</td>
                <td>{value.role}</td>
                <td>{(value.type) ? value.type : (<i>NULL</i>)}</td>
                <td>{value.annualLeaveEntitlement}</td>
                <td>{value.medicalLeaveEntitlement}</td>
                <td>{value.compensationLeaveEntitlement}</td>
                <td>
                  <Button variant="warning" size="sm" style={{ width: '100%' }}
                    onClick={() => {
                      window.location.href =
                        "/admin/employee/edit/?id=" + value.id;
                    }}
                  >
                    Edit
                  </Button>
                </td>
                <td>
                  {(user.username != value.name)&&(
                  <Button variant="danger" size="sm" style={{ width: '100%' }}
                    onClick={(e) => {
                    e.preventDefault();
                    setChosenEmployee(value);
                    setShowDeleteModal(true);
                  }}>
                    Delete
                  </Button>)}
                </td>
              </tr>
            );
          })}
        </tbody>
      </MyTable>
      <RedirectionModal
        show={showDeleteModal}
        handleButtonClick={() => {
          deleteEmployee(chosenEmployee.name).then((r) => {
            window.location.reload();
          });
        }}
        headerMsg={"Confirm delete employee " + chosenEmployee.Id + " ?"}
        buttonMsg={"DELETE"}
        enableCloseButton={true}
        handleClose={() => {
          setShowDeleteModal(false);
          setChosenEmployee({});
        }}
      ></RedirectionModal>
    </LoginCheckWrapper>
  );
}

export default AdminEmployeeList;
