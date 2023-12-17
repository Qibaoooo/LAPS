import React from "react";
import MyNavBar from "./components/myNavBar";
import LoginCheckWrapper from "./components/loginCheckWrapper";
import PageTitle from "./components/pageTitle";

function StaffClaimList() {
  return (
    <LoginCheckWrapper>
      <MyNavBar></MyNavBar>
      <PageTitle title={"Compensation Claim List"}></PageTitle>
    </LoginCheckWrapper>
  );
}

export default StaffClaimList;
