import NavBar from "./NavBar";
import { Link } from "react-router-dom";

function Header() {
  return (
    <>
      <div className="container">
        <div className="row justify-content-between">
          <div class="col-4 align-self-center">
            <h1 className="my-2">Club Runner</h1>
          </div>
          <div class="col-md-2 offset-md-6 align-self-center">
            <Link to="/login" className="btn btn-primary">
              Login
            </Link>
            <Link to="/register" className="btn btn-primary">
              Register
            </Link>
          </div>
        </div>
      </div>

      <div class="col">
        <h2> </h2>
      </div>

      <NavBar />
    </>
  );
}

export default Header;
