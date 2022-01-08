import { useState } from "react";
import { Link, useHistory } from "react-router-dom";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

import Errors from "./Errors";

function AddRun({ showModal, closeModal }) {
  const [date, setDate] = useState("");
  const [start_time, setStartTime] = useState("");
  const [address, setAddress] = useState("");
  const [description, setDescription] = useState("");
  const [club_id, setClubId] = useState("");
  const [user_id, setUserId] = useState("");
  const [max_capacity, setMaxCapacity] = useState([]);
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

  const addRunFormSubmitHandler = (event) => {
    event.preventDefault();

    const run = {
      run_id: 0,
      date,
      start_time,
      address,
      description,
      club_id,
      user_id,
      max_capacity,
      status: "pending",
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(run),
    };

    fetch("http://localhost:8080/run", init)
      .then((response) => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        }
        return Promise.reject("Something unexpected went wrong :)");
      })
      .then((data) => {
        // TODO: This needs to be changed to run_id. The test api required it to be id.
        if (data.id) {
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
        <Modal.Title>Add Run</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Errors errors={errors} />
        <form onSubmit={addRunFormSubmitHandler}>
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
                    id="start_time"
                    name="start_time"
                    value={start_time}
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
                    value={max_capacity}
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

export default AddRun;