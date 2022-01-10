import { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";

import RunTableHeader from "./RunTableHeader";
import ShowPendingRun from "./ShowPendingRun";

function PendingRuns() {
  const [runs, setRuns] = useState([]);

  const getRuns = () => {
    fetch("http://localhost:8080/api/run")
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setRuns(json))
      .catch(console.log);
  };

  useEffect(() => {
    getRuns();
  }, []);

  return (
    <Container>
      <h2 className="my-4">Pending Runs</h2>

      <table className="table">
        <thead>
          <RunTableHeader />
        </thead>
        <tbody>
          {runs.map((run) => (
                <ShowPendingRun run={run} />
              )

          )}
        </tbody>
      </table>
    </Container>
  );
}

export default PendingRuns;
