import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";
import { getUserinfo } from "../userinfo";

let getLeaveList = () => {
  return axios.get("http://localhost:8080/api/leave/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

export { getLeaveList };
