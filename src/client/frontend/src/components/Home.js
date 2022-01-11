import Container from "react-bootstrap/Container";
import Runs from "./Runs";
import { useState } from "react";
import PlacesAutocomplete from "./PlacesAutocomplete";

function Home() {
  const [address, setAddress] = useState("");
  const addressOnChangeHandler = (event) => {
    setAddress(event.target.value);
  };
  return (
    <Container>
      <h2 className="my-4">
        Welcome to Club Runner. Please Login or Register to sign up for runs.
      </h2>
      {/* <Runs /> */}
      <PlacesAutocomplete />
      <input
        type="text"
        id="address"
        name="address"
        value={address}
        onChange={addressOnChangeHandler}
      />
    </Container>
  );
}

export default Home;
