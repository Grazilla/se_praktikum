import React from 'react';
import './Home.css';

function Home() {
  return (
    <div className="container">
      <div className="row">
        <div className="col">&nbsp;</div>
        <div className="col-md-10 col-lg-8 col-sm-12">
          <h5>Welcome to GPS Race</h5>
          <h6>the most exciting race at JKU (aside from the Pondrace...)</h6>
          <p>
            Implemented as part of the SE practicum by:
          </p>
          <ul>
            <li>Someone</li>
            <li>Someone else</li>
            <li>Us</li>
          </ul>
        </div>
        <div className="col">&nbsp;</div>
      </div>
    </div>
  );
}

export default Home;
