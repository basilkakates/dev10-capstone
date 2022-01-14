import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

import RunTableHeader from "./RunTableHeader";
import RunForm from "./RunForm";
import useModal from "./useModal";
import MarkerInfoWindowGmapsObj from "./MarkerInfoWindowGmapsObj";
import RunRow from "./RunRow";

function Runs({ user }) {
  const [runs, setRuns] = useState([]);
  const { isVisible, toggleModal, viewModal } = useModal();
  const [runId, setRunId] = useState();

  const getRuns = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/run");
      const data = await response.json();
      setRuns(data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getRuns();
  }, [isVisible, user]);

  return (
    <Container>
      <MarkerInfoWindowGmapsObj runs={runs} />
      <h2 className="my-4"></h2>

      <div>
        {user.userId ? (
          <Button
            className="btn btn-primary"
            onClick={() => {
              viewModal();
              setRunId(0);
            }}
          >
            Add Run
          </Button>
        ) : null}
        <h2 className="my-4"></h2>
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
              <RunRow
                user={user}
                run={run}
                viewModal={viewModal}
                setRunId={setRunId}
              />
          ))}
        </tbody>
      </table>
    </Container>
  );
}

export default Runs;
