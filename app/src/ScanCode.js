import React, { Component } from 'react'
import QrReader from 'react-qr-reader'
 
class ScanCode extends Component {
  state = {
    result: 'No result'
  }
 
  handleScan = data => {
    if (data) {
      this.setState({
        result: data
      });
      this.sendScan();
    }

  }

  async sendScan() {
    var payload = {
      id: null,
      name: this.state.result
    };
    const response = await fetch('/api/race/' + this.state.selectedRaceId + '/login', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    });

    console.log(response);

  }

  
  handleError = err => {
    console.error(err)
  }
  
  render() {
    return (
      <div style={{ alignContent:'center'}}>
        <QrReader
          delay={300}
          onError={this.handleError}
          onScan={this.handleScan}
          style={{ width: '50%'}}
        />
        <p>{this.state.result}</p>
      </div>
    )
  }
}

export default ScanCode;