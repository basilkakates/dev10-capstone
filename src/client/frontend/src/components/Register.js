import { useContext, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import AuthContext from "../AuthContext";
import Container from "react-bootstrap/Container";

import Errors from "./Errors";

function Register() {
  const [username, setUsername] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState([]);

  const auth = useContext(AuthContext);

  const history = useHistory();

  const usernameOnChangeHandler = (event) => {
    setUsername(event.target.value);
  };

  const firstNameOnChangeHandler = (event) => {
    setFirstName(event.target.value);
  };

  const lastNameOnChangeHandler = (event) => {
    setLastName(event.target.value);
  };

  const passwordOnChangeHandler = (event) => {
    setPassword(event.target.value);
  };

  const confirmPasswordOnChangeHandler = (event) => {
    setConfirmPassword(event.target.value);
  };

  const formSubmitHandler = (event) => {
    event.preventDefault();

    setErrors([]);

    if (password !== confirmPassword) {
      setErrors(["Password and confirm password don't match"]);
      return;
    }

    const newUser = {
      username,
      firstName,
      lastName,
      password,
    };

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newUser),
    };

    fetch("http://localhost:8080/api/create_account", init)
      .then((response) => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        }
        return Promise.reject("Something unexpected went wrong :)");
      })
      .then((data) => {
        if (data.id) {
          const init = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(newUser),
          };

          fetch("http://localhost:8080/api/authenticate", init)
            .then((response) => {
              if (response.status === 200) {
                return response.json();
              } else if (response.status === 403) {
                return null;
              }
              return Promise.reject("Something unexpected went wrong :)");
            })
            .then((data) => {
              if (data) {
                auth.login(data.jwt_token);
                history.push("/");
              } else {
                setErrors(["Login failure."]);
              }
            })
            .catch((error) => console.log(error));
        } else {
          setErrors(data.messages);
        }
      })
      .catch((error) => console.log(error));
  };

  return (
    <Container>
      <h2 className="my-4"style={{color: "white", background: "#375d83", width:"145px", height:"45px", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Register&nbsp;</h2>
      <Errors errors={errors} />
      <form onSubmit={formSubmitHandler}>
        <div className="form-group">
          <label htmlFor="username"style={{color: "white", background: "#375d83", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Email:&nbsp;</label>
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
          <label htmlFor="firstName"style={{color: "white", background: "#375d83", fontWeight: "500", borderRadius: "10px"}}>&nbsp;First Name:&nbsp;</label>
          <input
            className="form-control"
            type="text"
            id="firstName"
            name="firstName"
            value={firstName}
            onChange={firstNameOnChangeHandler}
          />
        </div>
        <div className="form-group">
          <label htmlFor="lastName"style={{color: "white", background: "#375d83", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Last Name:&nbsp;</label>
          <input
            className="form-control"
            type="text"
            id="lastName"
            name="lastName"
            value={lastName}
            onChange={lastNameOnChangeHandler}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password"style={{color: "white", background: "#375d83", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Password:&nbsp;</label>
          <input
            className="form-control"
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={passwordOnChangeHandler}
          />
        </div>
        <div className="form-group">
          <label htmlFor="confirmPassword"style={{color: "white", background: "#375d83", fontWeight: "500", borderRadius: "10px"}}>&nbsp;Confirm Password:&nbsp;</label>
          <input
            className="form-control"
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={confirmPassword}
            onChange={confirmPasswordOnChangeHandler}
          />
        </div>
        <div className="mt-5">
          <button className="btn btn-success" type="submit">
            <i className="bi bi-plus-circle-fill"></i> Register
          </button>
          <Link to="/" className="btn btn-warning ml-2">
            <i className="bi bi-x"></i> Cancel
          </Link>
        </div>
      </form>
    </Container>
  );
}

export default Register;
