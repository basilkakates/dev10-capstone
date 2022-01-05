import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../AuthContext";

function NavBar() {
  const auth = useContext(AuthContext);

  return (
    <>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/about">About</Link>
        </li>
        <li>
          <Link to="/userprofile">User Profile</Link>
        </li>
        <li>
          <Link to="/runs">Runs</Link>
        </li>
        <li>
          <Link to="/clubs">Clubs</Link>
        </li>
        <li>
          <Link to="/runs/pending">Pending Runs</Link>
        </li>
        {!auth.user && (
          <>
            <li>
              <Link to="/login">Login</Link>
            </li>
            <li>
              <Link to="/register">Register</Link>
            </li>
          </>
        )}
      </ul>
      {auth.user && (
        <div>
          <p>Hello {auth.user.username}!</p>
          <button onClick={() => auth.logout()} className="btn btn-primary">
            Logout
          </button>
        </div>
      )}
    </>
  );
}

export default NavBar;
