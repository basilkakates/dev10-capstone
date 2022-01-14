import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

function AdminStatus({ clubId, user }) {
  const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);

  const getClubUserIsAdminOf = () => {
    fetch(`http://localhost:8080/api/member/admins/user/${user.userId}`)
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setClubUserIsAdminOf(json))
      .catch(console.log);
  };

  useEffect(() => {
    getClubUserIsAdminOf();
  }, [user]);

  return (
    <td>
    <Container>
      {clubUserIsAdminOf.isAdmin === 1 &&
        clubUserIsAdminOf.club.clubId === clubId && <Button>Admin</Button>}
    </Container>
    </td>
  );
}

export default AdminStatus;
