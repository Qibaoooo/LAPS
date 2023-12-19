import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getRoleList } from "./utils/api/apiAdmin";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";

function AdminRoleList() {
  const [roleList, setRoleList] = useState([]);

  useEffect(() => {
    if (getUserinfoFromLocal()) {
        getRoleList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setRoleList(list);
        });
    }
  }, []);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getRoleList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setRoleList(list);
        });
    }
  };

  return (
    <LoginCheckWrapper allowRole={["ROLE_admin"]} runAfterCheck={loadData}>
      <MyNavBar />
      <PageTitle title={"Role List"}></PageTitle>
      <MyTable>
        <thead>
          <tr>
            <th>Role Name</th>
            <th>Role Description</th>
            <th style={{ textAlign: 'center', verticalAlign: 'middle' }}>Edit</th>
            <th style={{ textAlign: 'center', verticalAlign: 'middle' }}>Delete</th>
          </tr>
        </thead>
        <tbody>
          {roleList.map((rp, index) => {
            return (
              <tr key={index}>
                <td>{rp.name}</td>
                <td>{rp.description}</td>
                <td>
                  <Button variant="secondary" size="sm"
                  onClick={() => {
                    window.location.href =
                      "/admin/role/edit/?id=" + rp.id;
                  }}
                  >
                    Edit
                  </Button>
                </td>
                <td>
                  <Button variant="danger" size="sm"
                  onClick={() => {
                    window.location.href =
                      "/admin/delete/?id=" + rp.id;
                  }}
                  >
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

export default AdminRoleList;
