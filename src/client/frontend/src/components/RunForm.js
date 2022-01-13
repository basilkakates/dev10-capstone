import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";

import Modal from "react-bootstrap/Modal";
import DatePicker from "react-datepicker";
import PlacesAutocomplete from "./PlacesAutocomplete";

import "react-datepicker/dist/react-datepicker.css";
import "react-time-picker/dist/TimePicker.css";

import Errors from "./Errors";
import Button from "react-bootstrap/esm/Button";

const DEFAULT_RUN = {
  runId: 0,
  timestamp: new Date(),
  address: "",
  description: "",
  maxCapacity: "",
  latitude: "",
  longitude: "",
  user: {
    firstName: "",
    lastName: "",
    email: "",
  },
  club: {
    name: "",
  },
  runStatus: {
    status: "",
  },
};

function RunForm({ isVisible, toggleModal, runId, user }) {
  const [run, setRun] = useState(DEFAULT_RUN);
  const [clubs, setClubs] = useState([]);
  const [runStatuses, setRunStatuses] = useState([]);
  const [isAdmin, setIsAdmin] = useState({});
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const addressToForm = (add, lat, lng) => {
    const updatedRun = { ...run };
    updatedRun.address = add;
    updatedRun.latitude = lat;
    updatedRun.longitude = lng;
    setRun(updatedRun);
  };

  useEffect(() => {
    const getData = async () => {
      try {
        if (runId) {
          const runResponse = await fetch(
            `http://localhost:8080/api/run/${runId}`
          );
          const runData = await runResponse.json();
          setRun(runData);
        } else {
          const updatedRun = { ...DEFAULT_RUN };
          updatedRun["user"] = user;
          setRun(updatedRun);
        }

        if (user.userId) {
          const memberResponse = await fetch(
            `http://localhost:8080/api/member/user/${user.userId}`
          );
          const memberData = await memberResponse.json();
          setClubs(memberData.flatMap((member) => [member.club]));

          memberData.map((member) => {
            if (member.isAdmin) {
              setIsAdmin(member.club);
            }
          });
        }

        const runStatusResponse = await fetch(
          `http://localhost:8080/api/runStatus`
        );
        const runStatusData = await runStatusResponse.json();
        setRunStatuses(runStatusData);
      } catch (error) {
        console.log(error);
        history.push(`/runs`);
      }
    };
    getData();
  }, [isVisible]);

  const handleChange = (event) => {
    const updatedRun = { ...run };
    updatedRun[event.target.name] = event.target.value;
    setRun(updatedRun);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const updatedRun = { ...run };
    console.log(updatedRun);

    try {
      if (runId) {
        const init = {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
          body: JSON.stringify(updatedRun),
        };

        const response = await fetch(
          `http://localhost:8080/api/run/${runId}`,
          init
        );

        if (response.status != 204) {
          const data = await response.json();
          setErrors(data);
          throw new Error(`Response is not 204 No Content: ${data}`);
        } else {
          console.log(`Successfully updated Run#${runId}.`);
          toggleModal();
          history.push(`/runs`);
        }
      } else {
        const init = {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
          body: JSON.stringify(updatedRun),
        };

        const response = await fetch(`http://localhost:8080/api/run`, init);
        const data = await response.json();

        if (response.status !== 201) {
          setErrors(data);
          throw new Error(`Response is not 201 Created: ${data}`);
        } else {
          console.log(`Successfully created Run.`);
          toggleModal();
          history.push(`/runs`);
        }
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <Modal show={isVisible} onHide={toggleModal}>
        <Modal.Header closeButton>
          <Modal.Title>{runId ? "Update A Run" : "Add A Run"}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Errors errors={errors} />
          <form onSubmit={handleSubmit}>
            <table className="table">
              <tbody>
                <tr>
                  <td>{`Date & Time:`}</td>
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
                    />
                  </td>
                </tr>
                <tr>
                  <td>Address: </td>
                  <td>
                    <PlacesAutocomplete
                      id="address"
                      addressToForm={addressToForm}
                      runAddress={run.address}
                      run={run}
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
                      onChange={handleChange}
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
                      required
                      value={run.maxCapacity}
                      onChange={handleChange}
                    />
                  </td>
                </tr>
                <tr>
                  <td>Club: </td>
                  <td>
                    <select className="form-select" id="club" name="club">
                      <option
                        key={run.club.clubId}
                        value={run.club}
                        onClick={() => {
                          const updatedRun = { ...run };
                          updatedRun.club = run.club;
                          updatedRun.runStatus = "";
                          setRun(updatedRun);
                        }}
                      >
                        {run.club.name}
                      </option>
                      {clubs
                        .filter((club) => run.club.clubId !== club.clubId)
                        .map((club) => (
                          <option
                            key={club.clubId}
                            value={club}
                            onClick={() => {
                              const updatedRun = { ...run };
                              updatedRun.club = club;
                              updatedRun.runStatus = "";
                              setRun(updatedRun);
                            }}
                          >
                            {club.name}
                          </option>
                        ))}
                    </select>
                  </td>
                </tr>
                <tr>
                  <td>User: </td>
                  <td>
                    <input
                      type="text"
                      className="form-control"
                      id="user"
                      name="user"
                      value={run.user.firstName + " " + run.user.lastName}
                      disabled
                    />
                  </td>
                </tr>
                <tr>
                  <td>Run Status: </td>
                  <td>
                    <select
                      className="form-select"
                      id="runStatus"
                      name="runStatus"
                    >
                      <option
                        key={run.runStatus.runStatusId}
                        onClick={() => {
                          const updatedRun = { ...run };
                          updatedRun.runStatus = run.runStatus;
                          setRun(updatedRun);
                        }}
                      >
                        {run.runStatus.status}
                      </option>
                      {runStatuses
                        .filter((runStatus) => {
                          if (!runId && runStatus.status === "Cancelled") {
                            return false;
                          }

                          if (
                            runStatus.runStatusId === run.runStatus.runStatusId
                          ) {
                            return false;
                          }

                          if (
                            isAdmin !== run.club &&
                            runStatus.status !== "Pending Approval"
                          ) {
                            return false;
                          }

                          return true;
                        })
                        .map((runStatus) => (
                          <option
                            key={runStatus.runStatusId}
                            onClick={() => {
                              const updatedRun = { ...run };
                              updatedRun.runStatus = runStatus;
                              setRun(updatedRun);
                            }}
                          >
                            {runStatus.status}
                          </option>
                        ))}
                    </select>
                  </td>
                </tr>
              </tbody>
            </table>
            <div className="mt-5">
              <Button
                className="btn btn-primary"
                onClick={errors ? null : toggleModal}
                type="submit"
              >
                <i className="bi bi-plus-circle-fill"></i> Submit
              </Button>
              <Button
                className="btn btn-secondary"
                onClick={() => {
                  toggleModal();
                  setErrors([]);
                }}
              >
                <i className="bi bi-x"></i> Go Back
              </Button>
            </div>
          </form>
        </Modal.Body>
        <Modal.Footer></Modal.Footer>
      </Modal>
    </>
  );
}

export default RunForm;
