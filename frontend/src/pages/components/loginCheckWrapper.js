import React from "react";
import { useEffect, useState } from "react";
import { getUserinfo } from "../utils/userinfo";
import PleaseLoginModal from "./pleaseLoginModal";

function LoginCheckWrapper({ children }) {
  const [showPleaseLogin, setShowPleaseLogin] = useState(false);

  const handleGoToLogin = () => (window.location.href = "/login");

  useEffect(() => {
    if (!getUserinfo()) {
      console.log("login please");
      setShowPleaseLogin(true);
    }
  }, []);

  return (
    <div>
      {children}
      <PleaseLoginModal
        show={showPleaseLogin}
        handleGoToLogin={handleGoToLogin}
      ></PleaseLoginModal>
    </div>
  );
}

export default LoginCheckWrapper;
