import { useContext, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import AuthContext from "../AuthContext";
import Container from "react-bootstrap/Container";

import Errors from "./Errors";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState([]);

  const auth = useContext(AuthContext);

  const history = useHistory();

  const usernameOnChangeHandler = (event) => {
    setUsername(event.target.value);
  };

  const passwordOnChangeHandler = (event) => {
    setPassword(event.target.value);
  };

  const formSubmitHandler = async (event) => {
    event.preventDefault();

    const authAttempt = {
      username,
      password,
    };

    const response = await fetch("http://localhost:8080/api/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username,
        password,
      }),
    });

    if (response.status === 200) {
      const { jwt_token } = await response.json();

      auth.login(jwt_token);

      history.push("/");
    } else if (response.status === 403) {
      setErrors(["Login failed."]);
    } else {
      setErrors(["Unknown error."]);
    }
  };

  return (
    <Container>
      <h2 className="my-4" style={{color: "white", background: "#375d83", width:"100px", height:"42px", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Login&nbsp;</h2>
      <Errors errors={errors} />
      <form onSubmit={formSubmitHandler}>
        <div className="form-group">
          <label htmlFor="username" style={{color: "white", background: "#375d83", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Email:&nbsp;</label>
          <input
            className="form-control"
            type="text"
            id="username"
            name="username"
            value={username}
            onChange={usernameOnChangeHandler}
          />
        </div>
        <div className="form-group">
          <label htmlFor="blank" style={{}}>&nbsp;</label>
        </div>
        <div className="form-group">
          <label htmlFor="password" style={{color: "white", background: "#375d83", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Password:&nbsp;</label>
          <input
            className="form-control"
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={passwordOnChangeHandler}
          />
        </div>
        <div className="mt-5">
          <button className="btn btn-success" type="submit">
            <i className="bi bi-plus-circle-fill"></i> Login
          </button>
          <Link to="/" className="btn btn-warning ml-2">
            <i className="bi bi-x"></i> Cancel
          </Link>
        </div>
      </form>
    </Container>
  );
}

export default Login;
