import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";
import { getUserinfoFromLocal } from "../userinfo";

/*
LEAVE APPPLICATION
*/
let getLeaveList = () => {
  return axios.get("http://localhost:8080/api/manager/leave/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

let getLeaveHistory = () => {
  return axios.get("http://localhost:8080/api/manager/leave/history", {
    headers: getJsonHeadersWithJWT(),
  });
};

let approveLeave = (data) => {
  return axios.post("http://localhost:8080/api/manager/leave/approve", data, {
    headers: getJsonHeadersWithJWT(),
  });
};

let getEntitlementLeft = (id) => {
  return axios.post(
    "http://localhost:8080/api/manager/leave/checkEntitle?id=" + id,
    null,
    {
      headers: getJsonHeadersWithJWT(),
    }
  );
};

let rejectLeave = (data) => {
  return axios.post("http://localhost:8080/api/manager/leave/reject", data, {
    headers: getJsonHeadersWithJWT(),
  });
};

/*
COMPENSATION CLAIM
*/
let getClaimList = () => {
  const data = {
    pendingClaimsOnly: true,
    managerId: getUserinfoFromLocal().id,
  };
  return axios.get("http://localhost:8080/api/manager/claims", {
    params: data,
    headers: getJsonHeadersWithJWT(),
  });
};

let getClaimHistory = () => {
  const data = {
    pendingClaimsOnly: false,
    managerId: getUserinfoFromLocal().id,
  };
  return axios.get("http://localhost:8080/api/manager/claims", {
    params: data,
    headers: getJsonHeadersWithJWT(),
  });
};

let approveClaim = (claim, comment) => {
  let data = buildClaimData(claim, comment, "APPROVED");
  return axios.put("http://localhost:8080/api/manager/claims", data, {
    headers: getJsonHeadersWithJWT(),
  });
};

let rejectClaim = (claim, comment) => {
  let data = buildClaimData(claim, comment, "REJECTED");
  return axios.put("http://localhost:8080/api/manager/claims", data, {
    headers: getJsonHeadersWithJWT(),
  });
};

let buildClaimData = (claim, comment, status) => {
  return {
    description: claim.description,
    overtimeTime: claim.overtimeTime,
    overtimeDate: claim.overtimeDate,
    id: claim.id,
    userid: claim.userid,
    comment: comment,
    status: status,
  };
};

/**
 * Reporting
 */
let getLeaveReport = (fromDate, toDate, leaveType) => {
  const data = {
    fromDate: fromDate,
    toDate: toDate,
    leaveType: leaveType,
  };
  return axios.get("http://localhost:8080/api/manager/report",{
    params: data,
    headers: getJsonHeadersWithJWT(),
  });
};

export {
  getLeaveList,
  getLeaveHistory,
  getClaimList,
  approveClaim,
  rejectClaim,
  getClaimHistory,
  approveLeave,
  rejectLeave,
  getEntitlementLeft,
  getLeaveReport,
};
