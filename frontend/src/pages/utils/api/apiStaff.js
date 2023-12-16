import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getLeaveList = () => {
  return axios.get("http://localhost:8080/api/staff/leave/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

let createNewClaim = (claim) => {
  return axios.post("http://localhost:8080/api/staff/claim/new", claim, {
    headers: getJsonHeadersWithJWT(),
  });
};

export { getLeaveList, createNewClaim };
