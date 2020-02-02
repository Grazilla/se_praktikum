import React from 'react';
import Home from './Home';
import ViewList from './ViewList';
import Participant from './Participant';
import Viewer from './Viewer';
import { UserContext } from './UserContext';
import Header from './Header';

import { BrowserRouter as Router, Route, Switch} from 'react-router-dom';



class App extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
        username: 'Lukas',
        userId: 1,
        loginUser: (name, id) => this.setState({username: name, userId: id}),
        logoutUser: () => this.setState({username: null, userId: null})
    };
  }

  render() {
    return (
      <UserContext.Provider value={this.state}>
        <Router>
          <div>
            <Header />
            <Switch>
              <Route path='/' exact={true} component={Home}/>
              <Route path="/participant" exact={true} component={Participant}/>
              <Route path="/viewer/:raceId" exact={true} component={Viewer}/>
              <Route path="/viewer" exact={true} component={ViewList}/>
            </Switch>
          </div>
        </Router>
      </UserContext.Provider>
    )
  }
}

export default App;