import React, { useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getLeaveHistory } from "./utils/api/apiManager";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import MyStatusBadge from "./components/myStatusBadge";
import LeaveDetailsModal from "./components/leaveDetailsModal";

function ManagerLeaveList() {
  const [leaveList, setLeaveList] = useState([]);
  const [showDetails, setShowDetails] = useState(false);
  const [detailsToBeShown, setDetailsToBeShown] = useState({});
  
  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getLeaveHistory()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setLeaveList(list);
        });
    }
  };

  const namelist = leaveList.map(
    (userLeaveArray) =>
      userLeaveArray[0].username.charAt(0).toUpperCase() +
      userLeaveArray[0].username.slice(1)
  );
  return (
    <LoginCheckWrapper allowRole={["ROLE_manager"]} runAfterCheck={loadData}>
      <MyNavBar />
      <PageTitle title="Subordinates Leave Application History"></PageTitle>

      {leaveList.map((userLeaveArray, index) => (
        <MyTable foldable={true}>
          <thead>
            <tr>
              <td colSpan={8} style={{ fontSize: "16px" }}>
                <b>Leave Application for {namelist[index]}</b>
              </td>
            </tr>
            <tr>
              <th>No.</th>
              <th>From Date</th>
              <th>To Date</th>
              <th>Type</th>
              <th>Description</th>
              <th>Comment</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {userLeaveArray.map((value, index) => {
              return (
                <tr key={index}>
                  <td width="7.5%">
                    <a
                      href=""
                      onClick={(e) => {
                        e.preventDefault();
                        setDetailsToBeShown(value)
                        setShowDetails(true);
                      }}
                    >
                      {value.id}
                    </a>
                  </td>
                  <td width="15%">{value.fromDate}</td>
                  <td width="15%">{value.toDate}</td>
                  <td width="15%">{value.type}</td>
                  {value.description ? (
                    <td>{value.description}</td>
                  ) : (
                    <td>
                      <i>-</i>
                    </td>
                  )}
                  {value.comment ? (
                    <td>{value.comment}</td>
                  ) : (
                    <td>
                      <i>-</i>
                    </td>
                  )}
                  <td width="7.5%">
                    <MyStatusBadge status={value.status}></MyStatusBadge>
                  </td>
                </tr>
              );
            })}
          </tbody>
          <LeaveDetailsModal
            show={showDetails}
            leave={detailsToBeShown}
            handleClose={() => {
              setShowDetails(false);
            }}
          ></LeaveDetailsModal>
        </MyTable>
      ))}
    </LoginCheckWrapper>
  );
}

export default ManagerLeaveList;
