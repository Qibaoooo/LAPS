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

  let editEmployeeInfo = (employee) => {
    return axios.post("http://localhost:8080/api/admin/employee/edit/{id}", employee, {
      headers: getJsonHeadersWithJWT(),
    });
  };

  let setEditDataOnLoad = async (id, ref) => {
    try {
      const resp = await getEmployeeList();
      const list = resp.data;
      list.forEach(employee => {
        if (id == employee.id) {
          console.log("found employee " + "id" + " under current user")
          ref.current.querySelector("#formUserName").value = employee.userName;
          ref.current.querySelector("#formPassword").value = employee.password;
          ref.current.querySelector("#formManager").value = employee.managerName; 
          ref.current.querySelector("#formRole").value = employee.roleName;
          ref.current.querySelector("#formAnnual").value = employee.annualLeaveEntitlement;
          ref.current.querySelector("#formMedical").value = employee.medicalLeaveEntitlement;
          ref.current.querySelector("#formCompensation").value = employee.compensationLeaveEntitlement;       
        }
      });
    } catch (e) {
    }
  };

  let getRoleList = () => {
    return axios.get("http://localhost:8080/api/admin/role", {
      headers: getJsonHeadersWithJWT(),
    });
  };

export { getEmployeeList,createNewEmployee,editEmployeeInfo,getRoleList,setEditDataOnLoad};