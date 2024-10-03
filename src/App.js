import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './LoginComponent/login';
import Register from './RegisterComponent/register';
import LibraryDashboard from './LibraryComponent/library';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/library" element={<LibraryDashboard />} />  {/* Protected route after login */}
      </Routes>
    </Router>
  );
};

export default App;

