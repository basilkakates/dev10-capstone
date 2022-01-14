import { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import jwt_decode from "jwt-decode";

import AuthContext from "./AuthContext";
import UserProfile from "./components/UserProfile";
import Runs from "./components/Runs";
import PendingRuns from "./components/PendingRuns";
import ApproveRun from "./components/ApproveRun";
import DeleteRun from "./components/DeleteRun";
import CancelRun from "./components/CancelRun";
import Clubs from "./components/Clubs";
import About from "./components/About";
import Header from "./components/Header";
import NotFound from "./components/NotFound";
import Login from "./components/Login";
import Register from "./components/Register";

const TOKEN_KEY = "user-api-topken";

function App() {
  const [user, setUser] = useState(null);
  const [userProfile, setUserProfile] = useState({});
  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem(TOKEN_KEY);

    if (token) {
      login(token);
    }

    if (user !== null && user.username) {
      getUser();
    } else {
      setUserProfile({})
    }

    setInitialized(true);
  }, [user]);

  const login = (token) => {
    const { id, sub: username, roles: userRoles } = jwt_decode(token);

    const roles = userRoles?.split(",");

    const user = {
      id,
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      },
    };

    console.log(user);

    setUser(user);
    return user;
  };

  const getUser = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/user/email/${user.username}`);
      const data = await response.json();
      setUserProfile(data);
      console.log(data)
    } catch (error) {
      console.log(error);
    }
  };

  const logout = () => {
    localStorage.removeItem(TOKEN_KEY);
    setUser(null);
  };

  const auth = {
    user: user ? { ...user } : null,
    login,
    logout,
  };

  if (!initialized) {
    return null;
  }

  const DEFAULT_USER = {
    userId: 5,
    firstName: "Testy",
    lastName: "McTest",
    email: "tmctest@test.com",
  };

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <Header user={userProfile} />
        <Switch>
          <Route exact path={"/"}>
            <Runs user={userProfile} />
          </Route>

          <Route exact path={"/runs"}>
            <Runs user={userProfile} />
          </Route>

          <Route exact path="/about">
            <About />
          </Route>

          <Route exact path="/userprofile">
            {user ? <UserProfile user={userProfile}/> : <Redirect to="/login" />}
          </Route>

          <Route exact path="/runs/pending">
            {user ? <PendingRuns user={userProfile}/> : <Redirect to="../login" />}
          </Route>

          <Route path="/runs/approve/:run_id">
            {user ? <ApproveRun /> : <Redirect to="../../login" />}
          </Route>

          <Route path="/runs/delete/:run_id">
            {user ? <DeleteRun /> : <Redirect to="../../login" />}
          </Route>

          <Route path="/runs/cancel/:run_id">
            {user ? <CancelRun /> : <Redirect to="../../login" />}
          </Route>

          <Route path="/clubs">
            {user ? <Clubs /> : <Redirect to="login" />}
          </Route>

          <Route path="/login">
            <Login />
          </Route>

          <Route path="/register">
            <Register />
          </Route>

          <Route path="*">
            <NotFound />
          </Route>
        </Switch>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
