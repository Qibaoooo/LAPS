import React, { useState } from "react";
import MyNavBar from "./components/myNavBar";
import {
    getLeaveList,
    approveLeave,
    rejectLeave,
    getEntitlementLeft
} from "./utils/api/apiManager";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import ConfirmLeaveModal from "./components/confirmLeaveModal";
import MyStatusBadge from "./components/myStatusBadge";

function ManagerLeaveList() {
    const [leaveList, setLeaveList] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [selectedLeave, setSelectedLeave] = useState({});
    const [selectedAction, setSelectedAction] = useState("");
    const [comment, setComment] = useState("");
    const [showCommentAlert, setShowCommentAlert] = useState(false);
    const [entitilementList, setEntitlementList] = useState([]);

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

    const getEntitlement = (id) => {
        getEntitlementLeft(id)
            .then((response) => response.data)
            .then((list) => {
                setEntitlementList(list);
            });
    };

    const handleUpdate = () => {
        console.log("handleUpdate");
        if (selectedAction === "APPROVE") {
            approveLeave({ id: selectedLeave.id, comment: comment }).then(
                (resp) => { }
            );
        } else {
            if (comment === "") {
                setShowCommentAlert(true);
                return;
            }
            rejectLeave({ id: selectedLeave.id, comment: comment }).then(
                (resp) => { }
            );
        }
        window.location.reload();
    };

    const handleClose = () => {
        setShowModal(false);
        setComment("");
        setShowCommentAlert(false);
    };

    const namelist = leaveList.map(
        (userLeaveArray) =>
            userLeaveArray[0].username.charAt(0).toUpperCase() +
            userLeaveArray[0].username.slice(1)
    );
    return (
        <LoginCheckWrapper allowRole={["ROLE_manager"]} runAfterCheck={loadData}>
            <MyNavBar />
            <PageTitle title="Subordinates Leave Application List"></PageTitle>
            {leaveList.length === 0 && (
                <p style={{ marginTop: "100px" }}>No pending leave applications for approval.</p>
            )}
            {leaveList.map((userLeaveArray, index) => (
                <MyTable>
                    <thead>
                        <tr>
                            <td colSpan={8} style={{ fontSize: '16px' }}>
                                <b>Leave Application for {namelist[index]}</b>
                            </td>
                        </tr>
                        <tr>
                            <th width="5%">NO.</th>
                            <th width="15%">From Date</th>
                            <th width="15%">To Date</th>
                            <th width="10%">Type</th>
                            <th width="25%">Description</th>
                            <th width="10%">Status</th>
                            <th width="20%">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {userLeaveArray.map((value, index) => (
                            <tr key={index}>
                                <td width="5%">{value.id}</td>
                                <td width="15%">{value.fromDate}</td>
                                <td width="15%">{value.toDate}</td>
                                <td width="10%">{value.type}</td>
                                <td width="25%">{value.description}</td>
                                <td width="10%">
                                    <MyStatusBadge status={value.status}></MyStatusBadge>
                                </td>
                                <td width="20%" style={{ textAlign: "center" }}>
                                        <Button
                                            style={{width:"35%",marginRight: "10px" }}
                                            variant="success"
                                            size="sm"
                                            onClick={() => {
                                                setShowModal(true);
                                                setSelectedLeave(value);
                                                setSelectedAction("APPROVE");
                                                getEntitlement(value.id);
                                            }}
                                        >
                                            Approve
                                        </Button>
                                    <Button
                                        style={{width:"35%"}}
                                        variant="danger"
                                        size="sm"
                                        onClick={() => {
                                            setShowModal(true);
                                            setSelectedLeave(value);
                                            setSelectedAction("REJECT");
                                            getEntitlement(value.id);
                                        }}
                                    >
                                        Reject
                                    </Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                    <div style={{ marginTop: "20px" }}></div>
                </MyTable>
            ))}
            <ConfirmLeaveModal
                show={showModal}
                leave={selectedLeave}
                action={selectedAction}
                handleClose={handleClose}
                handleUpdate={handleUpdate}
                comment={comment}
                entitlementList={entitilementList}
                showCommentAlert={showCommentAlert}
                onCommentInput={(e) => {
                    setComment(e.target.value);
                }}
            ></ConfirmLeaveModal>
        </LoginCheckWrapper>
    );
}

export default ManagerLeaveList;
