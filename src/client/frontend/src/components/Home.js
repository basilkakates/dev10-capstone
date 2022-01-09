import Container from "react-bootstrap/Container";
import Runs from "./Runs";

function Home() {
  return (
    <Container>
      <h2 className="my-4">
        Welcome to Club Runner. Please Login or Register to sign up for runs.
      </h2>
      <Runs />
    </Container>
  );
}

export default Home;
