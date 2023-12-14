import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import Cookies from "js-cookie";

function StaffLeaveList() {
  const [leaveList, setLeaveList] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/leave/list", {
      method: "GET",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Origin": "http://localhost:3000",
        "Access-Control-Allow-Methods": "GET,POST,PATCH,OPTIONS",
      },
      referrerPolicy: "no-referrer",
    })
      .then((response) => response.json())
      .then((list) => {
        console.log(list);
        setLeaveList(list);
      });
  }, []);

  return (
    <div>
      <MyNavBar></MyNavBar>
      <p>StaffLeaveList</p>
      {leaveList.map((value, index, array)=>{
            return <h5 className="p-1" key={index}>{JSON.stringify(value)}</h5>
      })}
    </div>
  );
}

export default StaffLeaveList;
