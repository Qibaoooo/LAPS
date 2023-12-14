import Cookies from "js-cookie";
import React from "react";
import {
  Navbar,
  Container,
  Nav,
  NavDropdown,
  Row,
  Button,
} from "react-bootstrap";
import { getUserinfo } from "../utils/userinfo";
import { logout } from "../utils/api/apiAuth";

function MyNavBar(props) {
  let userinfo = getUserinfo();

  const handleLogout = () => {
    localStorage.clear();
    window.location.href = "/login";
  };

  return (
    <Navbar
      bg="primary"
      className="justify-content-between"
      style={{ color: "white" }}
    >
      <Container className="justify-content-start">
        <Navbar.Brand href="/home">
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
        <Nav.Link className="mx-3" href="/home">
          Home
        </Nav.Link>
        {!userinfo && (
          <Nav.Link className="mx-3" href="/login">
            Login
          </Nav.Link>
        )}
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
      </Container>
      <Container
        className="justify-content-end mx-3"
        style={{ alignItems: "baseline" }}
      >
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
