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
    </Container>
  );
}

export default UserProfile;
