import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

function JoinRun({ joined }) {
  const joinRun = () => {};
  const dropRun = (event) => {};

  return (
    <Container>
          {joined === true ? (
            <Button onClick={dropRun}>Joined</Button>
          ) : (
            <Button onClick={joinRun}>Join</Button>
          )}
    </Container>
  );
}

export default JoinRun;
