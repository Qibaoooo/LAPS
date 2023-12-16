import React, { useEffect, useState } from "react";
import {
  Navbar,
  Container,
  Nav,
  NavDropdown,
  Button,
  OverlayTrigger,
  Tooltip,
} from "react-bootstrap";
import { getUserinfoFromLocal } from "../utils/userinfo";
import { BsFillMoonStarsFill, BsFillSunFill } from "react-icons/bs";
import { saveColorMode, getColorMode } from "../utils/colorModeSave";

function MyNavBar(props) {
  let userinfo;

  const [colorMode, setColorMode] = useState("");
  const [showStaffMenu, setShowStaffMenu] = useState(false);
  const [showManagerMenu, setShowManagerMenu] = useState(false);
  const [showaAdminMenu, setShowAdminMenu] = useState(false);
  const [showLogin, setShowLogin] = useState(true);
  const [user, setUser] = useState("guest");

  useEffect(() => {
    if (getColorMode()) {
      setColorMode(getColorMode());
      document.documentElement.setAttribute("data-bs-theme", colorMode);
    }
  }, [colorMode]);

  useEffect(() => {
    userinfo = getUserinfoFromLocal();
    if (userinfo === null) {
      // user not logged in, no need to update navbar menus
      return;
    }

    // user is logged in, update menus for different roles
    if (showLogin === true) {
      setShowLogin(false);
    }
    if (showaAdminMenu === false && userinfo.roleId === "ROLE_admin") {
      setShowAdminMenu(true);
    }
    if (showStaffMenu === false && userinfo.roleId === "ROLE_staff") {
      setShowStaffMenu(true);
    }
    if (showManagerMenu === false && userinfo.roleId === "ROLE_manager") {
      setShowStaffMenu(true);
      setShowManagerMenu(true);
    }
    if (user === "guest") {
      setUser(userinfo.username);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("userinfo");
    window.location.href = "/login";
  };

  const onClickColorModeButton = () => {
    if (getColorMode() === "light") {
      // console.log("set to dark")
      saveColorMode("dark");
      setColorMode("dark");
    } else {
      // console.log("set to light")
      saveColorMode("light");
      setColorMode("light");
    }
  };

  return (
    <Navbar className="justify-content-between bg-success-subtle">
      <Container className="justify-content-start">
        <Navbar.Brand href="/about">
          <h3
            style={{
              paddingLeft: "20px",
              textDecoration: "underline",
            }}
          >
            LAPS
          </h3>
        </Navbar.Brand>
        {!showLogin ? (
          <Nav.Link className="mx-3" href="/home">
            Home
          </Nav.Link>
        ) : (
          <Nav.Link className="mx-3" href="/login">
            Login
          </Nav.Link>
        )}
        {showStaffMenu && (
          <NavDropdown className="mx-3" title="Staff" id="basic-nav-dropdown">
            <NavDropdown.Item href="/staff/leave/list">
              View Leave Applications
            </NavDropdown.Item>
            <NavDropdown.Item href="/staff/leave/new">
              New Leave Application
            </NavDropdown.Item>
            <NavDropdown.Item href="/staff/claim/list">
              View Compensation Claims
            </NavDropdown.Item>
            <NavDropdown.Item href="/staff/claim/new">
              New Compensation Claim
            </NavDropdown.Item>
            {/* <NavDropdown.Divider /> */}
          </NavDropdown>
        )}
        {showManagerMenu && (
          <NavDropdown className="mx-3" title="Manager" id="basic-nav-dropdown">
            <NavDropdown.Item href="/manager/leave/list">
              Process Leave Application
            </NavDropdown.Item>
            <NavDropdown.Item href="/manager/?">TO BE CHANGED</NavDropdown.Item>
            <NavDropdown.Item href="/manager/claim/list">
              Process Compensation Claim
            </NavDropdown.Item>
            <NavDropdown.Item href="/manager/?">TO BE CHANGED</NavDropdown.Item>
          </NavDropdown>
        )}
        {showaAdminMenu && (
          <NavDropdown className="mx-3" title="Admin" id="basic-nav-dropdown">
            <NavDropdown.Item href="/admin/?">TO BE CHANGED</NavDropdown.Item>
            <NavDropdown.Item href="/admin/?">TO BE CHANGED</NavDropdown.Item>
            <NavDropdown.Item href="/admin/?">TO BE CHANGED</NavDropdown.Item>
            <NavDropdown.Item href="/admin/?">TO BE CHANGED</NavDropdown.Item>
          </NavDropdown>
        )}
      </Container>
      <Container
        className="justify-content-end mx-3"
        style={{ alignItems: "end" }}
      >
        <OverlayTrigger
          key="colorModeButton"
          placement="bottom"
          overlay={<Tooltip id={`tooltip-colorModeButton`}>Toggle Light/Dark</Tooltip>}
        >
          <Button
            style={{ borderRadius: "15px" }}
            className="opacity-50"
            size="sm"
            onClick={onClickColorModeButton}
          >
            {colorMode === "light" ? (
              <BsFillMoonStarsFill />
            ) : (
              <BsFillSunFill />
            )}
          </Button>
        </OverlayTrigger>
        <h6 className="mx-3" href="">
          hello, {user}
        </h6>
        {!showLogin && (
          <h6 onClick={handleLogout}>
            <a href="">logout</a>
          </h6>
        )}
      </Container>
    </Navbar>
  );
}

export default MyNavBar;
