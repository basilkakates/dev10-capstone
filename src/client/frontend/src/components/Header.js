import { useContext } from "react";
import NavBar from "./NavBar";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import AuthContext from "../AuthContext";

function Header() {
  const auth = useContext(AuthContext);

  return (
    <>
      <Container>
        <Row className="row justify-content-between">
          <Col class="col-4 align-self-center">
            <h1 className="my-2">Club Runner</h1>
          </Col>
          <Col class="col-md-2 offset-md-6 align-self-center">
            {!auth.user && (
              <>
                <Link to="/login" className="btn btn-primary">
                  Login
                </Link>
                <Link to="/register" className="btn btn-secondary">
                  Register
                </Link>
              </>
            )}
            {auth.user && (
              <>
                <button
                  onClick={() => auth.logout()}
                  className="btn btn-primary"
                >
                  Logout
                </button>
              </>
            )}
          </Col>
        </Row>
      </Container>
      <NavBar />
    </>
  );
}

export default Header;
