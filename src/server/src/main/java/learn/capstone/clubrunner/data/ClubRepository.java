package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Club;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClubRepository {
    List<Club> findAll();

    @Transactional
    Club findById(int clubId);
}
