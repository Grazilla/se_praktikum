import React from 'react';
import { Map, Marker, Popup, TileLayer, Polyline, Circle } from 'react-leaflet';
import './Viewer.css';
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';

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
        console.log(body.participants);
      });
  }

  componentDidMount() {
    let url = '/api/race/' + this.state.raceId;
    fetch(url)
      .then(response => response.json())
      .then(body => {
        let coords = [];
        for (let matchpoint of body.matchpoints) {
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
      <div style={{ backgroundColor: "#b3dfe5" }}>
        <div className="container" style={{ marginTop: 20 + 'px' }}>
          <div className="row">
            <div className="card col-md-4 col-sm-12">
              <div className="card-body">
                <h5 className="card-title">Current Standings</h5>
                <div className="card mb-3">
                  <div className="row no-gutters">
                    <div className="col-md-1">
                      {
                        this.state.participants.map((value, index) => {
                          return <div key={'card' + value.id}>{index + 1}
                            <div className="col-md-4">
                              <img src="https://steamuserimages-a.akamaihd.net/ugc/885378750233363168/CFA6808BAC06EC8983BE6D9E13D2D8790FAD3307/" width="100" height="100" alt="Yoshi"></img>
                            </div>
                            <div className="col-md-7">
                              <div className="card-body">
                                <Accordion defaultActiveKey="0">
                                  <Card>
                                    <Card.Header>
                                      <Accordion.Toggle as={Button} variant="link" eventKey="0">
                                        {value.participant.name}
                                      </Accordion.Toggle>
                                    </Card.Header>
                                    <Accordion.Collapse eventKey="0">
                                      <Card.Body>                                  
                                        Next matchpoint: {value.nextMatchpoint.oid},
                                        Distance: {value.distance}m,
                                      </Card.Body>
                                    </Accordion.Collapse>
                                  </Card>
                                </Accordion>
                              </div>
                            </div>
                          </div>
                        })
                      }
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="col-md-1">&nbsp;</div>
            <div className="card col-md-7 col-sm-12">
              <div className="card-body">
                <h5 className="card-title">Map View</h5>
                <div className="leaflet-container">
                  <Map center={this.state.mapPosition} zoom={15}>
                    <TileLayer
                      url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                      attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
                    />
                    {this.state.participants.map((value, index) => {
                      return <Marker key={'marker' + value.id} position={[value.latitude, value.longitude]}><Popup>{value.username}</Popup></Marker>
                    })}
                    <Polyline color="lime" positions={this.state.route} />
                    {this.state.route.map((value, index) => {
                      return <Circle key={'waypoint' + index} center={[value[0], value[1]]} color="purple" />
                    })}
                  </Map>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Viewer;
