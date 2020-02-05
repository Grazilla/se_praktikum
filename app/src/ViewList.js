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
            <div class="jumbotron jumbotron-fluid">
                <div class="container">
                    <h1 class ="display-4" > <img src="https://freesvg.org/img/DontDrinkDrive.png" width="75" height="75" alt="Bild"></img> Current Races <img src="https://png2.kisspng.com/sh/41236d40ebb52569c316adbae64acb75/L0KzQYm3UsA1N5dufZH0aYP2gLBuTgJia5pzf59vbHHqg37qjPlxNZJ3jJ97YXPoPcH1h71ncZ1qRadqN0fmc7O8WfE5PJUARqQ8OEm6RIGBUcUyP2k1SKg7OUa2Qoq1kP5o/kisspng-racing-flags-clip-art-race-png-file-5a77ccb59a84d9.2389740815178006296329.png" width="75" height="75" alt="Bild"></img></h1>
                    <p></p>
                        <p class="lead">
                            <ul>
                                {this.state.races.map((value, index) => 
                                <li key={'race' + value.id}> 
                                    <p></p>
                                    <Link to={"/viewer/" + value.id} ><font size="6">{value.name}</font> </Link></li>
                    )}
                            </ul>
                        </p>
                    </div>
                </div>

                );
        
    }
}

export default ViewerList;