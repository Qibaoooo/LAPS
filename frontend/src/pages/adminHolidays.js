import React, { useState } from "react";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import MyNavBar from "./components/myNavBar";
import PageTitle from "./components/pageTitle";
import { getHolidays, deleteHoliday, addHoliday } from "./utils/api/apiAdmin";
import { Col, Table } from "react-bootstrap";
import Form from "react-bootstrap/Form";
import { sortHolidays } from "./utils/sorting";
import MyAlert from "./components/myAlert";

function AdminHolidays() {
  const [holidays, setHolidays] = useState([]);
  const [newDay, setNewDay] = useState();
  const [desc, setDesc] = useState("");
  const [showAlert, setShowAlert] = useState(false);

  const loadData = () => {
    getHolidays().then((resp) => {
      let hlist = Object.entries(resp.data);
      hlist.sort(sortHolidays);
      setHolidays(hlist);
      console.log(hlist);
    });
  };
  return (
    <LoginCheckWrapper allowRole={["ROLE_admin"]} runAfterCheck={loadData}>
      <MyNavBar></MyNavBar>
      <PageTitle title="Holidays of Current Year"></PageTitle>
      <Col md={6} className="m-auto">
        <Table>
          <thead>
            <tr>
              <td>Date</td>
              <td>Description</td>
              <td>+/-</td>
            </tr>
          </thead>
          <tbody>
            {holidays.map((value, index) => {
              return (
                <tr key={index}>
                  <td>{value[0]}</td>
                  <td>{value[1]}</td>
                  <td>
                    <a
                      href=""
                      onClick={(e) => {
                        e.preventDefault();
                        deleteHoliday(value[0]).then(() => {
                          window.location.reload();
                        });
                      }}
                    >
                      remove
                    </a>
                  </td>
                </tr>
              );
            })}
            <tr>
              <td>
                <Form.Control
                  type="date"
                  id="holidayDate"
                  onInput={({ target: { value } }) => {
                    setNewDay(value);
                  }}
                />
              </td>
              <td>
                <Form.Control
                  type="text"
                  id="holidayDesc"
                  onInput={({ target: { value } }) => {
                    setDesc(value);
                  }}
                />
              </td>
              <td>
                <a
                  href=""
                  onClick={(e) => {
                    e.preventDefault();
                    console.log(desc);
                    if (newDay && desc !== "") {
                      addHoliday(newDay,desc).then(() => {
                        window.location.reload();
                      });
                      setShowAlert(false);
                    } else {
                      setShowAlert(true);
                    }
                  }}
                >
                  add
                </a>{" "}
              </td>
            </tr>
          </tbody>
        </Table>
        <MyAlert
          variant={"warning"}
          msg1={"Please fill date and description."}
          msg2={""}
          showAlert={showAlert}
          showReturnTo={false}
        ></MyAlert>
        <br></br>
      </Col>
    </LoginCheckWrapper>
  );
}

export default AdminHolidays;
