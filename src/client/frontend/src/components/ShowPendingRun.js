// import React, { useState, useEffect } from "react";
// import Container from "react-bootstrap/Container";
// import Button from "react-bootstrap/Button";
// import ApproveRun from "./ApproveRun";
// import DeleteRun from "./DeleteRun";

// function ShowPendingRun({ run }) {
//   const [clubUserIsAdminOf, setClubUserIsAdminOf] = useState([]);
//   const [showApproveModal, setShowApproveModal] = useState(false);
//   const [showDeclineModal, setShowDeclineModal] = useState(false);

//   const handleApproveModalClose = () => setShowApproveModal(false);
//   const handleApproveModalShow = () => setShowApproveModal(true);

//   const handleDeclineModalClose = () => setShowDeclineModal(false);
//   const handleDeclineModalShow = () => setShowDeclineModal(true);

//   const getClubUserIsAdminOf = () => {
//     fetch("http://localhost:8080/api/member/admins/user/5")
//       .then((response) => {
//         if (response.status !== 200) {
//           return Promise.reject("runs fetch failed");
//         }
//         return response.json();
//       })
//       .then((json) => setClubUserIsAdminOf(json))
//       .catch(console.log);
//   };

//   useEffect(() => {
//     getClubUserIsAdminOf();
//   }, []);

//   return (
//     <div>
//       {run.runStatus.status === "Pending Approval" &&
//         clubUserIsAdminOf.isAdmin === 1 &&
//         clubUserIsAdminOf.club.clubId === run.club.clubId && (
//           <tr key={run.runId}>
//             <th scrope="row">{run.date}</th>
//             <td>{run.startTime}</td>
//             <td>{run.address}</td>
//             <td>{run.description}</td>
//             <td>{run.club.name}</td>
//             <td>{run.maxCapacity}</td>
//             <td>
//               <div>
//                 <Button variant="primary" onClick={handleApproveModalShow}>
//                   Approve
//                 </Button>
//                 <ApproveRun
//                   showModal={showApproveModal}
//                   closeModal={handleApproveModalClose}
//                   runId={run.runId}
//                 />
//               </div>
//               <div>
//                 <Button variant="secondary" onClick={handleDeclineModalShow}>
//                   Decline
//                 </Button>
//                 <DeleteRun
//                   showModal={showDeclineModal}
//                   closeModal={handleDeclineModalClose}
//                   runId={run.runId}
//                 />
//               </div>
//             </td>
//           </tr>
//         )}
//     </div>
//   );
// }

// export default ShowPendingRun;
