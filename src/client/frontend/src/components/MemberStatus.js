import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

function MemberStatus({ clubId }) {
  const [clubsUserIsMemberOf, setClubsUserIsMemberOf] = useState([]);

  const getClubsUserIsMemberOf = () => {
    fetch("http://localhost:8080/api/member/user/1")
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setClubsUserIsMemberOf(json))
      .catch(console.log);
  };

  useEffect(() => {
    getClubsUserIsMemberOf();
  }, []);

  return (
    <Container>
      {clubsUserIsMemberOf.map((clubUserIsMemberOf) => (
        <>
          {clubUserIsMemberOf.club.clubId === clubId && <Button>Member</Button>}
        </>
      ))}
    </Container>
  );
}

export default MemberStatus;
