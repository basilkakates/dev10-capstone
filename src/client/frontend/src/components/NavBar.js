import { useContext } from "react";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import AuthContext from "../AuthContext";

function NavBar() {
  // const auth = useContext(AuthContext);

  return (
    <>
      {/* {!auth.user && (
        <>
          <Link to="/login" className="btn btn-primary">
            Login
          </Link>
          <Link to="/register" className="btn btn-primary">
            Register
          </Link>
        </>
      )}
      {auth.user && (
        <div>
          <button onClick={() => auth.logout()} className="btn btn-primary">
            Logout
          </button>
        </div>
      )} */}

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
          <Col>
            {/* {auth.user && ( */}
            <Link to="/runs/pending" className="btn btn-primary">
              Pending Runs
            </Link>
            {/* )} */}
          </Col>
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
