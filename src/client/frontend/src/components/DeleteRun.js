import { useState, useEffect } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

import Errors from "./Errors";

function DeleteRun({ showModal, closeModal, runId }) {
  const [date, setDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [address, setAddress] = useState("");
  const [description, setDescription] = useState("");
  const [clubId, setClubId] = useState("");
  const [userId, setUserId] = useState("");
  const [maxCapacity, setMaxCapacity] = useState([]);
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  useEffect(() => {
    fetch(`http://localhost:8080/run/${runId}`)
      .then((response) => {
        if (response.status === 404) {
          return Promise.reject(`Received 404 Not Found for Run ID: ${runId}`);
        }
        return response.json();
      })
      .then((data) => {
        setDate(data.date);
        setStartTime(data.startTime);
        setAddress(data.address);
        setDescription(data.description);
        setClubId(data.clubId);
        setUserId(data.userId);
        setMaxCapacity(data.maxCapacity);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [runId]);

  const deleteRunFormSubmitHandler = (event) => {
    event.preventDefault();

    const run = {
      runId: runId,
    };

    const init = {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(run),
    };

    fetch(`http://localhost:8080/run/${run.runId}`, init)
      .then((response) => {
        if (response.status === 204) {
          return null;
        } else if (response.status === 404) {
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
        <Modal.Title>Decline Run</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Errors errors={errors} />
        <form onSubmit={deleteRunFormSubmitHandler}>
          <table className="table">
            <tbody>
              <tr>
                <td>Date: </td>
                <td>
                  <input
                    type="text"
                    id="date"
                    name="date"
                    value={date}
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
                    value={startTime}
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
                    value={address}
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
                    value={description}
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
                    value={maxCapacity}
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

export default DeleteRun;
