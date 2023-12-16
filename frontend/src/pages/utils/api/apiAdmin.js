import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getEmployeeList = () => {
  return axios.get("http://localhost:8080/api/admin/employee", {
    headers: getJsonHeadersWithJWT(),
  });
};

export { getEmployeeList};