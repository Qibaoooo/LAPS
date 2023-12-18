import React from "react";
import { Badge } from "react-bootstrap";
function MyStatusBadge({ status }) {
  return (
    <Badge
      pill
      bg={(() => {
        switch (status.toString().toLowerCase()) {
          case "approved":
            return "success";
          case "applied":
            return "warning";
          case "updated":
            return "warning";
          case "rejected":
            return "danger";
          case "cancelled":
            return "info-subtle";
          default:
            return "";
        }
      })()}
    >
      {status}
    </Badge>
  );
}

export default MyStatusBadge;
