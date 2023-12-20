import React, { useEffect, useState } from "react";
import { Table, Col, Button, Badge } from "react-bootstrap";
import { BsTriangleFill, BsArrowsCollapse } from "react-icons/bs";

function MyTable({ children, foldable = false }) {
  const sizeChoices = [55, 350, 750];
  const [size, setSize] = useState(0);
  const [showExpand, setShowExpand] = useState(true);
  const [showFold, setShowFold] = useState(true);
  const [tableOverflow, setTableOverflow] = useState("scroll");

  useEffect(() => {
    if (foldable) {
      setSize(sizeChoices[1]);
    } else {
      setSize(sizeChoices[2]);
    }
  }, []);

  const onExpand = (e) => {
    switch (size) {
      case sizeChoices[1]:
        setSize(sizeChoices[2]);
        setShowExpand(false);
        break;
      case sizeChoices[0]:
        setSize(sizeChoices[1]);
        setShowFold(true);
        setTableOverflow("scroll");
      default:
        break;
    }
  };
  const onFold = (e) => {
    switch (size) {
      case sizeChoices[2]:
        setSize(sizeChoices[1]);
        setShowExpand(true);
        break;
      case sizeChoices[1]:
        setSize(sizeChoices[0]);
        setShowFold(false);
        setTableOverflow("hidden");
      default:
        break;
    }
  };

  return (
    <Col className="col-md-10 mx-auto" style={{ position: "relative" }}>
      {foldable && (
        <div style={{ position: "", marginLeft: "101%", maxHeight: "1px" }}>
          <BsTriangleFill
            className="folding"
            bg="info"
            onClick={onFold}
            style={{ cursor: "pointer" }}
          />

          <BsTriangleFill
            className="expanding"
            bg="info"
            onClick={onExpand}
            style={{ cursor: "pointer" }}
          />
        </div>
      )}
      <div
        style={{
          borderRadius: "20px",
          overflow: "hidden",
          overflowX: "scroll",
          maxHeight: `${size}px`,
          transition: "max-height 0.75s ease-out",
          overflowY: `${tableOverflow}`,
        }}
      >
        <Table className="opacity-75" style={{ marginBottom: "0px" }}>
          {children}
        </Table>
      </div>
    </Col>
  );
}

export default MyTable;
