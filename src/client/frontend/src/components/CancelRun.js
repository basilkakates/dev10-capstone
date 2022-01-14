import { useState, useEffect } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

function CancelRun({ showModal, closeModal, run }) {
  const [deleteStatus, setDeleteStatus] = useState({});

  const getCancelledRunStatus = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/runStatus/status/cancelled`
      );
      const data = await response.json();
      if (response.status !== 200) {
        throw new Error(`Response is not 200 OK: ${data}`);
      } else {
        setDeleteStatus(data);
      }
    } catch (errors) {
      console.log(errors);
    }
  };

  const cancelRunFormSubmitHandler = async (event) => {
    event.preventDefault();

    const updatedRun = { ...run };
    updatedRun.runStatus = deleteStatus;

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(updatedRun),
    };

    try {
      const response = await fetch(
        `http://localhost:8080/api/run/${updatedRun.runId}`,
        init
      );
      const data = await response.json();
      if (response.status !== 204) {
        throw new Error(`Response is not 204 No Content: ${data}`);
      } else {
      }
    } catch (errors) {
      console.log(errors);
    }
  };

  useEffect(() => {
    getCancelledRunStatus();
  }, []);

  return (
    <Modal show={showModal} onHide={closeModal}>
      <Modal.Header closeButton>
        <Modal.Title>Cancel Run</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={cancelRunFormSubmitHandler}>
          <table className="table">
            <tbody>
              <tr>
                <td>{`Date & Time:`}</td>
                <td>
                  <input
                    id="timestamp"
                    name="timestamp"
                    className="form-control"
                    readOnly
                    value={
                      new Date(run.timestamp).toDateString() +
                      " " +
                      new Date(run.timestamp).toLocaleTimeString()
                    }
                  />
                </td>
              </tr>
              <tr>
                <td>Address: </td>
                <td>
                  <input
                    id="address"
                    name="address"
                    className="form-control"
                    value={run.address}
                    readOnly
                  />
                </td>
              </tr>
              <tr>
                <td>Description: </td>
                <td>
                  <input
                    type="text"
                    id="description"
                    name="description"
                    className="form-control"
                    value={run.description}
                    readOnly
                  />
                </td>
              </tr>
              <tr>
                <td>Max Capacity: </td>
                <td>
                  <input
                    type="text"
                    id="maxCapacity"
                    name="maxCapacity"
                    className="form-control"
                    value={run.maxCapacity}
                    readOnly
                  />
                </td>
              </tr>
              <tr>
                <td>Club: </td>
                <td>
                  <input
                    type="text"
                    id="club"
                    name="club"
                    className="form-control"
                    value={run.club.name}
                    readOnly
                  />
                </td>
              </tr>
              <tr>
                <td>User: </td>
                <td>
                  <input
                    type="text"
                    id="user"
                    name="user"
                    className="form-control"
                    value={run.user.firstName + " " + run.user.lastName}
                    readOnly
                  />
                </td>
              </tr>
              <tr>
                <td>Run Status: </td>
                <td>
                  <input
                    type="text"
                    id="runStatus"
                    name="runStatus"
                    className="form-control"
                    value={run.runStatus.status}
                    readOnly
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <div className="mt-5">
            <Button
              className="btn btn-primary"
              onClick={closeModal}
              type="submit"
            >
              <i className="bi bi-plus-circle-fill"></i> Submit
            </Button>
            <Button className="btn btn-secondary" onClick={closeModal}>
              <i className="bi bi-x"></i> Go Back
            </Button>
          </div>
        </form>
      </Modal.Body>
      <Modal.Footer></Modal.Footer>
    </Modal>
  );
}

export default CancelRun;
