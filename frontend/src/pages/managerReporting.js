import React, { useEffect, useState } from "react";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import MyNavBar from "./components/myNavBar";
import PageTitle from "./components/pageTitle";
import { Button, Col, Form, Row, Tab, Tabs } from "react-bootstrap";
import MyAlert from "./components/myAlert";
import { getLeaveReport, getClaimHistory } from "./utils/api/apiManager";
import CsvDownloadButton from "react-json-to-csv";
import { getUserinfoFromLocal } from "./utils/userinfo";
import { sortByOvertimeDate } from "./utils/sorting";

function ManagerReporting() {
  // leave csv states
  const [validated, setValidated] = useState(false);
  const [fromDate, setFromDate] = useState();
  const [toDate, setToDate] = useState();
  const [leaveType, setLeaveType] = useState("MedicalLeave");
  const [reportData, setReportData] = useState();
  const [reportName, setReportName] = useState();
  const [showLeaveFormAlert, setShowLeaveFormAlert] = useState(false);
  const [alertMsg1, setAlertMsg1] = useState(false);
  const [alertMsg2, setAlertMsg2] = useState(false);

  // claim csv states
  const [validatedClaim, setValidatedClaim] = useState(false);
  const [showClaimFormAlert, setShowClaimFormAlert] = useState(false);
  const [alertMsg1Claim, setAlertMsg1Claim] = useState(false);
  const [alertMsg2Claim, setAlertMsg2Claim] = useState(false);
  const [claimList, setClaimList] = useState([]);
  const [selectedEmp, setSelectedEmp] = useState("");
  const [reportDataClaim, setReportDataClaim] = useState();
  const [reportNameClaim, setReportNameClaim] = useState();
  const onInputEMP = ({ target: { value } }) => setSelectedEmp(value);

  const loadData = () => {
    if (getUserinfoFromLocal()) {
      getClaimHistory()
        .then((response) => response.data)
        .then((list) => {
          list.map((userClaimArray, index) => {
            userClaimArray.sort(sortByOvertimeDate);
          });
          setClaimList(list);
          if (list.length == 0) {
            setAlertMsg1Claim("No claims found.");
            setAlertMsg2Claim(
              "Either you have no subordinates, or they have no compensation claims."
            );
            setShowClaimFormAlert(true);
          } else {
            setShowClaimFormAlert(false);
          }
        });
    }
  };

  useEffect(() => {
    if (reportData) {
      document.getElementById("downloadLeaveCSV").click();
    }
  }, [reportData]);

  useEffect(() => {
    if (reportDataClaim) {
      document.getElementById("downloadClaimCSV").click();
    }
  }, [reportDataClaim]);

  const onFormSubmitClaim = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.stopPropagation();
      setValidated(true);
    } else {
      if (claimList.length == 0) {
        setAlertMsg1Claim("No claims found.");
        setAlertMsg2Claim(
          "Either you have no subordinates, or they have no compensation claims."
        );
        setShowClaimFormAlert(true);
      } else {
        if (selectedEmp === "") {
          return;
        }
        setReportNameClaim(selectedEmp + "_Claims");
        if (selectedEmp === "ALL") {
          let arr = [];
          claimList.forEach((cl) => {
            arr = arr.concat(cl);
          });
          setReportDataClaim(arr);
        } else {
          let arr = [];
          claimList.forEach((cl) => {
            if (cl[0].username !== selectedEmp) {
              return;
            }
            arr = arr.concat(cl);
          });
          setReportDataClaim(arr);
        }
      }
    }
  };

  const onFormSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.stopPropagation();
      setValidated(true);
    } else {
      if (fromDate > toDate) {
        setAlertMsg1("Wrong input for dates");
        setAlertMsg2("FromDate cannot be later than ToDate");
        setShowLeaveFormAlert(true);
        return;
      }

      getLeaveReport(fromDate, toDate, leaveType)
        .then((resp) => {
          if (resp.status === 200) {
            setReportName(leaveType + "_" + fromDate + "_" + toDate);
            setReportData(resp.data);
          } else {
          }
        })
        .catch((e) => {
          setAlertMsg1("Failed to retrieve data");
          setAlertMsg2(e.response.data);
          setShowLeaveFormAlert(true);
        });

      setShowLeaveFormAlert(false);
    }
  };

  const onInputFDT = ({ target: { value } }) => setFromDate(value);
  const onInputTDT = ({ target: { value } }) => setToDate(value);
  const onInputType = ({ target: { value } }) => setLeaveType(value);

  return (
    <div>
      <LoginCheckWrapper allowRole={["ROLE_manager"]} runAfterCheck={loadData}>
        <MyNavBar />
        <PageTitle title="Generate Manager Reports"></PageTitle>
        <br></br>
        <Tabs fill defaultActiveKey="leave_tab" className="mx-5">
          <Tab eventKey="leave_tab" title="Employee Leave Report">
            <br></br>
            <h5>Get CSV for Employee on Leave During Selected Periods</h5>
            <Form noValidate validated={validated} onSubmit={onFormSubmit}>
              <Col md="5" className="mx-auto">
                <Row className="m-3" style={{ textAlign: "left" }}>
                  <Form.Group as={Col} controlId="formFromDate">
                    <Form.Label>FromDate</Form.Label>
                    <Form.Control
                      required
                      type="date"
                      placeholder="date"
                      onChange={onInputFDT}
                    />
                  </Form.Group>
                  <Form.Group as={Col} controlId="formToDate">
                    <Form.Label>ToDate</Form.Label>
                    <Form.Control
                      required
                      type="date"
                      placeholder="date"
                      onChange={onInputTDT}
                    />
                  </Form.Group>
                </Row>
                <Row className="m-3" style={{ textAlign: "left" }}>
                  <Form.Group as={Col} controlId="leaveType">
                    <Form.Label>Leave Type</Form.Label>
                    <Form.Select
                      required
                      className="form-select"
                      onChange={onInputType}
                    >
                      <option value="MedicalLeave">Medical Leave</option>
                      <option value="AnnualLeave">Annual Leave</option>
                      <option value="CompensationLeave">
                        Compensation Leave
                      </option>
                      <option value="All">All</option>
                    </Form.Select>
                  </Form.Group>
                </Row>
                <Button type="submit">Download</Button>
              </Col>
              <MyAlert
                variant="warning"
                msg1={alertMsg1}
                msg2={alertMsg2}
                showAlert={showLeaveFormAlert}
                handleCLose={() => {}}
                showReturnTo={false}
              ></MyAlert>
            </Form>
            <CsvDownloadButton
              id="downloadLeaveCSV"
              style={{ display: "none" }}
              data={reportData}
              filename={reportName}
            >
              DownloadCSV
            </CsvDownloadButton>
          </Tab>
          <Tab eventKey="claim_tab" title="Compensation Claims Report">
            <br></br>
            <h5>
              Get CSV for Compensation Claims under All/Particular Employee
            </h5>

            <Form
              noValidate
              validated={validatedClaim}
              onSubmit={onFormSubmitClaim}
            >
              <Col md="5" className="mx-auto">
                <Row className="m-3" style={{ textAlign: "left" }}>
                  <Form.Group as={Col} controlId="formEmployee">
                    <Form.Label>Employee</Form.Label>
                    <Form.Select
                      required
                      className="form-select"
                      onChange={onInputEMP}
                    >
                      <option value="" style={{ display: "none" }}>
                        Select Employee
                      </option>
                      {claimList.length > 0 &&
                        claimList.map((value, index) => {
                          return (
                            <option key={index} value={value[0].username}>
                              {value[0].username}
                            </option>
                          );
                        })}
                      <option value="ALL">ALL</option>
                    </Form.Select>
                  </Form.Group>
                </Row>
                <Button type="submit">Download</Button>
              </Col>
              <MyAlert
                variant="warning"
                msg1={alertMsg1Claim}
                msg2={alertMsg2Claim}
                showAlert={showClaimFormAlert}
                handleCLose={() => {}}
                showReturnTo={false}
              ></MyAlert>
            </Form>

            <CsvDownloadButton
              id="downloadClaimCSV"
              style={{ display: "none" }}
              data={reportDataClaim}
              filename={reportNameClaim}
            >
              DownloadCSV
            </CsvDownloadButton>
          </Tab>
        </Tabs>
      </LoginCheckWrapper>
    </div>
  );
}

export default ManagerReporting;
