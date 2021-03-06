import { useContext } from "react";
import NavBar from "./NavBar";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import AuthContext from "../AuthContext";

function Header({ user }) {
  const auth = useContext(AuthContext);

  return (
    <>
      <Container>
        <Row className="row justify-content-between">
          <Col class="col-4 align-self-center">
            <h1 className="my-2"style={{color: "white", background: "#375d83", width:"255px", height: "55px", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Club Runner</h1>
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
      <NavBar user={user} />
      <h2 className="my-4"></h2>
    </>
  );
}

export default Header;
