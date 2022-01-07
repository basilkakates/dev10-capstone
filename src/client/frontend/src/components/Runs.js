import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

import RunTableHeader from "./RunTableHeader";
import AddRun from "./AddRun";
import EditRun from "./EditRun";
import CancelRun from "./CancelRun";

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
            <tr key={run.run_id}>
              {run.status !== "pending" && (
                <>
                  <th scope="row">{run.run_id}</th>
                  <td>{run.date}</td>
                  <td>{run.start_time}</td>
                  <td>{run.address}</td>
                  <td>{run.description}</td>
                  <td>{run.club_id}</td>
                  <td>{run.user_id}</td>
                  <td>{run.max_capacity}</td>

                  {run.status === "approved" && (
                    <td>
                      <td className="btn btn-success btn-sm">Sign Up</td>
                      <div>
                        <Button
                          variant="primary"
                          onClick={handleEditModalShow}
                        >
                          Edit
                        </Button>
                        <EditRun
                          showModal={showEditModal}
                          closeModal={handleEditModalClose}
                          runId={run.run_id}
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
                          runId={run.run_id}
                        />
                      </div>
                    </td>
                  )}
                  {run.status === "canceled" && (
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
