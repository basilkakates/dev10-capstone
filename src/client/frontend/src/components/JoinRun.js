import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

function JoinRun({ joined, run, runner }) {
  const [user, setUser] = useState([]);
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const getUser = () => {
    fetch("http://localhost:8080/api/user/1")
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("user fetch failed");
        }
        return response.json();
      })
      .then((json) => setUser(json))
      .catch(console.log);
  };

  const joinRun = (event) => {
    event.preventDefault();

    const runner = {
      run: run,
      user: user,
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(runner),
    };

    fetch("http://localhost:8080/api/runner", init)
      .then((response) => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        }
        return Promise.reject("Something unexpected went wrong :)");
      })
      .then((data) => {
        if (data.runnerId) {
          history.push("/runs");
        } else {
          setErrors(data);
        }
      })
      .catch((error) => console.log(error));

    window.location.reload(false);
  };

  const dropRun = (event) => {
    event.preventDefault();

    const runnerToDelete = {
      runnerId: runner.runnerId,
    };

    const init = {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(runnerToDelete),
    };

    fetch(`http://localhost:8080/api/runner/${runner.runnerId}`, init)
      .then((response) => {
        if (response.status === 204) {
          return null;
        } else if (response.status === 404) {
          return response.json();
        }
        return Promise.reject("Something unexpected went wrong :)");
      })
      .then((data) => {
        if (!data) {
          history.push("/runs");
        } else {
          setErrors(data);
        }
      })
      .catch((error) => console.log(error));

    window.location.reload(false);
  };

  useEffect(() => {
    getUser();
  }, []);

  return (
    <Container>
      {joined === true ? (
        <Button onClick={dropRun}>Joined</Button>
      ) : (
        <Button onClick={joinRun}>Join</Button>
      )}
    </Container>
  );
}

export default JoinRun;
