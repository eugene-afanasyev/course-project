package main.services;

import main.dao.DBUserDAO;
import main.dao.UserDAO;
<<<<<<< HEAD
import main.models.Champ;
import main.models.Championship;
import main.models.Discipline;
import main.models.User;
=======
import main.models.*;
>>>>>>> dbrefactoring

import java.util.Enumeration;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UserService<T extends UserDAO> implements EntityService<User> {
    private final Supplier<T> supplier;

    public UserService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public User find( int id) {
        return supplier.get().findById(id);
    }

    @Override
    public void save(User user) {
        supplier.get().save(user);
    }

    @Override
    public void delete(User user) {
        supplier.get().remove(user);
    }

    @Override
    public void update(User user) {
        supplier.get().update(user);
    }

    @Override
    public List<User> findAll() {
        return supplier.get().findAll();
    }

    public void updateLogin(int id, String login){
        supplier.get().updateLogin(id, login);
    }
    public void updatePassword(int id, String password){
        supplier.get().updatePassword(id, password);
    }
    public User findByLogin(String login){
        try {
            var user = supplier.get().findByLogin(login);
            return supplier.get().findByLogin(login);
        }
        catch (Exception ex){
            return null;
        }
    }
    public List<User> findByName(String firstName, String lastName){
        return supplier.get().findByName(firstName, lastName);
    }

<<<<<<< HEAD
    public List<User> findAllVolunteers(){
        List<User> users = findAll();
        return users.stream().filter((user) ->
                user.getRole().getName().equals("Volunteer")
        ).collect(Collectors.toList());
    }

    public List<User> findAllVolunteers(Championship championship){
        var users = championship.getUsers();
        return users.stream().filter((user) ->
                user.getRole().getName().equals("Volunteer") && user.getResults().get(0).getChampionship().id == championship.id
        ).collect(Collectors.toList());
    }

    public List<User> findAllVolunteers( Championship championship, Discipline discipline ){
        var users = championship.getUsers();
        return users.stream().filter((user) ->
                user.getRole().getName().equals("Volunteer") &&
                        user.getResults().get(0).getChampionship().id == championship.id &&
                        user.getResults().get(0).getDiscipline().getId() == discipline.getId()
        ).collect(Collectors.toList());
=======
    public void updateChampionship(User user, Championship championship){
       supplier.get().updateChampionship(user.getId(), championship);
    }

    public void updateDiscipline(User user, Discipline discipline){
        supplier.get().updateDiscipline(user.getId(), discipline);
    }

    public void updateRegion(User user, Region region){
        supplier.get().updateRegion(user.getId(), region);
    }

    public void updateRole( User user, Role role ){
        supplier.get().updateRole(user.getId(), role);
>>>>>>> dbrefactoring
    }
}
