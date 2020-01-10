import React, { Component } from 'react';
import Home from './Home';
import Test from './Test';


import { BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import './App.css';


class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>

          <Route path="/test" exact={true} component={Test}/>
        </Switch>
      </Router>
    )
  }
}

export default App;