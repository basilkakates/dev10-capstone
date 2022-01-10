import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

function JoinRun({ runId }) {
  const [runsUserSignedUpFor, setRunsUserSignedUpFor] = useState([]);

  const getRunsUserSignedUpFor = () => {
    fetch("http://localhost:8080/api/runner/user/1")
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setRunsUserSignedUpFor(json))
      .catch(console.log);
  };

  const joinRun = () => {};
  const dropRun = (event) => {
      
  };

  useEffect(() => {
    getRunsUserSignedUpFor();
  }, []);

  return (
    <Container>
      {runsUserSignedUpFor.map((runUserSignedUpFor) => (
        <>
          {runUserSignedUpFor.run.runId === runId ? (
            <Button onClick={dropRun}>Joined</Button>
          ) : (
            <Button onClick={joinRun}>Join</Button>
          )}
        </>
      ))}
    </Container>
  );
}

export default JoinRun;
