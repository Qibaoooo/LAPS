import axios from "axios";
import { JsonHeaders } from "./properties";

let signup = (u, p) => {
  return axios.post(
    "http://localhost:8080/api/auth/login",
    { username: u, password: p },
    {
      headers: JsonHeaders,
    }
  );
};

export { signup };
