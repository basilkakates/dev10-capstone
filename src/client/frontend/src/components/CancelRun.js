import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

import Errors from "./Errors";

function CancelRun({ showModal, closeModal, runId }) {
  const [run, setRun] = useState("");
  const [errors, setErrors] = useState([]);

  // const { run_id } = useParams();
  const history = useHistory();

  useEffect(() => {
    fetch(`http://localhost:8080/api/run/${runId}`)
      .then((response) => {
        if (response.status === 404) {
          return Promise.reject(`Received 404 Not Found for Run ID: ${runId}`);
        }
        return response.json();
      })
      .then((data) => {
        setRun(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [runId]);

  const cancelRunFormSubmitHandler = (event) => {
    event.preventDefault();

    const updatedRun = {
      runId: run.runId,
      date: run.date,
      address: run.address,
      description: run.description,
      maxCapacity: run.maxCapacity,
      startTime: run.startTime,
      latitude: run.latitude,
      longitude: run.longitude,
      club: run.club,
      user: run.user,
      user: run.user,
      club: run.club,
      runStatus: {
        runStatusId: 3,
        status: "Cancelled",
      },
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(updatedRun),
    };

    fetch(`http://localhost:8080/api/run/${updatedRun.runId}`, init)
      .then((response) => {
        if (response.status === 204) {
          return null;
        } else if (response.status === 400) {
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

  return (
    <Modal show={showModal} onHide={closeModal}>
      <Modal.Header closeButton>
        <Modal.Title>Cancel Run</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Errors errors={errors} />
        <form onSubmit={cancelRunFormSubmitHandler}>
          <table className="table">
            <tbody>
              <tr>
                <td>Date: </td>
                <td>
                  <input
                    type="text"
                    id="date"
                    name="date"
                    value={run.date}
                    readOnly
                  />
                </td>
              </tr>
              <tr>
                <td>Start Time: </td>
                <td>
                  <input
                    type="text"
                    id="startTime"
                    name="startTime"
                    value={run.startTime}
                    readOnly
                  />
                </td>
              </tr>
              <tr>
                <td>Address: </td>
                <td>
                  <input
                    type="text"
                    id="address"
                    name="address"
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
                    value={run.maxCapacity}
                    readOnly
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <div className="mt-5">
            <Button variant="primary" onClick={closeModal} type="submit">
              <i className="bi bi-plus-circle-fill"></i> Submit
            </Button>
            <Button variant="secondary" onClick={closeModal}>
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
