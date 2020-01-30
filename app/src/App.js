import React, { Component } from 'react';
import Home from './Home';
import Test from './Test';
import Participant from './Participant';
import Viewer from './Viewer';
import ScanCode from './ScanCode';

import { BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import './App.css';


class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path="/participant" exact={true} component={Participant}/>
          <Route path="/viewer" exact={true} component={Viewer}/>
          <Route path="/test" exact={true} component={Test}/>
          <Route path="/scancode" exact={true} component={ScanCode}/>
          
        </Switch>
      </Router>
    )
  }
}

export default App;