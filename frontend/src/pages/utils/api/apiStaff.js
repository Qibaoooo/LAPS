import axios from "axios";
import { getJsonHeadersWithJWT } from "../properties";

let getLeaveList = () => {
  return axios.get("http://localhost:8080/api/staff/leave/list", {
    headers: getJsonHeadersWithJWT(),
  });
};

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

let cancelLeave = (leaveId) => {
  return axios.delete("http://localhost:8080/api/staff/leave/cancel/" + leaveId, {
    headers: getJsonHeadersWithJWT(),
  });
}

export {
  getLeaveList,
  getClaimList,
  createNewClaim,
  setClaimDataOnLoad,
  editClaim,
  deleteClaim,
  cancelLeave,
};
