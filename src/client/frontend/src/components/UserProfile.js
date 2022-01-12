import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import RunTableHeader from "./RunTableHeader";

function UserProfile({ user }) {
  // const [user, setUser] = useState([]);

  // const getUser = () => {
  //   fetch(`http://localhost:8080/api/user/${user.userId}`)
  //     .then((response) => {
  //       if (response.status !== 200) {
  //         return Promise.reject("user fetch failed");
  //       }
  //       return response.json();
  //     })
  //     .then((json) => setUser(json))
  //     .catch(console.log);
  // };

  // useEffect(() => {
  //   getUser();
  // }, []);

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
