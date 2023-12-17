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

let getClaimList = () => {
  return axios.get("http://localhost:8080/api/manager/claim/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

let approveClaim = (id) => {
  return axios.post("http://localhost:8080/api/manager/claim/approve", id, {
    headers: getJsonHeadersWithJWT(),
  })
};

let rejectClaim = (id) => {
  return axios.post("http://localhost:8080/api/manager/claim/reject", id, {
    headers: getJsonHeadersWithJWT(),
  })
};

export { getLeaveList, getLeaveHistory, getClaimList, approveClaim, rejectClaim };
