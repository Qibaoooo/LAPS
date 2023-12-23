import React, { useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getRoleList, deleteRole } from "./utils/api/apiAdmin";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import RedirectionModal from "./components/redirectionModal";
import MyAlert from "./components/myAlert";

function AdminRoleList() {
  let userinfo;

  const [roleList, setRoleList] = useState([]);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [user, setUser] = useState([]);
  const [chosenRole, setChosenRole] = useState([]);
  const [deleteRespMsg, setDeleteRespMsg] = useState("");
  const [showDeleteNsg, setShowDeleteMsg] = useState(false);

  /*useEffect(() => {
    
  }, []);*/

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getRoleList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setRoleList(list);
        });
        userinfo = getUserinfoFromLocal();
        setUser(userinfo);
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
                    // console.log(rp)
                    window.location.href =
                      "/admin/role/edit/?id=" + rp.id;
                  }}
                  >
                    Edit
                  </Button>
                </td>
                <td>
                  {(user.rolename != rp.name)&&(
                  <Button variant="danger" size="sm"
                    onClick={(e) => {
                    e.preventDefault();
                    setChosenRole(rp);
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
          deleteRole(chosenRole.id).then((resp) => {
            window.location.reload();
          }).catch((e)=>{
            // console.log(e.response.data)
            setDeleteRespMsg(e.response.data)
            setShowDeleteMsg(true)
            setShowDeleteModal(false)
          });
        }}
        headerMsg={"Confirm delete role " + chosenRole.name + " ?"}
        buttonMsg={"DELETE"}
        enableCloseButton={true}
        handleClose={() => {
          setShowDeleteModal(false);
          setChosenRole({});
        }}
      ></RedirectionModal>
      <MyAlert
      variant="warning"
      msg1="Delete failed"
      msg2={deleteRespMsg}
      showAlert={showDeleteNsg}
      handleCLose={()=>{setShowDeleteMsg(false)}}
      showReturnTo={false}
      ></MyAlert>
    </LoginCheckWrapper>
  );
}

export default AdminRoleList;
