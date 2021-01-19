package main.dao;

import main.models.*;

import java.util.Enumeration;
import java.util.List;

public interface UserDAO extends DataAccessObject<User>{
    User findByLogin(String login);
    List<User> findByName(String firstName, String lastName);
    void updatePassword(int id, String password);
    void updateChampionship( int id, Championship championship );
    void updateDiscipline( int id, Discipline discipline );
    void updateLogin(int id, String login);
    void updateRegion( int id, Region region );
    void updateRole(int id, Role role);
}
