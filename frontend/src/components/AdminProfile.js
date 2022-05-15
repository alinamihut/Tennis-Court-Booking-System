import React, {Component} from "react";
import './Home.css';
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

export default class AdminProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            location: '',
            rentingPrice: '',
            description: '',
            managersList: [],
            managersNames: [],
            selectedManager: '',
            areasList: [],
            selectedZone:'',
            administrator: localStorage.getItem('admin'),
            tennisCourtList:[],
            selectedTennisCourt:''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitAddTennisCourt = this.handleSubmitAddTennisCourt.bind(this);
        this.handleDropdownChangeManager = this.handleDropdownChangeManager.bind(this);
        this.handleDropdownChangeZone = this.handleDropdownChangeZone.bind(this);
        this.handleDropdownChangeTennisCourt = this.handleDropdownChangeTennisCourt.bind(this);
        this.handleSubmitDelete = this.handleSubmitDelete.bind(this);


    }

    componentDidMount() {
        fetch(
            'http://localhost:8080/sd_project1/manager/get',)
            .then(async response => {
                const managersFromApi = await response.json();

                this.setState({
                    managersList: managersFromApi
                });
            })
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
        fetch(
            'http://localhost:8080/sd_project1/tenniscourt/get').then(async response => {
            const areas = await response.json();

            this.setState({
                tennisCourtList: areas
            });
        })
            .catch(error => {
                console.log(error);
            });

    }

    handleChange(event) {
        var item = event.target.value;
        let zones = this.state.selectedDeliveryZones;
        zones.push(item);
        this.setState({
            selectedDeliveryZones: zones,
        });
        console.log(this.state.selectedDeliveryZones);

    }

    async handleDropdownChangeManager(e) {
        await this.setState({selectedManager: e.target.value});
        console.log(this.state.selectedManager + "22");
    }

    async handleDropdownChangeZone(e) {
        this.setState({selectedZone: e.target.value});
        console.log(this.state.selectedZone + "33");
    }

    async handleDropdownChangeTennisCourt(e) {
        await this.setState({selectedTennisCourt: e.target.value});
        console.log(this.state.selectedTennisCourt + "*");
    }

     async handleSubmitAddTennisCourt(event) {
         event.preventDefault();

         await this.setState({
             name: event.target.elements.name.value,
             location: event.target.elements.location.value,
             rentingPrice: event.target.elements.rentingPrice.value,
             description: event.target.elements.description.value,

         })

         const data = {
             name: this.state.name,
             location: this.state.location,
             rentingPrice: this.state.rentingPrice,
             description: this.state.description,
             manager: this.state.selectedManager,
             area: this.state.selectedZone,

         }

         console.log(data)
         const requestOptions = {
             method: 'POST',
             headers: {'Content-Type': 'application/json'},
             body: JSON.stringify(data)
         };

         fetch('http://localhost:8080/sd_project1/tenniscourt/add', requestOptions)
             .then(
                 response => {
                     if (response.status === 400) {
                         alert('Please enter a valid price!');
                     } else if (response.status === 500) {
                         alert('This manager already has a tennis court registered!');

                     } else if (response.status === 201) {
                         alert('Tennis court added successfully!');
                     }
                 });
     }


    async handleSubmitDelete(event) {
        event.preventDefault();

        const data = {
            name: this.state.selectedTennisCourt

        }
        const requestOptions = {
            method: 'DELETE',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        };
        fetch('http://localhost:8080/sd_project1/tenniscourt/delete/' + this.state.selectedTennisCourt , requestOptions)
            .then(
            response => {
                if (response.status === 200) {
                    alert('Tennis court deleted!');
                }
            });


    }
    render() {
        return (
            <div className="App">
                <h1>Administrator Profile for {localStorage.getItem('admin')}<br/></h1>
                <Form className="form-line" onSubmit={this.handleSubmitAddTennisCourt}>
                    <h2>Add a tennis court: </h2>
                    <label>Name:
                        <input className="textfield"
                               type="text"
                               name="name"
                            // onChange={this.handleChange}
                        />
                    </label>
                    <br/>
                    <label>Location:
                        <input className="textfield"
                               type="text"
                               name="location"
                            // onChange={this.handleChange}
                        />
                    </label>
                    <br/>
                    <label>Renting price per hour:
                        <input className="textfield"
                               type="text"
                               name="rentingPrice"
                            // onChange={this.handleChange}
                        />
                    </label>
                    <br/>
                    <label>Description:
                        <input className="textfield"
                               type="text"
                               name="description"
                            // onChange={this.handleChange}
                        />
                    </label>
                    <br/>
                    <label> Manager: </label>
                    <select className="textfield"  onChange={ (e) =>this.handleDropdownChangeManager(e)}>
                        {this.state.managersList.map(optn => (
                            <option>{optn}</option>
                        ))}

                    </select>
                    <br/>
                    <label> Zone: </label>
                    <select  className="textfield" onChange={ (e) =>this.handleDropdownChangeZone(e)}>
                        {this.state.areasList.map(optn => (
                            <option>{optn}</option>
                        ))}

                    </select>
                    <br/>
                    <div>
                        <Button className="button" value="customer" variant="secondary" size="lg" type="submit">Add</Button></div>


                </Form>

                <Form>

                    <h2>Delete a tennis court: </h2>
                    <select className="textfield"  onChange={this.handleDropdownChangeTennisCourt}>
                        {this.state.tennisCourtList.map(optn => (
                            <option>{optn}</option>
                        ))}

                    </select>
                    <br/>
                    <Button className="button" value="customer" variant="secondary" size="lg" type="submit"
                            onClick={(e) => this.handleSubmitDelete(e)} href={"/viewmenu"}>Delete</Button>
                </Form>
            </div>);

    }
}