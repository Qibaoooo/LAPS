import Cookies from "js-cookie";
import React from "react";
import { Navbar, Container, Nav, NavDropdown, Row } from "react-bootstrap";

function MyNavBar(props) {
  let cookie = Cookies.get("LAPS_USERINFO");
  let userinfo;
  if (cookie) {
    userinfo = JSON.parse(cookie);
  }

  const handleLogout = () => {
    fetch("http://localhost:8080/api/logout", {
      method: "POST",
      mode: "cors",
      credentials: "include",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:3000",
        "Access-Control-Allow-Methods": "POST,PATCH,OPTIONS",
        "Access-Control-Allow-Credentials": true,
      },
    }).then((r) => {
      // window.location.reload()
      Cookies.remove("LAPS_USERINFO");
    });
  };

  return (
    <Navbar className="bg-body-tertiary justify-content-between">
      <Container className="justify-content-start">
        <Navbar.Brand href="/home">LAPS</Navbar.Brand>
        <Nav.Link className="mx-3" href="/home">
          Home
        </Nav.Link>
        <Nav.Link className="mx-3" href="/login">
          Login
        </Nav.Link>
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
      <Container className="justify-content-end mx-3">
        <Nav.Link className="mx-3" href="">
          Hello {userinfo ? userinfo.username : "User"}
        </Nav.Link>
        <Nav.Link className="mx-3" onClick={handleLogout}>
          logout
        </Nav.Link>
      </Container>
    </Navbar>
  );
}

export default MyNavBar;
