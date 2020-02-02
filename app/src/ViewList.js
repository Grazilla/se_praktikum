import React from 'react';
import { Link } from 'react-router-dom';

class ViewerList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            races: []
        };
    }
    componentDidMount() {
      fetch('/api/race')
        .then(response => response.json())
        .then(body => {
          console.log(body);
          this.setState({
            races: body
          });
        });
    }

    render() {
        return (
            <ul>
                {this.state.races && this.state.races.map((value, index) => 
                    <li key={'race' + value.id}><Link to={"/viewer/" + value.id}>{value.name}</Link></li>
                )}
            </ul>
        );
    }
}

export default ViewerList;