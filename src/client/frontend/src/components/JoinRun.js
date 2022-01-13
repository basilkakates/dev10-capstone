import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import Container from "react-bootstrap/Container";

import JoinButton from "./JoinButton";

function JoinRun({ run, user }) {
  const [runsUserSignedUpFor, setRunsUserSignedUpFor] = useState([]);

  const history = useHistory();
  let joined = false;
  let currentRunner = [];

  // const getUser = () => {
  //   fetch("http://localhost:8080/api/user/1")
  //     .then((response) => {
  //       if (response.status !== 200) {
  //         return Promise.reject("user fetch failed");
  //       }
  //       return response.json();
  //     })
  //     .then((json) => setUser(json))
  //     .catch(console.log);
  // };

  const getRunsUserSignedUpFor = async () => {
    if (user.userId) {
      try {
        const response = await fetch(
          `http://localhost:8080/api/runner/user/${user.userId}`
        );
        const data = await response.json();
        setRunsUserSignedUpFor(data);
      } catch (error) {
        console.log(error);
      }
    }
  };

  useEffect(() => {
    getRunsUserSignedUpFor();
  }, []);

  return (
    <>
      {runsUserSignedUpFor.map((runner) => {
        if (runner.run.runId === run.runId) {
          joined = true;
          currentRunner = runner;
        }
      })}
      <JoinButton
        joined={joined}
        runner={currentRunner}
        run={run}
        user={user}
      />
    </>
  );
}

export default JoinRun;
