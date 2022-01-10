import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

import RunTableHeader from "./RunTableHeader";
import AddRun from "./AddRun";
import JoinRun from "./JoinRun";
import SignUpCount from "./SignUpCount";
import AdminOptionsForRun from "./AdminOptionsForRun";

function Runs() {
  const [runs, setRuns] = useState([]);
  const [showAddModal, setShowAddModal] = useState(false);

  const handleAddModalClose = () => setShowAddModal(false);
  const handleAddModalShow = () => setShowAddModal(true);

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
  }, []);

  return (
    <Container>
      <h2 className="my-4">Runs</h2>

      <div>
        <Button variant="primary" onClick={handleAddModalShow}>
          Add Run
        </Button>
        <AddRun showModal={showAddModal} closeModal={handleAddModalClose} />
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
                  <th scope="row">{run.date}</th>
                  <td>{run.startTime}</td>
                  <td>{run.address}</td>
                  <td>{run.description}</td>
                  <td>{run.clubId}</td>
                  <td>
                    <SignUpCount runId={run.runId} />/{run.maxCapacity}
                  </td>

                  <JoinRun runId={run.runId} />

                  {run.runStatus.status === "Approved" && (
                    <AdminOptionsForRun
                      runId={run.runId}
                      clubId={run.club.clubId}
                    />
                  )}
                  {run.runStatus.status === "Cancelled" && (
                    <td className="btn btn-outline-danger btn-sm" disabled>
                      CANCELED
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
