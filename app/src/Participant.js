import React, { Component } from 'react';
import './App.css';
import {Button} from 'react-bootstrap';
import QrReader from 'react-qr-reader'
import Home from './Home';


class Participant extends Component {
  state = {
    isLoading: true,
    race: undefined,
    userName: String,
    passwort: String,
    selectedRaceId: Number,
    nextMatchpointId: Number,
    matchpoints: [],
    userInfo: 'Auf zum ersten Punkt'
  };

  async componentDidMount() {
    console.log("I was here");
    console.log(Home.state.selectedRaceId);
    this.setState({selectedRaceId: Home.state.selectedRaceId});

    const response = await fetch('/api/race/' + this.state.selectedRaceId);
    const body = await response.json();
    
    const response2 = await fetch('/api/matchpoint/' + body);
    const body2 = await response2.json();
    
    this.setState({race: body, isLoading: false, userName:"", passwort:"", matchpoints: body2, nextMatchpointId: 0 });
    
    for(var m in this.state.matchpoints){
      if(m.oId === 0){
        this.setState({userInfo: m.name}); 
      }
    }

  }
 
  handleScan = data => {
    if (data) {
      for(var m in this.matchpoints){
        if(m.name.equals(data)){
          if(m.oId === this.state.nextMatchpointId){
              this.setState({
                userInfo: data,
                nextMatchpointId: this.nextMatchpointId+1
              });
              
          }
          else if(this.state.nextMatchpointId === 0){ //falls der participant nicht den ersten sondern einen anderen Matchpoint als erstes scannt
              alert("Das ist nicht der erste Matchpoint!");
          }
        }
      }
    }

  }

  render() {
    const {race, isLoading} = this.state;

    if(isLoading) {
      return <p>Loading...</p>;
    }

    console.log(this.state.selectedRace);

    return (
      
      <div style={{ alignContent:'center'}}>
       
        <p>{this.state.userInfo}</p>
        <QrReader
          delay={300}
          onError={this.handleError}
          onScan={this.handleScan}
          style={{ width: '50%'}}
        />
        
      </div>
    );
  }

}

export default Participant;
