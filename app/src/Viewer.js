import React from 'react';
import { Map, Marker, Popup, TileLayer, Polyline, Circle } from 'react-leaflet';
import './Viewer.css';

class Viewer extends React.Component {
  constructor(props) {
    super(props);
    const { match: { params } } = this.props;    
    this.state = { raceId: params.raceId, participants: [], mapPosition: [0, 0], route: [[0, 0]], matchpoints: [] };
  }

  getStandings() {
    let url = '/api/race/' + this.state.raceId;
    fetch(url)
      .then(response => response.json())
      .then(body => {
          this.setState({
            participants: body.participants
          });
          console.log(body);
        });
    //TODO: fetch data from backend instead of doing this in code

    // let participants = [];

    // let mod = Math.random() / 10000;

    // participants.push({ id: 1, username: 'ich', waypoints: 3, distance: Math.random(), latitude: 48.336812 + mod, longitude: 14.318426 + mod });
    // participants.push({ id: 2, username: 'du', waypoints: 2, distance: Math.random(), latitude: 48.337526 + mod, longitude: 14.318641 + mod });
    // participants.push({ id: 3, username: 'es', waypoints: 1, distance: Math.random(), latitude: 48.337363 + mod, longitude: 14.320697 + mod });

    // this.setState ({ participants: participants });
  }

  componentDidMount() {
    let url = '/api/race/' + this.state.raceId;
    fetch(url)
      .then(response => response.json())
      .then(body => {
        let coords = [];
        for(let matchpoint of body.matchpoints) {
           coords.push([matchpoint.latitude, matchpoint.longitude]);
        }
        this.setState({
          route: coords,
          matchpoints: body.matchpoints,
          participants: body.participants,
          mapPosition: [body.mapLatitude, body.mapLongitude]
        });
      });

    this.interval = setInterval(() => this.getStandings(), 1000);
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  render() {
    return (
      <div className="container" style={{marginTop: 20 +'px'}}>
        <div className="row">
          <div className="card col-md-4 col-sm-12">
            <div className="card-body">
              <h5 className="card-title">Current Standings</h5>
              {this.state.participants && this.state.participants.map((value, index) => {
                //always use ids as key if possible when the ordering inside the list changes for efficiency reasons (less DOM mutation)
                return <div key={'card' + value.participant.id}><b>{value.participant.name}</b> Waypoints: {value.nextMatchpoint ? value.nextMatchpoint.oid : '-'}, Distance: {value.distance} m</div>
              })}
            </div>
          </div>

          <div className="col-md-1">&nbsp;</div>
          <div className="card col-md-7 col-sm-12">
            <div className="card-body">
              <h5 className="card-title">Map View</h5>
              <Map center={this.state.mapPosition} zoom={17}>
                <TileLayer
                  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
                />
                {this.state.participants && this.state.participants.map((value, index) => {
                  return <Marker key={'marker' + value.participant.id} position={[value.latitude, value.longitude]}><Popup>{value.participant.name}</Popup></Marker>
                })}
                <Polyline color="lime" positions={this.state.route} />
                {this.state.matchpoints && this.state.matchpoints.map((value, index) => {
                  return <Circle key={'matchpoint' + value.id} center={[value.latitude, value.longitude]} color="purple" />
                })}
              </Map>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Viewer;
