import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import UserProfile from "./components/UserProfile";
import Runs from "./components/Runs";
import AddRun from "./components/AddRun";
import EditRun from "./components/EditRun";
import DeleteRun from "./components/DeleteRun";
import Clubs from "./components/Clubs";
import Home from "./components/Home";
import About from "./components/About";
import Header from "./components/Header";
import NotFound from "./components/NotFound";

function App() {
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
          <UserProfile />
        </Route>

        <Route path="/runs">
          <Runs />
        </Route>

        <Route path="/runs/add">
          <AddRun />
        </Route>

        <Route path="/runs/edit/:run_id">
          <EditRun />
        </Route>

        <Route path="/runs/delete/:run_id">
          <DeleteRun />
        </Route>

        <Route path="/clubs">
          <Clubs />
        </Route>

        <Route path="*">
          <NotFound />
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
