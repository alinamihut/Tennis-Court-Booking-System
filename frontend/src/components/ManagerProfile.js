import React, {Component} from "react";
import './Home.css';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

export default class ManagerProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tennisCourt:'',
            reservationsList:[]

        };


        this.handleSubmitExportReservations = this.handleSubmitExportReservations.bind(this);

    }
    async componentDidMount() {
        fetch('http://localhost:8080/sd_project1/tenniscourt/' + localStorage.getItem('manager'))
            .then(async response => {
                const tennisCourtFromApi = await response.json();
                this.setState({
                    tennisCourt: tennisCourtFromApi});})
            .catch(error => {
                console.log(error);
            });
        fetch('http://localhost:8080/sd_project1/reservation/getall/' + localStorage.getItem('manager'))
            .then(async response => {
                const reservationsFromApi = await response.json();
                this.setState({
                    reservationsList: reservationsFromApi});})
            .catch(error => {
                console.log(error);
            });


    }


    async handleSubmitExportReservations(event){
        // this.setState({ selectedRestaurant: e.target.value });
        // localStorage.setItem('restaurant', this.state.selectedTennisCourt);
        event.preventDefault();
        fetch('http://localhost:8080/sd_project1/reservation/exportreservations/' + this.state.tennisCourt.name)
            .then(
                response => {if (response.status === 400) {
                    alert ('Couldnt export reservations!');}
                else if (response.status === 201){
                    alert ('Reservations exported successfully!');
                }
                });}





    render() {

        return (
            <div className="App" className="centered">
                <div>
                    <h1 >Welcome, {localStorage.getItem('manager')}! <br /> </h1>
                    <h2>Manager for tennis court: {this.state.tennisCourt.name} <br /> </h2>

                </div>

                <body>
                <h2>All upcoming reservations <br /> </h2>
                <table className="textfield">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Customer</th>

                    </tr>
                    </thead>
                    <tbody>

                    {this.state.reservationsList.map((item, i) => (
                        <tr key={i}>
                            <td>{item.date}</td>
                            <td>{item.startTime}</td>
                            <td>{item.endTime}</td>
                            <td>{item.customer}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                </body>
                <div> <Button  className= "button"  value= "customer" variant="light" size="lg" type="submit" onClick={(e) => this.handleSubmitExportReservations(e)} > Export Reservations To PDF </Button> </div>
            </div>);
    }
}