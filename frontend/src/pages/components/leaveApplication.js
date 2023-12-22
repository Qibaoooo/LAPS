import React, { useState } from "react";
import { Badge, Button } from "react-bootstrap";
import CreateUpdateAndCancelButtons from "./leaveUpdateAndCancel";
import MyStatusBadge from "./myStatusBadge";
import LeaveDetailsModal from "./leaveDetailsModal";

function LeaveApplication({
  leaveapplication: initialLeaveApplication,
  index,
  onDelete,
}) {
  const [leaveapplication, setLeaveApplication] = useState(
    initialLeaveApplication
  );

  const [showDetails, setShowDetails] = useState(false);

  const handleCancel = () => {
    // Update the status of the leave application
    setLeaveApplication({ ...leaveapplication, status: "Cancelled" });
  };

  if (leaveapplication.status != "DELETED") {
    return (
      <tr key={index}>
        <td>
          <a
            href=""
            onClick={(e) => {
              e.preventDefault();
              setShowDetails(true)
            }}
          >
            {index}
          </a>
        </td>
        <td>{leaveapplication.fromDate}</td>
        <td>{leaveapplication.toDate}</td>
        <td>{leaveapplication.type}</td>
        <td>{leaveapplication.description}</td>
        <td>
          <MyStatusBadge status={leaveapplication.status} />
        </td>
        <CreateUpdateAndCancelButtons
          leaveapplication={leaveapplication}
          onCancel={handleCancel}
          onDelete={onDelete}
        />
        <LeaveDetailsModal
          show={showDetails}
          leave={leaveapplication}
          handleClose={() => {
            setShowDetails(false);
          }}
        ></LeaveDetailsModal>
      </tr>
    );
  }
}

export default LeaveApplication;
