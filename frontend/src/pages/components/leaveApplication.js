import React, { useState } from 'react';
import { Badge, Button} from "react-bootstrap";
import CreateUpdateAndCancelButtons from './leaveUpdateAndCancel';
import MyStatusBadge from "./myStatusBadge";

function LeaveApplication({ leaveapplication: initialLeaveApplication, index, onDelete }) {
  const [leaveapplication, setLeaveApplication] = useState(initialLeaveApplication);

  const handleCancel = () => {
    // Update the status of the leave application
    setLeaveApplication({ ...leaveapplication, status: "Cancelled" });
  };

  if(leaveapplication.status != "DELETED"){
  return (
    <tr key={index}>
      <td>{index + 1}</td>
      <td>{leaveapplication.fromDate}</td>
      <td>{leaveapplication.toDate}</td>
      <td>{leaveapplication.type}</td>
      <td>{leaveapplication.description}</td>
      <td><MyStatusBadge status = {leaveapplication.status}/></td>
      <CreateUpdateAndCancelButtons leaveapplication = {leaveapplication} onCancel = {handleCancel} onDelete={onDelete}/>
    </tr>
  );
  }
}

export default LeaveApplication;