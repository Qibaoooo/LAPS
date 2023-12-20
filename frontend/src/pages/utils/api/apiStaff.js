import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

/*
LEAVE APPPLICATION
*/
let getLeaveList = () => {
  return axios.get("http://localhost:8080/api/staff/leave/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

let cancelLeave = (leaveId) => {
  return axios.put("http://localhost:8080/api/staff/leave/cancel/"+ leaveId,{}, {
    headers: getJsonHeadersWithJWT(),
  });
};

let setLeaveDataOnLoad = async (leaveId, formRef) => {
  try {
    const response = await getLeaveList();
    const list = response.data;
    list.forEach((leaveData) => {
      if (leaveData.id == leaveId) {
        console.log("found");
        formRef.current.querySelector("#formFromDate").value =
          leaveData.fromDate;
        formRef.current.querySelector("#formToDate").value = leaveData.toDate;
        formRef.current.querySelector("#formType").value = leaveData.type;
        formRef.current.querySelector("#formDescription").value =
          leaveData.description;
      }
    });
  } catch (error) {
    console.error("Error fetching leave data:", error);
  }
};

let editLeave = (leaveapplication) => {
  return axios.put(
    "http://localhost:8080/api/staff/leave/edit",
    leaveapplication,
    {
      headers: getJsonHeadersWithJWT(),
    }
  );
};

/*
COMPENSATION CLAIM
*/
let createNewClaim = (claim) => {
  return axios.post("http://localhost:8080/api/staff/claims", claim, {
    headers: getJsonHeadersWithJWT(),
  });
};

let getClaimList = () => {
  return axios.get("http://localhost:8080/api/staff/claims", {
    headers: getJsonHeadersWithJWT(),
  });
};

let editClaim = (claim) => {
  return axios.put("http://localhost:8080/api/staff/claims", claim, {
    headers: getJsonHeadersWithJWT(),
  });
};

let deleteClaim = (claim) => {
  return axios.delete("http://localhost:8080/api/staff/claims", {
    data: claim,
    headers: getJsonHeadersWithJWT(),
  });
};

let setClaimDataOnLoad = async (id, ref, setClaim) => {
  try {
    const resp = await getClaimList();
    const list = resp.data;
    list.forEach((claim) => {
      if (id == claim.id) {
        console.log("found claim " + "id" + " under current user");
        ref.current.querySelector("#formDescription").value = claim.description;
        ref.current.querySelector("#formDate").value = claim.overtimeDate;
        ref.current.querySelector("#formTime").value = claim.overtimeTime;
        setClaim(claim);
      }
    });
  } catch (e) {}
};

export {
  getLeaveList,
  cancelLeave,
  setLeaveDataOnLoad,
  editLeave,
  getClaimList,
  createNewClaim,
  setClaimDataOnLoad,
  editClaim,
  deleteClaim,
};
