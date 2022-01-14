import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { useEffect, useState } from "react";

function NavBar({ user }) {
  const [clubUserAdminOf, setClubUserAdminOf] = useState(null);

  const getClubUserAdminOf = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/member/admins/user/${user.userId}`
      );
      const data = await response.json();
      if (response.status === 200) {
        setClubUserAdminOf(data.club);
      }

      console.log(data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    if (user.userId) {
      getClubUserAdminOf();
    }
  }, [user.userId]);

  return (
    <>
      <Container>
        <Row className="align-items-start">
          <Col>
            <Link to="/runs" className="btn btn-primary">
              Runs
            </Link>
          </Col>
          <Col>
            <Link to="/clubs" className="btn btn-primary">
              Clubs
            </Link>
          </Col>
          {clubUserAdminOf !== null ? (
            <Col>
              <Link to="/runs/pending" className="btn btn-primary">
                Pending Runs
              </Link>
            </Col>
          ) : null}
          <Col>
            <Link to="/userprofile" className="btn btn-primary">
              User Profile
            </Link>
          </Col>
          <Col>
            <Link to="/about" className="btn btn-primary">
              About
            </Link>
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default NavBar;
