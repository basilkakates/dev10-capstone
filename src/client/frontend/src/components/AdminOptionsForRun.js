import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";
import EditRun from "./EditRun";
import CancelRun from "./CancelRun";
import RunForm from "./RunForm";
import { Link } from "react-router-dom";

function AdminOptionsForRun({ runId, clubId, viewModal }) {
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
              <Link className="btn btn-primary" onClick={viewModal} to={`/run/edit/${runId}`}>
                Edit
              </Link>
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
