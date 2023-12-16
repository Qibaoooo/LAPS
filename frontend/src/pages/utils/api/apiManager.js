import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getLeaveList = () => {
  return axios.get("http://localhost:8080/api/manager/leave/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

let getClaimList = () => {
    return axios.get("http://localhost:8080/api/manager/claim/list", {
      headers: getJsonHeadersWithJWT(),
    });
  };

export { getLeaveList, getClaimList };