import React, { Component } from 'react';
import './App.css';
import {InputGroup, DropdownButton, Dropdown, FormControl, Button} from 'react-bootstrap';


class Home extends Component {
  state = {
    isLoading: true,
    races: [],
    userName: String,
    passwort: String,
    selectedRace: Number,
  };

  async componentDidMount() {
    const response = await fetch('/api/race');
    const body = await response.json();
    this.setState({races: body, isLoading: false, userName:"", passwort:""});
  }

  onSubmit(e) {
    e.preventDefault();
    var title = this.title;
    console.log(title);
}

  render() {
    const {races, isLoading} = this.state;

    if(isLoading) {
      return <p>Loading...</p>;
    }

    console.log(this.state.selectedRace);

    return (
      
      <div className="App">
        <header className="App-header">
          <div className="App-intro">
            <h2>Race List</h2>

            <InputGroup className="mb-3">
              <select className="custom-select" id="selected-race" onChange = {evt => this.updateSelectedRace(evt)}>
                {races.map(race => 
                  <option value={race.id}>{race.name}</option>
                  )}
              </select>
              <FormControl aria-describedby="basic-addon1" placeholder="NickName" type="text" value={this.state.userName} onChange={evt => this.updateUserName(evt)}/>
              <FormControl aria-describedby="basic-addon1" placeholder="Passwort" type="text" value={this.state.passwort} onChange={evt => this.updatePassword(evt)}/>
            </InputGroup>
            <Button  disabled={this.state.userName === "" || this.state.passwort === ""} href="#" size="lg">Login</Button>

          </div>
        </header>

      </div>
    );
  }
  updateUserName(evt) {
    this.setState({
      userName: evt.target.value
    });
  };

  updatePassword(evt) {
    this.setState({
      passwort: evt.target.value
    });
  };

  updateSelectedRace(evt) {
    this.setState({
      selectedRace: evt.target.value
    });
  };

}

export default Home;
