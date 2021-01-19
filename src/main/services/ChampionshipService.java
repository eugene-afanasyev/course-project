package main.services;

import main.dao.ChampionshipDAO;
import main.dao.DBUserDAO;
import main.models.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ChampionshipService<T extends ChampionshipDAO> implements EntityService<Championship> {
    private final Supplier<T> supplier;
    private final UserService<DBUserDAO> userService = new UserService<>(DBUserDAO::new);

    public ChampionshipService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Championship find( int id) {
        return supplier.get().findById(id);
    }

    @Override
    public void save(Championship championship) {
        supplier.get().save(championship);
    }

    @Override
    public void delete( Championship championship) {
        supplier.get().remove(championship);
    }

    @Override
    public void update(Championship championship) {
        supplier.get().update(championship);
    }

    @Override
    public List<Championship> findAll() {
        return supplier.get().findAll();
    }

    public void addUser( Championship champ, User user ){
        supplier.get().addUser(champ, user);
    }

    /***
     * Используется для добавления новой дисциплины к чемпионату
     * @param champ - чемпионат, к которгому добавляется новая дисциплина
     * @param disc - дисциплина, которая добавляется к чемпионату
     */
    public void addDiscipline( Championship champ, Discipline disc ){
        supplier.get().addDiscipline(champ, disc);
    }

    /***
     * Используется для поиска пользователей по какой-либо роли
     * @param roleName - название роли, пользователей с которой нужно найти
     * @return - список пользователей с соответствующей ролью
     */
    public List<User> findAllByRole(String roleName){
       return userService.findAll().stream().filter((user) -> user.getRole().getName().equals(roleName)).collect(Collectors.toList());
    }

    /***
     * Поиск пользователей, участвующих на чемпионате и имеющих соответствующую роль
     * @param roleName - роль искомых пользователей
     * @param championship - чемпионат, на котором производится поиск
     * @return - список пользователей
     */
    public List<User> findAllByRole( String roleName, Championship championship ){
        return userService.findAll().stream().filter((user) ->
                user.getRole().getName().equals(roleName) &&
                        user.getChampionship().id == championship.id
        ).collect(Collectors.toList());
    }

    /***
     * Поиск пользователей, участвующих на чемпионате в определенной компетенции и имеющих соответствующую роль
     * @param roleName - роль искомых пользователей
     * @param championship - чемпионат, на котором производится поиск
     * @param discipline - дисциплина, на которой производится поиск
     * @return
     */
    public List<User> findAllByRole( String roleName, Championship championship, Discipline discipline){
        return userService.findAll().stream().filter((user) ->
                user.getRole().getName().equals(roleName) &&
                        user.getChampionship().id == championship.id &&
                        user.getDiscipline().getId() == discipline.getId()
        ).collect(Collectors.toList());
    }
}
