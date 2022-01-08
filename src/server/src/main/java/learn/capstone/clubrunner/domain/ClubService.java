package learn.capstone.clubrunner.domain;

import learn.capstone.clubrunner.data.ClubRepository;
import learn.capstone.clubrunner.models.Club;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {

    private final ClubRepository repository;

    public ClubService(ClubRepository repository) {this.repository = repository;}

    public List<Club> findAll() {return repository.findAll();}

    public Club findById(int clubId) {return repository.findById(clubId);}
}
