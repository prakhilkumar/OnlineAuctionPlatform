import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Import Components
import Main from './Components/Main';
import Login from './Components/Login';
import SignUp from './Components/Signup';
import UserDashBoard from './Components/UserDashBoard';
import AdminDashBoard from './Components/AdminDashBoard';
import CreateBidForm from './Components/CreateBidForm'
import PremiumMembership from './Components/PremiumMembership';
import UserWallet from './Components/UserWallet';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/create-bid" element={<CreateBidForm />} />
        <Route path="/Login" element={<Login />} />
        <Route path="/SignUp" element={<SignUp />} />
        <Route path="/UserDashBoard" element={<UserDashBoard />} />
        <Route path="/AdminDashBoard" element={<AdminDashBoard />} />
        <Route path="/premium-membership" element={<PremiumMembership/>} />
        <Route path="/user-wallet" element={<UserWallet/>} />
      </Routes>
    </Router>
  );
}

export default App;
