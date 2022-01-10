import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";
import EditRun from "./EditRun";
import CancelRun from "./CancelRun";

function AdminOptionsForRun({ runId, clubId }) {
  const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);
  const [showEditModal, setShowEditModal] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);

  const handleEditModalClose = () => setShowEditModal(false);
  const handleEditModalShow = () => setShowEditModal(true);

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
              <Button variant="primary" onClick={handleEditModalShow}>
                Edit
              </Button>

              <EditRun
                showModal={showEditModal}
                closeModal={handleEditModalClose}
                runId={runId}
              />
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
