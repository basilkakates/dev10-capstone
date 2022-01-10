import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

function AdminStatus({ clubId }) {
  const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);

  const getClubUserIsAdminOf = () => {
    fetch("http://localhost:8080/api/member/admins/user/3")
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
  }, []);

  return (
    <Container>
      {clubUserIsAdminOf === clubId && <Button>Admin</Button>}
    </Container>
  );
}

export default AdminStatus;
