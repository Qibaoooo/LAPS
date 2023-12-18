import React from "react";
import { useEffect, useState } from "react";
import { getUserinfoFromLocal } from "../utils/userinfo";
import RedirectionModal from "./redirectionModal";

function LoginCheckWrapper({ children, allowRole, runAfterCheck }) {
  const [showModal, setShowModal] = useState(false);
  const [headerMsg, setHeaderMsg] = useState("");
  const [buttonMsg, setButtonMsg] = useState("");
  const [redirectURL, setRedirectURL] = useState("");

  const handleButtonClick = () => (window.location.href = redirectURL);

  useEffect(() => {
    if (!getUserinfoFromLocal()) {
      console.log("login please");
      setHeaderMsg("Please login first.");
      setButtonMsg("Go To Login Page");
      setRedirectURL("/login");
      setShowModal(true);
      return;
    }

    // if user is logged in already, we check role.
    if (!allowRole) {
      // no allowRole supplied, allow user.
      return;
    }
    if (!allowRole.includes(getUserinfoFromLocal().roleId)) {
      setHeaderMsg("You are not authorized.");
      setButtonMsg("Go To Home Page");
      setRedirectURL("/home");
      setShowModal(true);
      return;
    }

    // all check passed
    runAfterCheck();
  }, []);

  return (
    <div>
      {children}
      <RedirectionModal
        show={showModal}
        handleButtonClick={handleButtonClick}
        headerMsg={headerMsg}
        buttonMsg={buttonMsg}
      ></RedirectionModal>
    </div>
  );
}

export default LoginCheckWrapper;
