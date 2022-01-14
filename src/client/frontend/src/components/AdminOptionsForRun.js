import React, { useState, useEffect } from "react";
import CancelRun from "./CancelRun";

function AdminOptionsForRun({ viewModal, setRunId, user, run }) {
  const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);
  const [showCancelModal, setShowCancelModal] = useState(false);

  const handleCancelModalClose = () => setShowCancelModal(false);
  const handleCancelModalShow = () => setShowCancelModal(true);

  const getClubUserIsAdminOf = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/member/admins/user/${user.userId}`
      );
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
    } else {
      setClubUserIsAdminOf({});
    }
  }, [run, user.userId]);

  return (
    <>
      {clubUserIsAdminOf.isAdmin === 1 &&
        clubUserIsAdminOf.club.clubId === run.club.clubId && (
          <>
            <td>
              <button
                className="btn btn-secondary"
                onClick={() => {
                  viewModal();
                  setRunId(run.runId);
                }}
              >
                Edit
              </button>
            </td>
            {run.runStatus.status !== "Cancelled" && (
              <td>
                <button
                  className="btn btn-danger"
                  onClick={handleCancelModalShow}
                >
                  Cancel
                </button>
                <CancelRun
                  showModal={showCancelModal}
                  closeModal={handleCancelModalClose}
                  run={run}
                />
              </td>
            )}
          </>
        )}
    </>
  );
}

export default AdminOptionsForRun;
