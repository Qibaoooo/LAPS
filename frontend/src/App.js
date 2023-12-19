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
import ManagerLeaveHistory from "./pages/managerLeaveHistory";
import ManagerClaimList from "./pages/managerClaimList";
import AdminEmployeeList from "./pages/adminEmployeeList";
import AdminRoleList from "./pages/adminRoleList";
import AdminEmployeeEdit from "./pages/adminEmployeeEdit";
import StaffClaimEdit from "pages/staffClaimEdit";
import AdminEmployeeNew from "./pages/adminEmployeeNew";
import ManagerClaimHistory from "pages/managerClaimHistory";
import AdminRoleNew from "pages/adminRoleNew";
import AdminRoleEdit from "pages/adminRoleEdit";

function App() {
  return (
    <div
      className="App bg-primary-subtle"
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
        <Route path="/staff/claim/edit" element={<StaffClaimEdit />} />
        <Route path="/manager/leave/list" element={<ManagerLeaveList />} />
        <Route path="/manager/leave/history" element={<ManagerLeaveHistory />} />
        <Route path="/manager/claim/list" element={<ManagerClaimList />} />
        <Route path="/manager/claim/history" element={<ManagerClaimHistory />} />
        <Route path="/admin/employee" element={<AdminEmployeeList/>}/>
        <Route path="/admin/employee/new" element={<AdminEmployeeNew/>}/>
        <Route path="/admin/employee/edit" element={<AdminEmployeeEdit/>}/>
        <Route path="/admin/role" element={<AdminRoleList/>}/>
        <Route path="/admin/role/new" element={<AdminRoleNew/>}/>
        <Route path="/admin/role/edit" element={<AdminRoleEdit/>}/>
      </Routes>
    </div>
  );
}

export default App;
