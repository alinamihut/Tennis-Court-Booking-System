import React, {Component} from "react";

import Form from "react-bootstrap/Form";

import Button from "react-bootstrap/Button";
import './Home.css';
import {Navigate} from "react-router";

export default class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '', password: '', userLoggedIn:0, adminLoggedIn:0, managerLoggedIn:0
        }
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    async handleSubmit(event){
        event.preventDefault();
        await this.setState({
            username: event.target.elements.username.value,
            password: event.target.elements.password.value,
        })
        const data = {
            username: this.state.username,
            password: this.state.password
        }

        //let auth =  localStorage.getItem('user_token');
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json',
                'Accept': 'application/json'},
            body: JSON.stringify(data)
        };

        if ( event.nativeEvent.submitter.innerText === "Login as customer") {
            console.log(event.nativeEvent.submitter.innerText);
            fetch('http://localhost:8080/sd_project1/customer/login', requestOptions)
                .then(
                    response => {if (response.status === 400) {
                        alert ('Wrong credentials!')
                    }
                    else if (response.status === 200 ){
                        this.setState({userLoggedIn:1});

                    }
                    });}
        else if ( event.nativeEvent.submitter.innerText === "Login as administrator") {
            fetch('http://localhost:8080/sd_project1/admin/login', requestOptions)
                .then(
                    response => {
                        if (response.status === 400) {
                            alert ('Wrong credentials!');}

                        else if (response.ok){
                            this.setState({adminLoggedIn:1})

                        }
                    }

                )

        }

        if ( event.nativeEvent.submitter.innerText === "Login as tennis court manager") {
            console.log(event.nativeEvent.submitter.innerText);
            fetch('http://localhost:8080/sd_project1/manager/login', requestOptions)
                .then(
                    response => {if (response.status === 400) {
                        alert ('Wrong credentials!')
                    }
                    else if (response.status === 200 ){
                        this.setState({managerLoggedIn:1});

                    }
                    });}
    }

    render() {
        if (this.state.userLoggedIn === 1){
            localStorage.setItem('customer', this.state.username);
            return <Navigate to="/customerprofile"> </Navigate>
        }
        else if (this.state.adminLoggedIn === 1){
            localStorage.setItem('admin', this.state.username);

            return <Navigate to="/adminprofile"> </Navigate>
        }
        else if (this.state.managerLoggedIn === 1){
            localStorage.setItem('manager', this.state.username);

            return <Navigate to="/managerprofile"> </Navigate>
        }
        return (

            <div className="Login">
                <h1>  Log in </h1>
                <Form onSubmit={this.handleSubmit}>
                    <div>
                        <label> Username:
                            <input className="textfield"
                                   type="text"
                                   name="username"
                                // onChange={this.handleChange}
                            />
                        </label>
                    </div>
                    <div>
                        <label>Password:
                            <input className="textfield"
                                   type="password"
                                   name="password"
                            />
                        </label>
                    </div>

                    <Button className= "button"  variant="light" size="lg" type="submit">Login as customer </Button>
                    <Button className= "button"  variant="light" size="lg" type="submit" >Login as administrator</Button>
                    <Button className= "button"  variant="light" size="lg" type="submit" >Login as tennis court manager</Button>

                </Form>

            </div>

        );
    }
}