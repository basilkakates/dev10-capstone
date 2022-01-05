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
import AddRun from "./components/AddRun";
import EditRun from "./components/EditRun";
import DeleteRun from "./components/DeleteRun";
import CancelRun from "./components/CancelRun";
import Clubs from "./components/Clubs";
import Home from "./components/Home";
import About from "./components/About";
import Header from "./components/Header";
import NotFound from "./components/NotFound";
import Login from "./components/Login";
import Register from "./components/Register";

// const TOKEN_KEY = "user-api-topken";

function App() {
  // const [user, setUser] = useState(null);
  // const [initialized, setInitialized] = useState(false);

  // useEffect(() => {
  //   const token = localStorage.getItem(TOKEN_KEY);

  //   if (token) {
  //     login(token);
  //   }

  //   setInitialized(true);
  // }, []);

  // const login = (token) => {
  //   const { id, sub: username, roles: userRoles } = jwt_decode(token);

  //   const roles = userRoles?.split(",");

  //   const user = {
  //     id,
  //     username,
  //     roles,
  //     token,
  //     hasRole(role) {
  //       return this.roles.includes(role);
  //     },
  //   };

  //   console.log(user);

  //   setUser(user);

  //   return user;
  // };

  // const logout = () => {
  //   localStorage.removeItem(TOKEN_KEY);
  //   setUser(null);
  // };

  // const auth = {
  //   user: user ? { ...user } : null,
  //   login,
  //   logout,
  // };

  // if (!initialized) {
  //   return null;
  // }

  return (
    <Router>
      <Header />
      <Switch>
        <Route exact path="/">
          <Home />
        </Route>

        <Route exact path="/about">
          <About />
        </Route>

        <Route exact path="/userprofile/:user_id">
          {/* {user ? <UserProfile /> : <Redirect to="/login" />} */}
        </Route>

        <Route exact path="/runs">
          {/* {user ? <Runs /> : <Redirect to="login" />} */}
          <Runs />
        </Route>

        <Route path="/runs/pending">
          <PendingRuns />
        </Route>

        <Route path="/runs/add">
          {/* {user ? <AddRun /> : <Redirect to="login" />} */}
          <AddRun />
        </Route>

        <Route path="/runs/edit/:run_id">
          {/* {user ? <EditRun /> : <Redirect to="login" />} */}
          <EditRun />
        </Route>

        <Route path="/runs/delete/:run_id">
          {/* {user ? <DeleteRun /> : <Redirect to="login" />} */}
          <DeleteRun />
        </Route>

        <Route path="/runs/cancel/:run_id">
          <CancelRun />
        </Route>

        <Route path="/clubs">
          {/* {user ? <Clubs /> : <Redirect to="login" />} */}
          <Clubs />
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
    // </AuthContext.Provider>
  );
}

export default App;
