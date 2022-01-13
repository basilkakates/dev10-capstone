import Container from "react-bootstrap/Container";

function UserProfile({ user }) {
  return (
    <Container>
      <div>
        <h2 className="my-4">User Profile</h2>
        <table className="table">
          <tbody>
            <tr>
              <td scope="row">First Name: {user.firstName}</td>
            </tr>
            <tr>
              <td scope="row">Last Name: {user.lastName}</td>
            </tr>
            <tr>
              <td scope="row">Email: {user.email}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </Container>
  );
}

export default UserProfile;
