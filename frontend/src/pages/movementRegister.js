import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getLeaveList } from "./utils/api/apiStaff";
import { getUserinfoFromLocal } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import { Button } from "react-bootstrap";
import PageTitle from "./components/pageTitle";
import MyTable from "./components/myTable";
import getMovementRegister from "./utils/api/apiCommon";
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';

function CommonLeaveList() {
    const [leaveList, setLeaveList] = useState([]);
    const [view,setView] = useState("Calendar");

    const currentDate = new Date();
    const [month, setMonth] = useState(currentDate.getMonth() + 1);
    const [year, setYear] = useState(currentDate.getFullYear());
    
    useEffect(() => {
        console.log("!!!CommonLeaveList useEffect RUN")
        if (month && year) {
          loadData(year, month);
        }
      }, [month, year]);

    const loadData = (year, month) => {
    if (getUserinfoFromLocal()) {
        getMovementRegister(year, month)
        .then((response) => response.data)
        .then((list) => {
            setLeaveList(list);
        });
    }
    };

    const handleDatesSet = (dateInfo) => {
        const activeStart = new Date(dateInfo.view.currentStart); 
        const activeMonth = activeStart.getMonth() + 1; // Get the month from the current start of the view
        const activeYear = activeStart.getFullYear(); // Get the year from the current start of the view
    
        console.log("Active Start:", activeStart);
        console.log("Viewing month:", activeMonth, "Year:", activeYear);
        setMonth(activeMonth); // Set the active month
        setYear(activeYear); // Set the active year
    };
  

    return (
    <LoginCheckWrapper
        allowRole={["ROLE_manager", "ROLE_staff"]}
        runAfterCheck={()=>{}}
    >
        <MyNavBar />
        <PageTitle title="Staff Leave Application List"></PageTitle>
        <Button onClick={() => setView(view === "List" ? "Calendar" : "List")}>Toggle View</Button>
        {view === "List" ? 
        (<MyTable>
        <thead>
            <tr>
            <th>NO.</th>
            <th>Name</th>
            <th>From Date</th>
            <th>To Date</th>
            <th>Type</th>
            <th>Description</th>
            </tr>
        </thead>
        <tbody>
            {leaveList.map((value, index, array) => (
            <tr key={index}>
                <td>{index + 1}</td>
                <td>{value.name}</td>
                <td>{value.fromDate}</td>
                <td>{value.toDate}</td>
                <td>{value.type}</td>
                <td>{value.description}</td>
            </tr>
            ))}
        </tbody>
        </MyTable>) :
        (<FullCalendar
            plugins={[dayGridPlugin]}
            initialView="dayGridMonth"
            height={600}
            aspectRatio={1}
            datesSet={handleDatesSet}
            initialDate={currentDate}
            fixedWeekCount={4}
            />)
        }
        
    </LoginCheckWrapper>
  );
}

export default CommonLeaveList;
