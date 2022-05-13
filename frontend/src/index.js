import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

import {Route, BrowserRouter, Routes} from "react-router-dom";

import Home from './Home';
import Login from "./components/Login";
import Register from "./components/Register";
import CustomerProfile from "./components/CustomerProfile";
import AdminProfile from "./components/AdminProfile";
import ManagerProfile from "./components/ManagerProfile";


export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                <Route exact path="/login" element = {<Login/>} />
                <Route exact path="/register" element = {<Register/>} />
                <Route exact path="/customerprofile" element = {<CustomerProfile/>} />
                <Route exact path="/adminprofile" element = {<AdminProfile/>} />
                <Route exact path="/managerprofile" element = {<ManagerProfile/>} />
            </Routes>
        </BrowserRouter>
    );
}

ReactDOM.render(<App />, document.getElementById('root'));