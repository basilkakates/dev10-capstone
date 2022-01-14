import Container from "react-bootstrap/Container";

function About() {
  return (
    <Container>
      <h2 className="my-4"style={{color: "white", background: "#375d83", width: "120px", height: "42px", fontWeight: "500", borderRadius: "10px"}}>&nbsp;About&nbsp;</h2>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">Summary</th>
          </tr>
        </thead>
        <tbody>
          <td>
            <p>
            Club runner is a web app used to organize and promote local runs and
            running groups. It's designed for users to easily find and sign up
            for runs.
            </p>
          </td>
        </tbody>
      </table>

      <table className="table">
        <thead>
          <tr>
            <th scope="col">User Profile</th>
          </tr>
        </thead>
        <tbody>
          <td>
            <p>
              Lists a logged in users basic information. 
              Provides a list of runs they are currently signed up for and a list of clubs they 
              are members of.
              The club list will also specify if a user is an admin for a certain club.
            </p>    
          </td>
        </tbody>
      </table>

      <table className="table">
        <thead>
          <tr>
            <th scope="col">Runs</th>
          </tr>
        </thead>
        <tbody>
          <td>
            <p>
            Displays all currently approved runs. Users can sign up for runs
            here and/or see a list of members who are signed up for a run. Users
            are also able to add a run by clicking the "Add Run" button.
            Submitted runs will need to be approved by a club admin before
            appearing on the Runs table. Once approved, the creator of the run
            and any club admins can optionally edit or cancel the scheudled run.
            Canceled runs will still appear on the list, but will have a
            "CANCELLED" notice instead of a sign up button. Runs that were
            scheduled in the past do not display.
            </p>
          </td>
        </tbody>
      </table>

      <table className="table">
        <thead>
          <tr>
            <th scope="col">Clubs</th>
          </tr>
        </thead>
        <tbody>
          <td>
            <p>
              Shows the list of clubs with its basic information. Like the User
            Profile, the club list will also specify if a user is an admin for a
            certain club. Currently users are not able to join new clubs.
            </p>
          </td>
        </tbody>
      </table>

      <table className="table">
        <thead>
          <tr>
            <th scope="col">Pending Runs</th>
          </tr>
        </thead>
        <tbody>
          <td>
            <p>
            Only appears for club admins. Any pending runs will appear here for
            any members that are an admin for the club hosting the run. Admins
            can either approve or deny the run. Approved requests will then
            appear under the Runs link and denied requests will be deleted.
            </p>
          </td>
        </tbody>
      </table>
    </Container>
  );
}

export default About;
