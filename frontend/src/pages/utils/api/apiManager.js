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

let getSubordinateClaimList = () => {
    return axios.get("http://localhost:8080/api/manager/claim/list", {
      headers: getJsonHeadersWithJWT(),
    });
  };

export { getLeaveList, getLeaveHistory, getSubordinateClaimList };