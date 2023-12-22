import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getMovementRegister = (year, month) => {
    return axios.get("http://localhost:8080/api/common/leaves", {
      headers: getJsonHeadersWithJWT(),
      params: {
        year: year, // Assuming leaveapplication has an 'id' property
        month: month
      }
    });
  };


export default getMovementRegister;