import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";
import CancelRun from "./CancelRun";

function AdminOptionsForRun({ runId, clubId, viewModal, setRunId, user }) {
  const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);
  const [showCancelModal, setShowCancelModal] = useState(false);

  const handleCancelModalClose = () => setShowCancelModal(false);
  const handleCancelModalShow = () => setShowCancelModal(true);

  const getClubUserIsAdminOf = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/member/admins/user/${user.userId}`);
        const data = await response.json();
        if (response.status === 200) {
          setClubUserIsAdminOf(data);
        } else if (response.status !== 404) {
          throw new Error(`Response is not 200 OK: ${data}`);
        }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    if (user.userId) {
      getClubUserIsAdminOf();
    }
  }, [user.userId]);

  return (
    <>
      {clubUserIsAdminOf.isAdmin === 1 &&
        clubUserIsAdminOf.club.clubId === clubId && (
          <>
            <Container>
              <Button
                className="btn btn-primary"
                onClick={() => {
                  viewModal();
                  setRunId(runId);
                }}
              >
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
