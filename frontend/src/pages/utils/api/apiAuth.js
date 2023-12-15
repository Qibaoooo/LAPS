import axios from "axios";
import { getJsonHeaders, getJsonHeadersWithJWT } from "../properties";

let login = (u, p) => {
  return axios.post(
    "http://localhost:8080/api/auth/login",
    { username: u, password: p },
    {
      headers: getJsonHeaders(),
    }
  );
};

export { login };
