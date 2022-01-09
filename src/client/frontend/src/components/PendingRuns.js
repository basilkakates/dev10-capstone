import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

import RunTableHeader from "./RunTableHeader";
import ApproveRun from "./ApproveRun";
import DeleteRun from "./DeleteRun";

function PendingRuns() {
  const [runs, setRuns] = useState([]);
  const [showApproveModal, setShowApproveModal] = useState(false);
  const [showDeclineModal, setShowDeclineModal] = useState(false);

  const handleApproveModalClose = () => setShowApproveModal(false);
  const handleApproveModalShow = () => setShowApproveModal(true);

  const handleDeclineModalClose = () => setShowDeclineModal(false);
  const handleDeclineModalShow = () => setShowDeclineModal(true);

  const getRuns = () => {
    fetch("http://localhost:8080/run")
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

  const runDeleteClickHandler = (runId) => {
    const init = {
      method: "DELETE",
    };

    fetch(`http://localhost:8080/run/${runId}`, init)
      .then((response) => {
        if (response.status === 204) {
          getRuns();
        } else if (response.status === 404) {
          Promise.reject(`Run ID ${runId} not found`);
        } else {
          Promise.reject("Something unexpected went wrong :)");
        }
      })
      .catch((error) => console.log(error));
  };

  return (
    <Container>
      <h2 className="my-4">Pending Runs</h2>

      <table className="table">
        <thead>
          <RunTableHeader />
        </thead>
        <tbody>
          {runs.map((run) => (
            <tr key={run.runId}>
              {run.status === "pending" && (
                <>
                  <th scrope="row">{run.date}</th>
                  <td>{run.startTime}</td>
                  <td>{run.address}</td>
                  <td>{run.description}</td>
                  <td>{run.clubid}</td>
                  <td>{run.maxCapacity}</td>
                  <td>
                    <div>
                      <Button
                        variant="primary"
                        onClick={handleApproveModalShow}
                      >
                        Approve
                      </Button>
                      <ApproveRun
                        showModal={showApproveModal}
                        closeModal={handleApproveModalClose}
                        runId={run.runId}
                      />
                    </div>
                    <div>
                      <Button
                        variant="secondary"
                        onClick={handleDeclineModalShow}
                      >
                        Decline
                      </Button>
                      <DeleteRun
                        showModal={showDeclineModal}
                        closeModal={handleDeclineModalClose}
                        runId={run.runId}
                      />
                    </div>
                  </td>
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </Container>
  );
}

export default PendingRuns;
