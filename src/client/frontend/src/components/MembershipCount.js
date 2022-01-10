import React, { useState, useEffect } from "react";

function MembershipCount({ clubId }) {
  const [usersInClub, setUsersInClub] = useState([]);

  const getUsersInClub = () => {
    fetch(`http://localhost:8080/api/member/club/${clubId}`)
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setUsersInClub(json))
      .catch(console.log);
  };

  useEffect(() => {
    getUsersInClub();
  }, []);

  return <td>{usersInClub.length}</td>;
}

export default MembershipCount;
