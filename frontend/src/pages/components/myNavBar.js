import React, { useEffect, useState } from "react";
import { Navbar, Container, Nav, NavDropdown, Button } from "react-bootstrap";
import { getUserinfo } from "../utils/userinfo";
import { BsFillMoonStarsFill, BsFillSunFill } from "react-icons/bs";
import {saveColorMode, getColorMode} from "../utils/colorModeSave"

function MyNavBar(props) {
  let userinfo = getUserinfo();

  const [colorMode, setColorMode] = useState()

  useEffect(() => {
    if (getColorMode()) {
      console.log("color: ", getColorMode())
      setColorMode(getColorMode())
      document.documentElement.setAttribute("data-bs-theme", colorMode);
    }
  },[colorMode])

  const handleLogout = () => {
    localStorage.removeItem("userinfo");
    window.location.href = "/login";
  };

  const onClickColorModeButton = () => {
    if (getColorMode() === "light") {
      // console.log("set to dark")
      saveColorMode("dark")
      setColorMode("dark")
    } else {
      // console.log("set to light")
      saveColorMode("light")
      setColorMode("light")
    }
  };

  return (
    <Navbar
      bg="primary"
      className="justify-content-between"
      style={{ color: "white" }}
    >
      <Container className="justify-content-start">
        <Navbar.Brand href="/about">
          <h3
            style={{
              color: "white",
              paddingLeft: "20px",
              textDecoration: "underline",
            }}
          >
            {" "}
            LAPS{" "}
          </h3>
        </Navbar.Brand>
        {userinfo ? (
          <Nav.Link className="mx-3" href="/home">
            Home
          </Nav.Link>
        ) : (
          <Nav.Link className="mx-3" href="/login">
            Login
          </Nav.Link>
        )}
        {userinfo &&
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
          <NavDropdown.Divider />
        </NavDropdown>
        }
      </Container>
      <Container
        className="justify-content-end mx-3"
        style={{ alignItems: "baseline" }}
      >
        <Button onClick={onClickColorModeButton}>
          {colorMode==="light" ? 
          <BsFillMoonStarsFill /> :
          <BsFillSunFill />
        }
        </Button>
        <h6 className="mx-3" href="">
          hello, {userinfo ? userinfo.username : "User"}
        </h6>
        {userinfo && (
          <Button className="" onClick={handleLogout} variant="dark" size="sm">
            logout
          </Button>
        )}
      </Container>
    </Navbar>
  );
}

export default MyNavBar;
