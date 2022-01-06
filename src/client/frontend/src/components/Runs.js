import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import RunTableHeader from "./RunTableHeader";

function Runs() {
  const [runs, setRuns] = useState([]);

  const getRuns = () => {
    fetch("http://localhost:8080/run")
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setRuns(json))
      .catch(console.log);
  };

  useEffect(() => {
    getRuns();
  }, []);

  const runDeleteClickHandler = (run_id) => {
    const init = {
      method: "DELETE",
    };

    fetch(`http://localhost:8080/run/${run_id}`, init)
      .then((response) => {
        if (response.status === 204) {
          getRuns();
        } else if (response.status === 404) {
          Promise.reject(`Run ID ${run_id} not found`);
        } else {
          Promise.reject("Something unexpected went wrong :)");
        }
      })
      .catch((error) => console.log(error));
  };

  return (
    <div>
      <h2 className="my-4">Runs</h2>

      <Link to="/runs/add" className="btn btn-primary mb-4">
        <i className="bi bi-plus-circle-fill"></i> Add Run
      </Link>

      <table className="table">
        <thead>
          <RunTableHeader />
        </thead>
        <tbody>
          {runs.map((run) => (
            <tr key={run.run_id}>
              {run.status !== "pending" && (
                <>
                  <th scrope="row">{run.run_id}</th>
                  <td>{run.date}</td>
                  <td>{run.start_time}</td>
                  <td>{run.address}</td>
                  <td>{run.description}</td>
                  <td>{run.club_id}</td>
                  <td>{run.user_id}</td>
                  <td>{run.max_capacity}</td>

                  {run.status === "approved" && (
                    <td>
                      <Link
                        to={`/runs/edit/${run.run_id}`}
                        className="btn btn-primary btn-sm"
                      >
                        <i className="bi bi-pencil"></i> Edit
                      </Link>
                      <Link
                        to={`/runs/cancel/${run.run_id}`}
                        className="btn btn-danger btn-sm"
                      >
                        <i className="bi bi-pencil"></i> Cancel
                      </Link>
                    </td>
                  )}
                  {run.status === "canceled" && (
                    <td className="btn btn-danger btn-sm">CANCELED</td>
                  )}
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Runs;
