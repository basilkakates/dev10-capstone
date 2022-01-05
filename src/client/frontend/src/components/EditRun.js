import { useState, useEffect } from "react";
import { Link, useHistory, useParams } from "react-router-dom";

import Errors from "./Errors";

function EditRun() {
  const [date, setDate] = useState("");
  const [start_time, setStartTime] = useState("");
  const [address, setAddress] = useState("");
  const [description, setDescription] = useState("");
  const [club_id, setClubId] = useState("");
  const [user_id, setUserId] = useState("");
  const [max_capacity, setMaxCapacity] = useState([]);
  const [errors, setErrors] = useState([]);

  const { run_id } = useParams();
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
    fetch(`http://localhost:8080/api/run/${run_id}`)
      .then((response) => {
        if (response.status === 404) {
          return Promise.reject(`Received 404 Not Found for Run ID: ${run_id}`);
        }
        return response.json();
      })
      .then((data) => {
        setDate(data.date);
        setStartTime(data.start_time);
        setAddress(data.address);
        setDescription(data.description);
        setMaxCapacity(data.max_capacity);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [run_id]);

  const editRunFormSubmitHandler = (event) => {
    event.preventDefault();

    const updatedRun = {
      run_id: run_id,
      date,
      start_time,
      address,
      description,
      club_id,
      user_id,
      max_capacity,
    };

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(updatedRun),
    };

    fetch(`http://localhost:8080/api/run/${updatedRun.run_id}`, init)
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
    <>
      <h2 className="my-4">Edit Run</h2>
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
          <button className="btn btn-success" type="submit">
            <i className="bi bi-plus-circle-fill"></i> Update Run
          </button>
          <Link to="/runs" className="btn btn-warning ml-2">
            <i className="bi bi-x"></i> Cancel
          </Link>
        </div>
      </form>
    </>
  );
}

export default EditRun;
