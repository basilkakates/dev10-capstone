import { useEffect, useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";

import Modal from "react-bootstrap/Modal";
import DatePicker from "react-datepicker";
import TimePicker from "react-time-picker";
import PlacesAutocomplete from "./PlacesAutocomplete";

import "react-datepicker/dist/react-datepicker.css";

import Errors from "./Errors";
import Button from "react-bootstrap/esm/Button";

const DEFAULT_RUN = {
    date: "",
    address: "",
    startTime: "",
    latitude: "",
    longitude: "",
    user: {
        firstName: "",
        lastName: "",
        email: ""
    },
    club: {
        name: ""
    },
    status: {
        status: ""
    }
};

function RunForm({ isVisible, toggleModal, runId }) {
    const [run, setRun] = useState(DEFAULT_RUN);
    const [errors, setErrors] = useState([]);

    const history = useHistory();
    useEffect(() => {
        const getData = async () => {
            try {
                if (runId) {
                    const response = await fetch(`http://localhost:8080/api/run/${runId}`);
                    const data = await response.json();
                    setRun(data);
                }
            } catch (error) {
                console.log(error);
                history.push(`/runs`)
            }
        };  
        getData();
    }, [history, runId]);

    const handleChange = (event) => {
        const updatedRun = { ...run };
        updatedRun[event.target.name] = event.target.value;
        setRun(updatedRun);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const updatedRun = { ...run };

        try {
            if (runId) {
                const init = {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    },
                    body: JSON.stringify(updatedRun)
                };

                const response = await fetch(`http://localhost:8080/api/run/${runId}`, init);

                if (response.status != 204) {
                    const data = await response.json();
                    setErrors(data);
                    throw new Error(`Response is not 204 No Content: ${data}`);
                } else {
                    console.log(`Successfully updated Run#${runId}.`);
                    history.push(`/runs`);
                }
            } else {
                const init = {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    },
                    body: JSON.stringify(updatedRun)
                };

                const response = await fetch(`http://localhost:8080/api/run`, init);
                const data = await response.json();

                if (response.status !== 201) {
                    setErrors(data);
                    throw new Error(`Response is not 201 Created: ${data}`);
                } else {
                    console.log(`Successfully created Run.`);
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
                    <Modal.Title>{run.runId ? "Update An Run" : "Add An Run"}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Errors errors={errors} />
                    <form onSubmit={handleSubmit}>
                        <table className="table">
                            <tbody>
                                <tr>
                                    <td>Date: </td>
                                    <td>
                                        <DatePicker
                                            id="date"
                                            name="date"
                                            required
                                            selected={run.date}
                                            onChange={(date) => {
                                                const updatedRun = { ...run };
                                                updatedRun[`date`] = date;
                                                console.log(updatedRun)
                                                setRun(updatedRun);
                                            }}
                                        />
                                    </td>
                                </tr>
                                <tr>
                                    <td>Start Time: </td>

                                    <td>
                                        <TimePicker
                                            id="startTime"
                                            name="startTime"
                                            onChange={handleChange}
                                            value={run.startTime}
                                            disableClock={true}
                                        />
                                    </td>
                                </tr>
                                <tr>
                                    <td>Address: </td>
                                    <td>
                                        <PlacesAutocomplete />
                                        <input
                                            type="text"
                                            id="address"
                                            name="address"
                                            value={run.address}
                                            onChange={handleChange}
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
                                            value={run.maxCapacity}
                                            onChange={handleChange}
                                        />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div className="mt-5">
                            <Button className="btn btn-primary" onClick={errors ? null : toggleModal} type="submit">
                                <i className="bi bi-plus-circle-fill"></i> Submit
                            </Button>
                            <Button className="btn btn-secondary" onClick={toggleModal}>
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

