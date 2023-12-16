import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getLeaveList = () => {
  return axios.get("http://localhost:8080/api/manager/leave/view", {
    headers: getJsonHeadersWithJWT(),
  });
};

export { getLeaveList };