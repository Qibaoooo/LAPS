import React, { useEffect, useState } from "react";
import MyNavBar from "./components/myNavBar";
import { getLeaveList } from "./utils/api/apiStaff";
import { getUserinfo } from "./utils/userinfo";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import {
  Card,
  Badge,
  Button,
  Col,
  Container,
  Stack,
  Table,
} from "react-bootstrap";

function StaffLeaveList() {
  const [leaveList, setLeaveList] = useState([]);

  useEffect(() => {
    if (getUserinfo()) {
      getLeaveList()
        .then((response) => response.data)
        .then((list) => {
          console.log(list);
          setLeaveList(list);
        });
    }
  }, []);

  return (
    <LoginCheckWrapper>
      <MyNavBar />
      <h3 className="mt-3" style={{ fontStyle: "oblique" }}>
        Staff Leave Application List
      </h3>
      <Col className="col-md-10 mx-auto" style={{  }}>
        <Table>
          <thead>
            <tr>
              <th>#</th>
              <th>From Date</th>
              <th>To Date</th>
              <th>Type</th>
              <th>Description</th>
              <th>Status</th>
              <th>Update</th>
              <th>Cancel</th>
            </tr>
          </thead>
          <tbody>
            {leaveList.map((value, index, array) => {
              return (
                <tr>
                  <td>{index + 1}</td>
                  <td>{value.fromDate}</td>
                  <td>{value.toDate}</td>
                  <td>{value.type}</td>
                  <td>{value.description}</td>
                  <td>
                    <Badge>{value.status}</Badge>
                  </td>
                  <td>
                    <Button variant="secondary" size="sm">Update</Button>
                  </td>
                  <td>
                    <Button variant="danger" size="sm">Cancel</Button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </Table>
      </Col>
    </LoginCheckWrapper>
  );
}

export default StaffLeaveList;
