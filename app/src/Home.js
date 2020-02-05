import React from 'react';
import './Home.css';
import Rois from "./Rois.jpeg";

function Home() {
  return (
    <div className="container">
      <div className="row">
        <div className="col">&nbsp;</div>
        <div className="col-md-10 col-lg-8 col-sm-12">
          <h5>Welcome to GPS Race</h5>
          <h6>The most exciting race at JKU (aside from the Pondrace...)</h6>
          <p>
            Implemented as part of the SE practicum by:
          </p>
          <ul>
            <li>Lukas Grüneis</li>
            <li>Lisa Kammerer</li>
            <li>Sandra Pühringer</li>
            <li>Sara Scheucher</li>
            <p></p>
            <img src={Rois} width="200" height="200" alt="bild" />
          </ul>
        </div>
        <div className="col">&nbsp;</div>
      </div>
    </div>
  );
}

export default Home;
