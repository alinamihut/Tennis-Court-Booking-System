import React, {Component} from "react";
import './Home.css';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

export default class ViewTennisCourtCustomer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tennisCourtsList:[],
            selectedTennisCourt:'',
            reservationDate:'',
            reservationHour:'',
            areasList:[],
            selectedZone:''

        };

    }
    async componentDidMount() {
        fetch('http://localhost:8080/sd_project1/tenniscourt/getall/' + localStorage.getItem('zone'))
            .then(async response => {
                const tennisCourtsFromApi = await response.json();
                this.setState({
                    tennisCourtsList: tennisCourtsFromApi});})
            .catch(error => {
                console.log(error);
            });

    }

    render() {

        return (
            <div className="App" className="centered">
                <div>
                    <h1 >Available Tennis Courts in {localStorage.getItem('zone')} <br /> </h1>
                    <table className="textfield">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Location</th>
                            <th>Renting Price/Hour</th>
                            <th>Description</th>
                            <th>Manager </th>
                            <th>Zone</th>

                        </tr>
                        </thead>
                        <tbody>

                        {this.state.tennisCourtsList.map((item, i) => (
                            <tr key={i}>
                                <td>{item.name}</td>
                                <td>{item.location}</td>
                                <td>{item.rentingPrice}</td>
                                <td>{item.description}</td>
                                <td>{item.manager}</td>
                                <td>{item.area}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

            </div>);
    }
}