import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getEmployeeList = () => {
  return axios.get("http://localhost:8080/api/admin/employee", {
    headers: getJsonHeadersWithJWT(),
  });
};

let createNewEmployee = (employee) => {
    return axios.post("http://localhost:8080/api/admin/employee/new", employee, {
      headers: getJsonHeadersWithJWT(),
    });
  };

let getAllList = () => {
    return axios.get("http://localhost:8080/api/admin/employee/new", {
      headers: getJsonHeadersWithJWT(),
    });
  };

let createNewRole = (role) => {
  return axios.post("http://localhost:8080/api/admin/role/new", role, {
    headers: getJsonHeadersWithJWT(),
  });
};

let editRole=(role)=>{
  return axios.put("http://localhost:8080/api/admin/role/edit", role, {
    headers: getJsonHeadersWithJWT(),
  })
}


export { getEmployeeList,createNewEmployee,createNewRole,editRole};

