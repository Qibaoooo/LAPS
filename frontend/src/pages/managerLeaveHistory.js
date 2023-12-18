import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getLeaveHistory } from "./utils/api/apiManager";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Badge } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";

function ManagerLeaveList() {
    const [leaveList, setLeaveList] = useState([]);

    useEffect(() => {
        if (getUserinfoFromLocal()) {
            getLeaveHistory()
                .then((response) => response.data)
                .then((list) => {
                    console.log(list);
                    setLeaveList(list);
                });
        }
    }, [])
    const namelist = leaveList.map((userLeaveArray) => (
        userLeaveArray[0].username.charAt(0).toUpperCase()
        + userLeaveArray[0].username.slice(1)))
        ;

    return (
        <LoginCheckWrapper>
            <MyNavBar />
            <PageTitle title="Subordinates Leave Application List"></PageTitle>

            {leaveList.map((userLeaveArray, index) => (
                <MyTable>
                    <thead>
                        <tr><td colSpan={8} style={{ fontSize: '18px' }}><b>Leave Application for {namelist[index]}</b></td></tr>
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
                        {userLeaveArray.map((value, index) => (
                            <tr key={index}>
                                <td width="5%">{value.id}</td>
                                <td width="15%">{value.fromDate}</td>
                                <td width="15%">{value.toDate}</td>
                                <td width="15%">{value.type}</td>
                                <td width="25%">{(value.description)? value.description:"NULL"}</td>
                                <td width="25%">{(value.comment)? value.comment: "NULL"}</td>
                                <td width="5%"><Badge pill bg={(() => {
                                    switch (value.status.toString().toLowerCase()) {
                                        case 'approved':
                                            return "success";
                                        case 'applied':
                                            return "warning";
                                        case 'updated':
                                            return "warning";
                                        case 'rejected':
                                            return "danger";
                                        default:
                                            return "";
                                    }
                                })()}>
                                    {value.status}
                                </Badge></td>
                            </tr>
                        ))}
                    </tbody>
                    <div style={{ marginTop: "20px" }}></div>
                </MyTable>
            ))}

        </LoginCheckWrapper>
    );
}

export default ManagerLeaveList;
