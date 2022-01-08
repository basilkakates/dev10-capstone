import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";

function Clubs() {
  const [clubs, setClubs] = useState([]);

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
    getClubs();
  }, []);

  return (
    <Container>
      <h2 className="my-4">Clubs</h2>

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
    </Container>
  );
}

export default Clubs;
