import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getUserDetails = () => {
  return axios.get("http://localhost:8080/api/common/user-details", {
    headers: getJsonHeadersWithJWT(),
  });
};

export { getUserDetails };
