import React from 'react';
//import './participate.css';
import { UserContext } from './UserContext';
import Button from 'react-bootstrap/Button';
import jsQR from 'jsqr';

class Participant extends React.Component {
  constructor(props) {
    super(props);
    const { match: { params } } = this.props;
    this.state = {
      raceId: params.raceId,
      showQr: false,
      userMessage: "Start rois",
      nextMpOId: 1
    };

    this.videoTag = React.createRef();
    this.canvas = React.createRef();

    this.tick = this.tick.bind(this);
  }

  componentDidMount() {
    let payload = {
      id: this.context.userId,
      name: this.context.username
    };
    console.log(payload.id);
    fetch('/api/race/' + this.state.raceId + '/register', {
      method: 'POST',
      headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    })
    .then(response => response.json())
    .then(body => {
      console.log('Registered user for race: ' + body.name)
      if (navigator.geolocation) {
        this.geoWatcher = navigator.geolocation.watchPosition((pos) => this.positionRegistered(pos), null, { enableHighAccuracy: true, maximumAge: 10000 });
      }
      else {
        alert("Your browser does not support geolocation. Unfortunately you can't participate.");
      }
    });
  }

  componentWillUnmount() {
    navigator.geolocation.clearWatch(this.geoWatcher);
    const video = this.videoTag.current;
    if(video && video.srcObject) {
      video.srcObject.getTracks().forEach(track => track.stop());
    }
  }

  positionRegistered(pos) {
    //TODO update location on the backend
    console.log(pos);
    let payload = {
      latitude: pos.coords.latitude,
      longitude: pos.coords.longitude
    };
    fetch('/api/race/' + this.state.raceId + '/updateCoords/' + this.context.userId, {
      method: 'PUT',
      headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    })
    .then(response => response.json())
    .then(body => console.log('Updated coords'));
  }

  handleReadQrCode() {
    this.setState({ showQr: true });
    setTimeout(this.initQrReader.bind(this), 500);
  }

  initQrReader() {
    navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } })
      .then((stream) => {
        const video = this.videoTag.current;
        if(video) {
          video.srcObject = stream;
          video.setAttribute("playsinline", true);
          video.play();
          requestAnimationFrame(this.tick);
        }
      });
  }

  tick() {
    const video = this.videoTag.current;
    if (video && video.readyState === video.HAVE_ENOUGH_DATA) {
      const canvasElement = this.canvas.current;
      const canvas = canvasElement.getContext("2d");

      canvasElement.height = video.videoHeight;
      canvasElement.width = video.videoWidth;

      canvas.drawImage(video, 0, 0, canvasElement.width, canvasElement.height);
      let imageData = canvas.getImageData(0, 0, canvasElement.width, canvasElement.height);

      var code = null; 
      code = jsQR(imageData.data, imageData.width, imageData.height, { inversionAttempts: "dontInvert" });

      if (code) {
        const json = code.data;
        console.log(json);
        const obj = JSON.parse(json);

        console.log(obj.oID);
        console.log(obj.text);
        let numb = obj.oID;

        console.log("Nächste oId: " + this.state.nextMpOId);
        console.log("QR-Code-oId: " + numb);
        console.log("sollte false sein: ");
        console.log(this.state.nextMpOId != numb);
        if(this.state.nextMpOId != numb){
          let oldUserMessage = this.state.userMessage;
          console.log("Ich war leider hier: " + oldUserMessage);
          this.setState({
            userMessage: "Sorry aber das ist nicht der richtige Punkt! " + oldUserMessage
          }); 
        }
        else{
          console.log(this.context.username + ' logged in at location: ' + code.data);
          let url = '/api/race/' + this.state.raceId + '/' +this.context.userId + '/matchPoint/' + numb ;
          fetch(url,{
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
          })
            .then(response => {
  
              console.log(response);
              if(response === null){
                console.log("im the response bad: " + response);
                this.setState({
                  userMessage: "GZ YOU HAVE FINISHED THE ROIC!"
                });
              }
              else{
                console.log("Iwas gerer");
              }
              response.json();
            })          
            .then(body => {
              console.log("im the body:" + body);
              this.setState({
                userMessage: obj.text,
                nextMpOId: this.state.nextMpOId+1
              });
            });
        }


        this.setState({ showQr: false });
        video.srcObject.getTracks().forEach(track => track.stop());
      }
      else if(this.state.showQr) {
        requestAnimationFrame(this.tick);
      }
    }
    else {
      requestAnimationFrame(this.tick);
    }
  }

  handleAbortQrCode() {
    this.setState({ showQr: false });
    const video = this.videoTag.current;
    if(video && video.srcObject) {//avoid issues when calling abort before video has fully initialized
      video.srcObject.getTracks().forEach(track => track.stop());
    }
  }

  render() {
    return (
      <UserContext.Consumer>
        {user => (
          <div>
            <div>
              {!user.username && (<span>If you want to participate in a race - please log in first.</span>)}
              {user.username && (<span>Welcome to the race {this.context.username}</span>)}
              
            </div>
            <div>{<span>Now you have to reach: {this.state.userMessage}</span>}</div>
            <div></div>
            <div>
              {!this.state.showQr && (<Button variant="secondary" onClick={() => this.handleReadQrCode()}>Read QR Code</Button>)}
              {this.state.showQr && (
                <div>
                  <video ref={this.videoTag} width="400" height="400" autoPlay  />
                  <canvas ref={this.canvas} style={{ display: 'none'}} />
                  <Button variant="secondary" onClick={() => this.handleAbortQrCode()}>Abort QR Scan</Button>
                </div>
              )}
            </div>
          </div>
        )
        }
      </UserContext.Consumer>
    );
  }
}

Participant.contextType = UserContext;

export default Participant;
