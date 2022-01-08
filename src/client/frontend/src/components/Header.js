import NavBar from "./NavBar";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

function Header() {
  return (
    <>
      <Container>
        <Row className="row justify-content-between">
          <Col class="col-4 align-self-center">
            <h1 className="my-2">Club Runner</h1>
          </Col>
          <Col class="col-md-2 offset-md-6 align-self-center">
            <Link to="/login" className="btn btn-primary">
              Login
            </Link>
            <Link to="/register" className="btn btn-primary">
              Register
            </Link>
          </Col>
        </Row>
      </Container>

      <div class="col">
        <h2> </h2>
      </div>

      <NavBar />
    </>
  );
}

export default Header;
