import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function UserProfile() {
  const [user, setUser] = useState([]);

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

  useEffect(() => {
    getUser();
  }, []);

  return (
    <div>
      <h2 className="my-4">User Profile</h2>

      <table className="table">
        <tbody>
          <td>First Name: {user.first_name}</td>
          <td>Last Name: {user.last_name}</td>
          <td>Email: {user.email}</td>
        </tbody>
      </table>
    </div>
  );
}

export default UserProfile;
