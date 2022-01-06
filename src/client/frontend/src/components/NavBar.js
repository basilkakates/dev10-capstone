import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../AuthContext";

function NavBar() {
  const auth = useContext(AuthContext);

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
      <div className="container">
        <div className="row align-items-start">
          <div class="col">
            <Link to="/" className="btn btn-primary">
              Home
            </Link>
          </div>
          <div class="col">
            <Link to="/about" className="btn btn-primary">
              About
            </Link>
          </div>
          <div class="col">
            <Link to="/userprofile" className="btn btn-primary">
              User Profile
            </Link>
          </div>
          <div class="col">
            <Link to="/runs" className="btn btn-primary">
              Runs
            </Link>
          </div>
          <div class="col">
            <Link to="/clubs" className="btn btn-primary">
              Clubs
            </Link>
          </div>
          <div class="col">
            {/* {auth.user && ( */}
            <Link to="/runs/pending" className="btn btn-primary">
              Pending Runs
            </Link>
            {/* )} */}
          </div>
        </div>
      </div>
    </>
  );
}

export default NavBar;
