import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import "react-time-picker/dist/TimePicker.css";

import Errors from "./Errors";

function ApproveRun({ showModal, closeModal, runId }) {
  const [run, setRun] = useState("");
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  useEffect(() => {
    fetch(`http://localhost:8080/api/run/${runId}`)
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setRun(json))
      .catch(console.log);
  }, [runId]);

  const approveRunFormSubmitHandler = (event) => {
    event.preventDefault();

    const updatedRun = {
      runId: run.runId,
      timestamp: run.timestamp,
      address: run.address,
      description: run.description,
      maxCapacity: run.maxCapacity,
      latitude: run.latitude,
      longitude: run.longitude,
      club: run.club,
      user: run.user,
      user: run.user,
      club: run.club,
      runStatus: {
        runStatusId: 2,
        status: "Approved",
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

    fetch(`http://localhost:8080/api/run/${run.runId}`, init)
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
        <Modal.Title>Approve Run</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Errors errors={errors} />
        <form onSubmit={approveRunFormSubmitHandler}>
          <table className="table">
            <tbody>
              <tr>
                <td>{`Date:` }</td>
                <td>
                    <DatePicker
                      id="timestamp"
                      name="timestamp"
                      className="form-control"
                      required
                      showTimeSelect
                      showTimeInput
                      selected={new Date(run.timestamp)}
                      onChange={(timestamp) => {
                        const updatedRun = { ...run };
                        updatedRun[`timestamp`] = timestamp;
                        setRun(updatedRun);
                      }}
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

export default ApproveRun;
