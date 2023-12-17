import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getLeaveList } from "./utils/api/apiManager";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Badge, Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";

function ManagerLeaveList() {
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
    }, [])
    const namelist = leaveList.map((userLeaveArray) => (
                userLeaveArray[0].username.charAt(0).toUpperCase() 
                + userLeaveArray[0].username.slice(1)))
    ;

    return (
        <LoginCheckWrapper>
            <MyNavBar />
            <PageTitle title="Subordinates Leave Application List"></PageTitle>
             
            {leaveList.map((userLeaveArray,index) => (
                <MyTable>  
                                   
                    <thead>
                        <tr><td colSpan={8}><b>Leave Application for {namelist[index]}</b></td></tr>
                        <tr>
                            <th>ID</th>
                            <th>From Date</th>
                            <th>To Date</th>
                            <th>Type</th>
                            <th>Description</th>
                            <th>Status</th>
                            <th>Process</th>
                        </tr>
                    </thead>
                    <tbody>
                        {userLeaveArray.map((value, index) => (
                            <tr key={index}>
                                <td width="10%">{value.id}</td>
                                <td width="15%">{value.fromDate}</td>
                                <td width="15%">{value.toDate}</td>
                                <td width="20%">{value.type}</td>
                                <td width="20%">{value.description}</td>
                                <td width="10%"><Badge>{value.status}</Badge></td>
                                <td width="10%"><Button variant="secondary" size="sm">Details</Button></td>
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
