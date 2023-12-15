import React from "react";
import { Table, Col } from "react-bootstrap";

function MyTable({ children }) {
  return (
    <Col className="col-md-10 mx-auto" style={{}}>
      <div style={{ borderRadius: "20px", overflow: "hidden" }}>
        <Table className="opacity-75" style={{ marginBottom: "0px" }}>
            {children}
        </Table>
      </div>
    </Col>
  );
}

export default MyTable;
