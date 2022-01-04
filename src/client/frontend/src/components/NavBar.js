import { Link } from "react-router-dom";

function NavBar() {
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
      </ul>
    </>
  );
}

export default NavBar;
