import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

import RunTableHeader from "./RunTableHeader";
import JoinRun from "./JoinRun";
import SignUpCount from "./SignUpCount";
import AdminOptionsForRun from "./AdminOptionsForRun";
import RunForm from "./RunForm";
import useModal from "./useModal";
import RunTime from "./RunTime";
import MarkerInfoWindowGmapsObj from "./MarkerInfoWindowGmapsObj";

function Runs({ user }) {
  const [runs, setRuns] = useState([]);
  const [runsUserSignedUpFor, setRunsUserSignedUpFor] = useState([]);

  const { isVisible, toggleModal, viewModal } = useModal();
  const [runId, setRunId] = useState();

  let joined = false;
  let currentRunner;

  const getRuns = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/run");
      const data = await response.json();
      setRuns(data);
    } catch (error) {
      console.log(error);
    }
  };

  const getRunsUserSignedUpFor = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/runner/user/${user.userId}`
      );
      const data = await response.json();
      setRunsUserSignedUpFor(data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getRuns();
    getRunsUserSignedUpFor();
  }, [isVisible, user.userId]);

  return (
    <Container>
      <MarkerInfoWindowGmapsObj runs={runs} />
      <h2 className="my-4">Runs</h2>

      <div>
        <Button
          className="btn btn-primary"
          onClick={() => {
            viewModal();
            setRunId(0);
          }}
        >
          Add Run
        </Button>
        <RunForm
          isVisible={isVisible}
          toggleModal={toggleModal}
          runId={runId}
          user={user}
        />
      </div>

      <table className="table">
        <thead>
          <RunTableHeader />
        </thead>
        <tbody>
          {runs.map((run) => (
            <tr key={run.runId}>
              {run.runStatus.status !== "Pending Approval" && (
                <>
                  <RunTime timestamp={run.timestamp} />
                  <td>{run.address}</td>
                  <td>{run.description}</td>
                  <td>{run.club.name}</td>
                  <td>
                    <SignUpCount runId={run.runId} />/{run.maxCapacity}
                  </td>

                  {runsUserSignedUpFor.map((runner) => {
                    if (runner.run.runId === run.runId) {
                      joined = true;
                      currentRunner = runner;
                    }
                  })}

                  <JoinRun joined={joined} run={run} runner={currentRunner} />
                  {(joined = false)}

                  {run.runStatus.status === "Approved" && (
                    <AdminOptionsForRun
                      runId={run.runId}
                      clubId={run.club.clubId}
                      viewModal={viewModal}
                      setRunId={setRunId}
                      user={user}
                    />
                  )}
                  {run.runStatus.status === "Cancelled" && (
                    <td className="btn btn-cancel btn-sm" disabled>
                      CANCELLED
                    </td>
                  )}
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </Container>
  );
}

export default Runs;
