import React, { Component } from "react";
import isEmpty from "lodash.isempty";
import { useState, useEffect } from "react";
import GoogleMapReact from "google-map-react";

const getInfoWindowString = (place) => `
    <div>
      <div style="font-size: 16px;">
        ${place.name}
      </div>
      <div style="font-size: 14px;">
        <span style="color: grey;">
        ${place.rating}
        </span>
        <span style="color: orange;">${String.fromCharCode(9733).repeat(
          Math.floor(place.rating)
        )}</span><span style="color: lightgrey;">${String.fromCharCode(
  9733
).repeat(5 - Math.floor(place.rating))}</span>
      </div>
      <div style="font-size: 14px; color: grey;">
        ${place.types[0]}
      </div>
      <div style="font-size: 14px; color: grey;">
        ${"$".repeat(place.price_level)}
      </div>
      <div style="font-size: 14px; color: green;">
        ${place.opening_hours.open_now ? "Open" : "Closed"}
      </div>
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
          content: `<div></div>`,
          // content: getInfoWindowString(place),
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
  }, [runMarkers]);

  return (
    <>
      {!isEmpty(runs) && (
        <div className="map">
          <h2 className="map-h2">Scheduled Runs</h2>

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
