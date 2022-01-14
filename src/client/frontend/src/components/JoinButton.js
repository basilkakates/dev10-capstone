import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";

function JoinButton({ joined, runner, run, user }) {
  const [errors, setErrors] = useState([]);
  const [runsUserSignedUpFor, setRunsUserSignedUpFor] = useState([]);
  const [isJoined, setIsJoined] = useState(joined);
  const history = useHistory();

  //   const getUser = () => {
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
  };

  useEffect(() => {
    async function getRunsUserSignedUpFor() {
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
    }
    getRunsUserSignedUpFor();
  });

  const getIsJoined = () => {
    let joined = false;
    runsUserSignedUpFor.map((runner) => {
      if (runner.run.runId === run.runId) {
        joined = true;
      }
    });
    return joined;
  };

  return (
    runsUserSignedUpFor && (
      <>
        {getIsJoined() ? (
          <button onClick={dropRun}>Joined</button>
        ) : (
          <button onClick={joinRun}>Join</button>
        )}
      </>
    )
  );
}

export default JoinButton;
