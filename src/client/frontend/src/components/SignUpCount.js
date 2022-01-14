import React, { useState, useEffect } from "react";

function SignUpCount({ runId }) {
  const [usersSignedUp, setUsersSignedUp] = useState([]);

  const getUsersSignedUp = () => {
    fetch(`http://localhost:8080/api/runner/run/${runId}`)
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setUsersSignedUp(json))
      .catch(console.log);
  };

  useEffect(() => {
    getUsersSignedUp();
  });

  return usersSignedUp && <>{usersSignedUp.length}</>;
}

export default SignUpCount;
