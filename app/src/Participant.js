import React from 'react';
//import './participate.css';
import { UserContext } from './UserContext';
import Button from 'react-bootstrap/Button';
import jsQR from 'jsqr';

class Participant extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showQr: false
    };

    this.videoTag = React.createRef();
    this.canvas = React.createRef();

    this.tick = this.tick.bind(this);
  }

  componentDidMount() {
    if (navigator.geolocation) {
      this.geoWatcher = navigator.geolocation.watchPosition((pos) => this.positionRegistered(pos), null, { enableHighAccuracy: true, maximumAge: 10000 });
    }
    else {
      alert("Your browser does not support geolocation. Unfortunately you can't participate.");
    }
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

      var code = jsQR(imageData.data, imageData.width, imageData.height, { inversionAttempts: "dontInvert" });

      if (code) {
        //TODO send reached waypoint to server
        console.log(this.context.username + ' logged in at location: ' + code.data);
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
