import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getLeaveList = () => {
  return axios.get("http://localhost:8080/api/staff/leave/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

let createNewClaim = (claim) => {
  return axios.post("http://localhost:8080/api/staff/claim/new", claim, {
    headers: getJsonHeadersWithJWT(),
  });
};


let editClaim = (claim) => {
  return axios.put("http://localhost:8080/api/staff/claim/edit", claim, {
    headers: getJsonHeadersWithJWT(),
  });
}

let getClaimList = () => {
  return axios.get("http://localhost:8080/api/staff/claim/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

let setClaimDataOnLoad = async (id, ref) => {
  try {
    const resp = await getClaimList();
    const list = resp.data;
    list.forEach(claim => {
      if (id == claim.id) {
        console.log("found claim " + "id" + " under current user")
        ref.current.querySelector("#formDescription").value = claim.description;
        ref.current.querySelector("#formDate").value = claim.date;
        ref.current.querySelector("#formTime").value = claim.time;        
      }
    });
  } catch (e) {
  }
};

let cancelLeave = (leaveId) => {
  return axios.put("http://localhost:8080/api/staff/leave/cancel/" + leaveId, {}, {
    headers: getJsonHeadersWithJWT(),
  });
};

let setLeaveDataOnLoad = async (leaveId, formRef) => {
  try{
    const response = await getLeaveList();
    const list = response.data;
    list.forEach(leaveData => {
      if (leaveData.id == leaveId) {
        console.log("found");
        formRef.current.querySelector("#formFromDate").value = leaveData.fromDate;
        formRef.current.querySelector("#formToDate").value = leaveData.toDate;
        formRef.current.querySelector("#formType").value = leaveData.type;
        formRef.current.querySelector("#formDescription").value = leaveData.description;
      }
      });
    
  } catch (error) {
    console.error("Error fetching leave data:", error);
  }
};

let editLeave = (leaveapplication) => {
  return axios.put("http://localhost:8080/api/staff/leave/edit", leaveapplication, {
    headers: getJsonHeadersWithJWT(),
  });
}

export { getLeaveList, getClaimList, createNewClaim, setClaimDataOnLoad, editClaim, cancelLeave, setLeaveDataOnLoad, editLeave };
