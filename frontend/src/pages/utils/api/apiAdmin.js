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

let deleteEmployee = (employeeName) => {
  return axios.delete(
    "http://localhost:8080/api/admin/employee?username=" + employeeName,
    {
      headers: getJsonHeadersWithJWT(),
    }
  );
};

let editEmployeeInfo = (employee) => {
  console.log(employee);
  return axios.post("http://localhost:8080/api/admin/employee/edit", employee, {
    headers: getJsonHeadersWithJWT(),
  });
};

let getAllList = () => {
  return axios.get("http://localhost:8080/api/admin/employee/new", {
    headers: getJsonHeadersWithJWT(),
  });
};

let setEditDataOnLoad = async (id, ref) => {
  try {
    const resp = await getEmployeeList();
    const list = resp.data;
    console.log("here " + id);

    list.forEach((employee) => {
      if (id == employee.id) {
        console.log("found employee " + "id" + " under current user");
        console.log(employee);
        ref.current.querySelector("#formUserName").value = employee.name;
        ref.current.querySelector("#formPassword").value = employee.password;
        // find manager name
        let managerId = employee.managerId;
        let managerName = "";
        list.forEach((e) => {
          if (e.id == managerId) {
            managerName = e.name;
          }
        });
        ref.current.querySelector("#formManager").value = managerName;
        ref.current.querySelector("#formRole").value = employee.role;
        ref.current.querySelector("#formType").value = employee.type;
        ref.current.querySelector("#formAnnual").value =
          employee.annualLeaveEntitlement;
        ref.current.querySelector("#formMedical").value =
          employee.medicalLeaveEntitlement;
        ref.current.querySelector("#formCompensation").value =
          employee.compensationLeaveEntitlement;
      }
    });
  } catch (e) {}
};

let getRoleList = () => {
  return axios.get("http://localhost:8080/api/admin/role", {
    headers: getJsonHeadersWithJWT(),
  });
};
let createNewRole = (role) => {
  return axios.post("http://localhost:8080/api/admin/role/new", role, {
    headers: getJsonHeadersWithJWT(),
  });
};

let deleteRole = (role) => {
  return axios.delete("http://localhost:8080/api/admin/role?id=" + role, {
    headers: getJsonHeadersWithJWT(),
  });
};

let editRole = (role) => {
  return axios.put("http://localhost:8080/api/admin/role/edit", role, {
    headers: getJsonHeadersWithJWT(),
  });
};

let getHolidays = () => {
  return axios.get("http://localhost:8080/api/admin/holidays", {
    headers: getJsonHeadersWithJWT(),
  });
};

let deleteHoliday = (day) => {
  return axios.delete("http://localhost:8080/api/admin/holidays?day=" + day, {
    headers: getJsonHeadersWithJWT(),
  });
};

let addHoliday = (day, desc) => {
  return axios.post(
    "http://localhost:8080/api/admin/holidays?day=" + day + "&desc=" + desc, {},
    {
      headers: getJsonHeadersWithJWT(),
    }
  );
};

let setRoleDataOnLoad = async (id, ref, setRole) => {
  try {
    const resp = await getRoleList();
    const list = resp.data;
    // console.log(list)
    list.forEach((role) => {
      if (id == role.id) {
        console.log("found role " + "id");
        ref.current.querySelector("#formName").value = role.name;
        ref.current.querySelector("#formDescription").value = role.description;
      }
    });
  } catch (e) {}
};

export {
  getEmployeeList,
  createNewEmployee,
  deleteEmployee,
  editEmployeeInfo,
  getRoleList,
  setEditDataOnLoad,
  createNewRole,
  deleteRole,
  editRole,
  getAllList,
  setRoleDataOnLoad,
  getHolidays,
  deleteHoliday,
  addHoliday,
};
