package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.ClubRepository;
import learn.capstone.clubrunner.models.Club;
import learn.capstone.clubrunner.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {

    private final ClubRepository repository;

    public ClubService(ClubRepository repository) {this.repository = repository;}

    public List<Club> findAll() {return repository.findAll();}

    public Result<Club> findById(int clubId) {
        Result<Club> result = new Result<>();
        result.setPayload(repository.findById(clubId));

        if (result.getPayload() == null) {
            String msg = String.format("clubId: %s not found", clubId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }
}
