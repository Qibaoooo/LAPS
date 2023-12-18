import React from 'react';
import { Badge, Button} from "react-bootstrap";
import CreateUpdateAndCancelButtons from './leaveUpdateAndCancel';


function LeaveApplication({ leaveapplication, index }) {

  return (
    <tr key={index}>
      <td>{index + 1}</td>
      <td>{leaveapplication.fromDate}</td>
      <td>{leaveapplication.toDate}</td>
      <td>{leaveapplication.type}</td>
      <td>{leaveapplication.description}</td>
      {console.log(typeof leaveapplication.id)}
      <CreateUpdateAndCancelButtons leaveapplication = {leaveapplication}/>
    </tr>
  );
}

export default LeaveApplication;