import React, { Component } from 'react';
import './App.css';
import {Button} from 'react-bootstrap';


class Participant extends Component {
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
            <h2>Hello Player</h2>       
          
          </div>
          <Button  href="/" size="lg">Back</Button> 
        </header>

      </div>
    );
  }

}

export default Participant;
