import React from "react";
import MyNavBar from "./components/myNavBar";
import { Container } from "react-bootstrap";

function aboutPage() {
  return (
    <div>
      <MyNavBar></MyNavBar>
      <Container className="col-md-5 mt-5 " style={{ textAlign:"start"}}>
        <h5>ReactJS Frontend Of Leave Application Processing System (LAPS)</h5>
        <br></br>
        <h5>Made by Team11, GDipSA 57</h5>
        <br></br>
        <p>December 2023</p>
      </Container>
    </div>
  );
}

export default aboutPage;
