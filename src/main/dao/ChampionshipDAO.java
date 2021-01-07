package main.dao;

import main.models.Championship;
import main.models.Discipline;
import main.models.User;

public interface ChampionshipDAO extends DataAccessObject<Championship>{
    void addUser(Championship champ, User user);
    void addDiscipline( Championship champ, Discipline discipline );
}
