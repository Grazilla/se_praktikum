import React, { Component } from 'react';
import './App.css';

import { Button } from 'react-bootstrap';
import { Map as LeafletMap, TileLayer, Marker} from 'react-leaflet';
import { featureGroup } from 'leaflet';


class Viewer extends Component {
  state = {
    isLoading: true,
    races: [],
    userName: String,
    passwort: String,
    selectedRace: Number,
    currentXPos1: Number,
    currentXPos2: Number,
    currentYPos1: Number,
    currentYPos2: Number, 
  };

  async componentDidMount() {
    const response = await fetch('/api/race');
    const body = await response.json();
    this.setState({ races: body, isLoading: false, userName: "", passwort: "", currentXPos1setState:48.335369, currentXPos2setState:48.0, currentYPos1setState:14.324181, currentyPos2setState: 12.0 });
  }

  render() {
    const {isLoading } = this.state;


    if (isLoading) {
      return <p>Loading...</p>;
    }

    console.log(this.state.selectedRace);

    return (

      <div className="App">
        <header className="App-header">
          <div className="App-intro">
            <LeafletMap
              center={[48.335369, 14.324181]}
              zoom={15}
              maxZoom={18}
              attributionControl={true}
              zoomControl={true}
              doubleClickZoom={true}
              scrollWheelZoom={true}
              dragging={true}
              animate={true}
              easeLinearity={0.35}

            >
              <TileLayer
                url='http://{s}.tile.osm.org/{z}/{x}/{y}.png'
              />
              <featureGroup>
                <Marker position={[48.335369, 14.324181]}></Marker>
                <Marker position={[48.0, 12.0]}></Marker>
              </featureGroup>
                


            </LeafletMap>

          </div>
          <Button href="/" size="lg">Back</Button>
        </header>

      </div>
    );
  }

}

export default Viewer;
