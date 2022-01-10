import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

import RunTableHeader from "./RunTableHeader";
import AddRun from "./AddRun";
import EditRun from "./EditRun";
import CancelRun from "./CancelRun";
import JoinRun from "./JoinRun";
import SignUpCount from "./SignUpCount";

function Runs() {
  const [runs, setRuns] = useState([]);

  const [showAddModal, setShowAddModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);

  const handleAddModalClose = () => setShowAddModal(false);
  const handleAddModalShow = () => setShowAddModal(true);

  const handleEditModalClose = () => setShowEditModal(false);
  const handleEditModalShow = () => setShowEditModal(true);

  const handleCancelModalClose = () => setShowCancelModal(false);
  const handleCancelModalShow = () => setShowCancelModal(true);

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
              {run.runStatus.runStatusId !== 1 && (
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

                  {run.runStatus.runStatusId === 2 && (
                    <td>
                      <div>
                        <Button variant="primary" onClick={handleEditModalShow}>
                          Edit
                        </Button>

                        <EditRun
                          showModal={showEditModal}
                          closeModal={handleEditModalClose}
                          runId={run.runId}
                        />
                      </div>
                      <div>
                        <Button
                          variant="secondary"
                          onClick={handleCancelModalShow}
                        >
                          Cancel
                        </Button>
                        <CancelRun
                          showModal={showCancelModal}
                          closeModal={handleCancelModalClose}
                          runId={run.runId}
                        />
                      </div>
                    </td>
                  )}
                  {run.runStatus.runStatusId === 3 && (
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
