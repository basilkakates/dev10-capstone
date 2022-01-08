import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import RunTableHeader from "./RunTableHeader";

function UserProfile() {
  const [user, setUser] = useState([]);
  const [runs, setRuns] = useState([]);
  const [clubs, setClubs] = useState([]);

  const getUser = () => {
    fetch("http://localhost:8080/api/user")
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("user fetch failed");
        }
        return response.json();
      })
      .then((json) => setUser(json))
      .catch(console.log);
  };

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

  const getClubs = () => {
    fetch("http://localhost:8080/club")
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("clubs fetch failed");
        }
        return response.json();
      })
      .then((json) => setClubs(json))
      .catch(console.log);
  };

  useEffect(() => {
    getUser();
    getRuns();
    getClubs();
  }, []);

  return (
    <Container>
      <div>
        <h2 className="my-4">User Profile</h2>
        <table className="table">
          <tbody>
            <tr>
              <td scope="row">First Name: {user.firstName}</td>
            </tr>
            <tr>
              <td scope="row">Last Name: {user.lastName}</td>
            </tr>
            <tr>
              <td scope="row">Email: {user.email}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div>
        <h2 className="my-4">Signed Up Runs</h2>
        <table className="table">
          <thead>
            <RunTableHeader />
          </thead>
          <tbody>
            {runs.map((run) => (
              <tr key={run.runId}>
                {run.status !== "pending" && (
                  <>
                    <th scope="row">{run.runId}</th>
                    <td>{run.date}</td>
                    <td>{run.startTime}</td>
                    <td>{run.address}</td>
                    <td>{run.description}</td>
                    <td>{run.clubId}</td>
                    <td>{run.userId}</td>
                    <td>{run.maxCapacity}</td>

                    {run.status === "approved" && (
                      <td>
                        <td className="btn btn-success btn-sm">Sign Up</td>
                        <Link
                          to={`/runs/edit/${run.runId}`}
                          className="btn btn-primary btn-sm"
                        >
                          <i className="bi bi-pencil"></i> Edit
                        </Link>
                        <Link
                          to={`/runs/cancel/${run.runId}`}
                          className="btn btn-danger btn-sm"
                        >
                          <i className="bi bi-pencil"></i> Cancel
                        </Link>
                      </td>
                    )}
                    {run.status === "canceled" && (
                      <td className="btn btn-outline-danger btn-sm" disabled>
                        CANCELED
                      </td>
                    )}
                  </>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div>
        <h2 className="my-4">Club Memberships</h2>
        <table className="table">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Name</th>
              <th scope="col">Description</th>
              <th scope="col">Number of Members</th>
            </tr>
          </thead>
          <tbody>
            {clubs.map((club) => (
              <tr key={club.clubId}>
                <th scope="row">{club.clubId}</th>
                <td>{club.name}</td>
                <td>{club.description}</td>
                <td></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </Container>
  );
}

export default UserProfile;
