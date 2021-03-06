package learn.capstone.clubrunner.data;

import learn.capstone.clubrunner.models.Club;

import java.util.List;

public interface ClubRepository {
    List<Club> findAll();

    Club findById(int clubId);
}
