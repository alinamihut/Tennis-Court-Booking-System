import React, {Component} from "react";
import './Home.css';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

export default class CustomerProfile extends Component {
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

        this.handleDropdownChangeTennisCourt= this.handleDropdownChangeTennisCourt.bind(this);
        this.handleSubmitBook = this.handleSubmitBook.bind(this);
        this.handleChangeDate = this.handleChangeDate.bind(this);
        this.handleDropdownChangeZone = this.handleDropdownChangeZone.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    async componentDidMount() {
        fetch('http://localhost:8080/sd_project1/tenniscourt/getall')
            .then(async response => {
                const tennisCourtsFromApi = await response.json();
                this.setState({
                    tennisCourtsList: tennisCourtsFromApi});})
            .catch(error => {
                console.log(error);
            });
        fetch(
            'http://localhost:8080/sd_project1/tenniscourt/getareas').then(async response => {
            const areas = await response.json();

            this.setState({
                areasList: areas
            });
        })
            .catch(error => {
                console.log(error);
            });
    }


   async handleSubmitBook(event){
        // this.setState({ selectedRestaurant: e.target.value });
       // localStorage.setItem('restaurant', this.state.selectedTennisCourt);
       event.preventDefault();
       await this.setState({
           reservationTime: event.target.elements.startHour.value

       })
       const data = {
           tennisCourt: this.state.selectedTennisCourt,
           customer: localStorage.getItem('customer'),
           date: this.state.reservationDate,
           startTime: this.state.reservationTime

       }

       console.log(data)
       const requestOptions = {
           method: 'POST',
           headers: {'Content-Type': 'application/json'},
           body: JSON.stringify(data)
       };

       fetch('http://localhost:8080/sd_project1/reservation/add', requestOptions)
           .then(
               response => {
                   if (response.status === 400) {
                       alert('Please enter valid time date and time!');
                   } else if (response.status === 500) {
                       alert('There already is a reservation for this time frame!');

                   } else if (response.status === 201) {
                       alert('Reservation added successfully!');
                   }
               });


    }

    async handleChangeDate(event) {
       this.setState({
            reservationDate: event});

    }

    async handleDropdownChangeTennisCourt(e) {
        await this.setState({selectedTennisCourt: e.target.value});
    }

    async handleDropdownChangeZone(e2) {
        await this.setState({selectedZone: e2.target.value});
    }

   handleSubmit(){
        // this.setState({ selectedRestaurant: e.target.value });
        localStorage.setItem('zone', this.state.selectedZone);
        console.log(this.state.selectedZone)
    }
    render() {

        return (
            <div className="App" className="centered">
                <div>
                <h1 >Welcome, {localStorage.getItem('customer')}! <br /> </h1>
                <h2>Available Tennis Courts <br /> </h2>
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

                <Form className="form-line" onSubmit={this.handleSubmitBook}>
                    <h2>Book a tennis court <br /> </h2>
                <select className="textfield"  onChange={ (e) =>this.handleDropdownChangeTennisCourt(e)}>
                    {this.state.tennisCourtsList.map(optn => (
                        <option>{optn.name}</option>
                    ))}

                </select>
                    <br/>
                    <label className="label" >Date </label>
                    <DatePicker wrapperClassName="datePicker" selected={this.state.reservationDate} onChange={ (e) =>this.handleChangeDate(e) }
                                name="date" dateFormat="yyyy-MM-dd"
                                />
                    <br/>
                    <label className="label" >Start hour <br/> Note: your booking is made for one hour <br/>
                        <input className="textfield"
                               type="startHour"
                               name="startHour"
                        />
                    </label>
                    <br/>
                    <Button className="textfield" value="customer" variant="secondary" size="lg" type="submit">Book</Button>
                 </Form>
                <Form className="form-line" onSubmit={this.handleSubmitBook}>
                    <h2>Search for a tennis court in an area: <br /> </h2>
                    <select className="textfield"  onChange={ (e) =>this.handleDropdownChangeZone(e)}>
                        {this.state.areasList.map(optn => (
                            <option>{optn}</option>
                        ))}

                    </select>
                    <br/>

                    <Button className="textfield" value="customer" variant="secondary" size="lg" onClick={(e) => this.handleSubmit(e) } href={"/viewtenniscourtsarea"}type="submit">Search</Button>
                </Form>
            </div>);
    }
}