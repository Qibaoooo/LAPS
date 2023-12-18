import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

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
  return axios.post("http://localhost:8080/api/manager/leave/checkEntitle?id="+id,null,{
    headers: getJsonHeadersWithJWT(),
  });
};

let rejectLeave = (data) => {
  return axios.post("http://localhost:8080/api/manager/leave/reject", data, {
    headers: getJsonHeadersWithJWT(),
  });
};

let getClaimList = () => {
  return axios.get("http://localhost:8080/api/manager/claim/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

let getClaimHistory = () => {
  return axios.get("http://localhost:8080/api/manager/claim/history", {
    headers: getJsonHeadersWithJWT(),
  });
};

let approveClaim = (data) => {
  return axios.post("http://localhost:8080/api/manager/claim/approve", data, {
    headers: getJsonHeadersWithJWT(),
  });
};

let rejectClaim = (data) => {
  return axios.post("http://localhost:8080/api/manager/claim/reject", data, {
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
  getEntitlementLeft
};
