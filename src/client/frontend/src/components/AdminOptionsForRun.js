import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";
import CancelRun from "./CancelRun";
import { Link } from "react-router-dom";

function AdminOptionsForRun({ runId, clubId, viewModal, setRunId, userId }) {
  const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);
  const [showCancelModal, setShowCancelModal] = useState(false);

  const handleCancelModalClose = () => setShowCancelModal(false);
  const handleCancelModalShow = () => setShowCancelModal(true);

  const getClubUserIsAdminOf = () => {
    fetch(`http://localhost:8080/api/member/admins/user/${userId}`)
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
    <>
      {clubUserIsAdminOf.isAdmin === 1 &&
        clubUserIsAdminOf.club.clubId === clubId && (
          <>
            <Container>
              <Button className="btn btn-primary" onClick={() => {viewModal(); setRunId(runId);}}>
                Edit
              </Button>
            </Container>
            <Container>
              <Button variant="secondary" onClick={handleCancelModalShow}>
                Cancel
              </Button>
              <CancelRun
                showModal={showCancelModal}
                closeModal={handleCancelModalClose}
                runId={runId}
              />
            </Container>
          </>
        )}
    </>
  );
}

export default AdminOptionsForRun;
