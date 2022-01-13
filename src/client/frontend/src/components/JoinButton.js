import React, { useState } from "react";
import { useHistory } from "react-router-dom";

function JoinButton({ joined, runner, run, user }) {
  const [errors, setErrors] = useState([]);
  const history = useHistory();

  const joinRun = (event) => {
    event.preventDefault();

    const newRunner = {
      run: run,
      user: user,
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(newRunner),
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

  return (
    <>
      {joined === true ? (
        <button onClick={dropRun}>Joined</button>
      ) : (
        <button onClick={joinRun}>Join</button>
      )}
    </>
  );
}

export default JoinButton;
