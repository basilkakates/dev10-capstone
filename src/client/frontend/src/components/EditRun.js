import { useState, useEffect } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

import Errors from "./Errors";

function EditRun({ showModal, closeModal, runId }) {
  const [date, setDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [address, setAddress] = useState("");
  const [description, setDescription] = useState("");
  const [clubId, setClubId] = useState("");
  const [userId, setUserId] = useState("");
  const [maxCapacity, setMaxCapacity] = useState([]);
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const dateOnChangeHandler = (event) => {
    setDate(event.target.value);
  };

  const startTimeOnChangeHandler = (event) => {
    setStartTime(event.target.value);
  };

  const addressOnChangeHandler = (event) => {
    setAddress(event.target.value);
  };

  const descriptionOnChangeHandler = (event) => {
    setDescription(event.target.value);
  };

  const maxCapacityOnChangeHandler = (event) => {
    setMaxCapacity(event.target.value);
  };

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
        setMaxCapacity(data.maxCapacity);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [runId]);

  const editRunFormSubmitHandler = (event) => {
    event.preventDefault();

    const updatedRun = {
      runId: runId,
      date,
      startTime,
      address,
      description,
      clubId,
      userId,
      maxCapacity,
      status: "approved",
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(updatedRun),
    };

    fetch(`http://localhost:8080/run/${updatedRun.runId}`, init)
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
        <Modal.Title>Edit Run</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Errors errors={errors} />
        <form onSubmit={editRunFormSubmitHandler}>
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
                    onChange={dateOnChangeHandler}
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
                    onChange={startTimeOnChangeHandler}
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
                    onChange={addressOnChangeHandler}
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
                    onChange={descriptionOnChangeHandler}
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
                    onChange={maxCapacityOnChangeHandler}
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

export default EditRun;
