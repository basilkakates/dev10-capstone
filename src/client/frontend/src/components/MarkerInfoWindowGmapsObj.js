import React from "react";
import isEmpty from "lodash.isempty";
import { useState, useEffect } from "react";
import GoogleMapReact from "google-map-react";

const getInfoWindowString = (run) => `
    <div>
      <div style="font-size: 16px;">
        ${run.timestamp.date}
      </div>
    <div style="font-size: 16px;">
    Address: ${run.address}
  </div>
  <div style="font-size: 16px;">
  Description: ${run.description}
</div>`;

// Refer to https://github.com/google-map-react/google-map-react#use-google-maps-api
const handleApiLoaded = (map, maps, runs) => {
  const markers = [];
  const infowindows = [];

  runs.forEach((run) => {
    if (run.runStatus.status !== "Pending Approval") {
      markers.push(
        new maps.Marker({
          position: {
            lat: run.latitude,
            lng: run.longitude,
          },
          map,
        })
      );

      infowindows.push(
        new maps.InfoWindow({
          // content: `<div></div>`,
          content: getInfoWindowString(run),
        })
      );
    }
  });

  markers.forEach((marker, i) => {
    marker.addListener("click", () => {
      infowindows[i].open(map, marker);
    });
  });
};

function MarkerInfoWindowGmapsObj({ runs }) {
  const [runMarkers, setRunMarkers] = useState([]);

  useEffect(() => {
    setRunMarkers(runs);
  }, [runs]);

  return (
    <>
      {!isEmpty(runs) && (
        <div className="map">
          <h2 className="map-h2"style={{color: "white", background: "#375d83", width: "270px", height: "42px", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Scheduled Runs&nbsp;</h2>

          <div className="google-map" style={{ height: "50vh", width: "100%" }}>
            <GoogleMapReact
              defaultZoom={10}
              defaultCenter={{ lat: 41.8781, lng: -87.6298 }}
              bootstrapURLKeys={{
                key: "",
              }}
              yesIWantToUseGoogleMapApiInternals
              onGoogleApiLoaded={({ map, maps }) =>
                handleApiLoaded(map, maps, runs)
              }
            />
          </div>
        </div>
      )}
    </>
  );
}

export default MarkerInfoWindowGmapsObj;
