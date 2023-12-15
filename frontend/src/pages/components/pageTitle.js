import React from "react";

function PageTitle(props) {
  return (
    <h3 className="my-3 py-3" style={{ fontStyle: "oblique" }}>
      {props.title}
    </h3>
  );
}

export default PageTitle;
