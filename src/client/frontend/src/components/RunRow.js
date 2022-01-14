import { useEffect, useState } from "react";
import RunTime from "./RunTime";
import AdminOptionsForRun from "./AdminOptionsForRun";
import Container from "react-bootstrap/esm/Container";
import Button from "react-bootstrap/esm/Button";

function RunRow({ run, user, viewModal, setRunId }) {
  const [runsUserSignedUpFor, setRunsUserSignedUpFor] = useState([]);
  const [runnerEntriesForUser, setRunnerEntriesForUser] = useState([]);
  const [usersSignedUpForRun, setUsersSignedUpForRun] = useState([]);

  const getRunsUserSignedUpFor = async () => {
    try {
      if (user.userId) {
        const response = await fetch(
          `http://localhost:8080/api/runner/user/${user.userId}`
        );
        const data = await response.json();
        setRunnerEntriesForUser(data);
        setRunsUserSignedUpFor(data.flatMap((runner) => [runner.run.runId]));
      }
    } catch (error) {
      console.log(error);
    }
  };

  const getUsersSignedUpForRun = async (runId) => {
    try {
      if (runId) {
        const response = await fetch(
          `http://localhost:8080/api/runner/run/${runId}`
        );
        const data = await response.json();

        if (response.status !== 200) {
          throw new Error(`Response is not 200 OK: ${data}`);
        } else {
          setUsersSignedUpForRun(data);
        }
      }
    } catch (error) {
      console.log(error);
    }
  };

  const joinRun = async (run) => {
    try {
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
      const response = await fetch(`http://localhost:8080/api/runner`, init);
      const data = await response.json();
      if (response.status !== 201) {
        throw new Error(`Response is not 201 Created: ${data}`);
      } else if (response.status === 201) {
        setRunnerEntriesForUser(runnerEntriesForUser.concat(data));
        setRunsUserSignedUpFor(runsUserSignedUpFor.concat(data.run.runId));
        getUsersSignedUpForRun(run.runId);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const dropRun = async (runId) => {
    const runnerId = runnerEntriesForUser.filter(
      (runner) => runner.run.runId === runId
    )[0][`runnerId`];
    try {
      const response = await fetch(
        `http://localhost:8080/api/runner/${runnerId}`,
        { method: "DELETE" }
      );
      if (response.status === 204) {
        setRunnerEntriesForUser(
          runnerEntriesForUser.filter((runner) => runner.run.runId !== runId)
        );
        setRunsUserSignedUpFor(
          runsUserSignedUpFor.filter((runId2) => runId2 !== runId)
        );
        getUsersSignedUpForRun(run.runId);
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getRunsUserSignedUpFor();
    getUsersSignedUpForRun(run.runId);
  }, [run, user]);

  return (
    <tr key={run.runId} scope="row">
      {run.runStatus.status !== "Pending Approval" && (
        <>
          <RunTime timestamp={run.timestamp} />
          <td>{run.address}</td>
          <td>{run.description}</td>
          <td>{run.club.name}</td>
          <td>
            {usersSignedUpForRun.length}/{run.maxCapacity}
          </td>
          <td>
            <Container>
              {run.runStatus.status === "Approved" && (
                <>
                  {user.userId ? (
                    <>
                      {runsUserSignedUpFor.includes(run.runId) ? (
                        <Button
                          className="btn btn-primary"
                          onClick={() => {
                            dropRun(run.runId);
                          }}
                        >
                          Joined
                        </Button>
                      ) : (
                        <Button
                          className="btn btn-primary"
                          onClick={() => {
                            joinRun(run);
                          }}
                        >
                          Join
                        </Button>
                      )}
                    </>
                  ) : (
                    <Button className="btn btn-primary">SCHEDULED</Button>
                  )}
                </>
              )}
              {run.runStatus.status === "Cancelled" && (
                <Button className="btn btn-danger">CANCELLED</Button>
              )}
            </Container>
          </td>
          <td>
            <AdminOptionsForRun
              viewModal={viewModal}
              setRunId={setRunId}
              user={user}
              run={run}
            />
          </td>
        </>
      )}
    </tr>
  );
}

export default RunRow;
