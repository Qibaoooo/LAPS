import logo from "./logo.svg";
import "./App.css";
import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/login";
import Home from "./pages/home";
import About from "./pages/about";
import StaffLeaveList from "./pages/staffLeaveList";
import StaffLeaveNew from "./pages/staffLeaveNew";
import StaffClaimList from "./pages/staffClaimList";
import StaffClaimNew from "./pages/staffClaimNew";
import ManagerLeaveList from "./pages/managerLeaveList";
import ManagerClaimList from "./pages/managerClaimList";
import AdminEmployeeList from "./pages/adminEmployeeList";

function App() {
  return (
    <div
      className="App bg-primary-subtle"
      style={{height:"100vh"}}
    >
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/home" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/staff" element={<StaffLeaveList />} />
        <Route path="/staff/leave/list" element={<StaffLeaveList />} />
        <Route path="/staff/leave/new" element={<StaffLeaveNew />} />
        <Route path="/staff/claim/list" element={<StaffClaimList />} />
        <Route path="/staff/claim/new" element={<StaffClaimNew />} />
        <Route path="/manager/leave/list" element={<ManagerLeaveList />} />
        <Route path="/manager/claim/list" element={<ManagerClaimList />} />
        <Route path="/admin/employee" element={<AdminEmployeeList/>}/>
      </Routes>
    </div>
  );
}

export default App;
