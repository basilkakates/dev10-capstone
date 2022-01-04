import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function Clubs() {
  const [clubs, setClubs] = useState([]);

  const getClubs = () => {
    fetch("http://localhost:8080/api/club")
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
    <div>
      <h2 className="my-4">Clubs</h2>

      <table className="table">
        <thead>
          <tr>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {clubs.map((club) => (
            <tr key={club.club_id}>
              <td>{club.name}</td>
              <td>Description: {club.description}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Clubs;
