import { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";

import PendingRunTableHeader from "./PendingRunTableHeader";
import RunTime from "./RunTime";
import Button from "react-bootstrap/Button";
import ApproveRun from "./ApproveRun";
import DeleteRun from "./DeleteRun";

function PendingRuns({ user }) {
  const [runs, setRuns] = useState([]);
  const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);
  const [showApproveModal, setShowApproveModal] = useState(false);
  const [showDeclineModal, setShowDeclineModal] = useState(false);

  const handleApproveModalClose = () => setShowApproveModal(false);
  const handleApproveModalShow = () => setShowApproveModal(true);

  const handleDeclineModalClose = () => setShowDeclineModal(false);
  const handleDeclineModalShow = () => setShowDeclineModal(true);

  const getClubUserIsAdminOf = () => {
    fetch(`http://localhost:8080/api/member/admins/user/${user.userId}`)
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setClubUserIsAdminOf(json.club))
      .catch(console.log);
  };

  const getRuns = () => {
    fetch("http://localhost:8080/api/run")
      .then((response) => {
        if (response.status !== 200) {
          return Promise.reject("runs fetch failed");
        }
        return response.json();
      })
      .then((json) => setRuns(json))
      .catch(console.log);
  };

  useEffect(() => {
    getRuns();
    getClubUserIsAdminOf();
  }, [user]);

  return (
    <Container>
      <h2 className="my-4">Pending Runs</h2>

      <table className="table">
        <thead>
          <PendingRunTableHeader />
        </thead>
        <tbody>
          {runs
            .filter((run) => (
              run.club.clubId === clubUserIsAdminOf.clubId
            ))
            .map((run) => (
              <tr key={run.runId}>
                {run.runStatus.status == "Pending Approval" && (
                  <>
                    <RunTime timestamp={run.timestamp} />
                    <td>{run.address}</td>
                    <td>{run.description}</td>
                    <td>{run.club.name}</td>
                    <td>{run.maxCapacity}</td>
                    <td>
                      <div>
                        <Button
                          variant="primary"
                          onClick={handleApproveModalShow}
                        >
                          Approve
                        </Button>
                        <ApproveRun
                          showModal={showApproveModal}
                          closeModal={handleApproveModalClose}
                          run={run}
                        />
                      </div>
                      <div>
                        <Button
                          variant="secondary"
                          onClick={handleDeclineModalShow}
                        >
                          Decline
                        </Button>
                        <DeleteRun
                          showModal={showDeclineModal}
                          closeModal={handleDeclineModalClose}
                          run={run}
                        />
                      </div>
                    </td>
                  </>
                )}
              </tr>
            ))}
        </tbody>
      </table>
    </Container>
  );
}

export default PendingRuns;
