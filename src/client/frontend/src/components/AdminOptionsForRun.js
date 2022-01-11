import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import CancelRun from "./CancelRun";

function AdminOptionsForRun({ runId, clubId, viewModal, setRunId }) {
  const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);
  const [showCancelModal, setShowCancelModal] = useState(false);

  const handleCancelModalClose = () => setShowCancelModal(false);
  const handleCancelModalShow = () => setShowCancelModal(true);

  const getClubUserIsAdminOf = () => {
    fetch("http://localhost:8080/api/member/admins/user/5")
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
    <div>
      {clubUserIsAdminOf.isAdmin === 1 &&
        clubUserIsAdminOf.club.clubId === clubId && (
          <div>
            <div>
              <Button className="btn btn-primary" onClick={() => {viewModal(); setRunId(runId);}}>
                Edit
              </Button>
            </div>
            <div>
              <Button variant="secondary" onClick={handleCancelModalShow}>
                Cancel
              </Button>
              <CancelRun
                showModal={showCancelModal}
                closeModal={handleCancelModalClose}
                runId={runId}
              />
            </div>
          </div>
        )}
    </div>
  );
}

export default AdminOptionsForRun;
