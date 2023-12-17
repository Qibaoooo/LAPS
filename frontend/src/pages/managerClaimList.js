import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getClaimList } from "./utils/api/apiManager";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Badge, Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";

function ManagerClaimList() {
    const [claimList, setClaimList] = useState([]);

    useEffect(() => {
        if (getUserinfoFromLocal()) {
            getClaimList()
                .then((response) => response.data)
                .then((list) => {
                    console.log(list);
                    setClaimList(list);
                });
        }
    }, []);

    const namelist = claimList.map((userLeaveArray) => (
        userLeaveArray[0].username.charAt(0).toUpperCase() 
        + userLeaveArray[0].username.slice(1)))

    return (
        <LoginCheckWrapper>
            <MyNavBar />
            <PageTitle title="Subordinates Compensastion Claim List"></PageTitle>
            {claimList.map((userClaimArray, index) => (
                <MyTable>
                    <thead>
                        <tr><td colSpan={8}><b>Compensation Claim for {namelist[index]}</b></td></tr>
                        <tr>
                            <th>ID</th>
                            <th>Applied By</th>
                            <th>Description</th>
                            <th>Time</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        {userClaimArray.map((value, index) => (
                            <tr key={index}>
                                <td>{value.id}</td>
                                <td>{value.username}</td>
                                <td>{value.description}</td>
                                <td>{value.time}</td>
                                <td>{value.date}</td>
                                <td>
                                    <Badge>{value.status}</Badge>
                                </td>
                                <td>
                                    <Button variant="secondary" size="sm">
                                        Details
                                    </Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                    <div style={{ marginTop: "20px" }}></div>
                </MyTable>
            ))}
        </LoginCheckWrapper>
    );
}

export default ManagerClaimList;
