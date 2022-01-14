import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

import Errors from "./Errors";

function DeleteRun({ showModal, closeModal, run }) {
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const deleteRunFormSubmitHandler = (event) => {
    event.preventDefault();

    const init = {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(run),
    };

    fetch(`http://localhost:8080/api/run/${run.runId}`, init)
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
