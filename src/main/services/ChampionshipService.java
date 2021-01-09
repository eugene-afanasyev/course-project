package main.services;

import main.dao.ChampionshipDAO;
import main.models.Championship;
import main.models.Discipline;
import main.models.Region;
import main.models.User;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ChampionshipService<T extends ChampionshipDAO> implements EntityService<Championship> {
    private final Supplier<T> supplier;

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
     * Используется для поиска экспертов на определенном чемпионате
     * @param championship - чемпионат, экспертов которого необходимо найти
     * @return - список экспертов
     */
    public List<User> getExperts(Championship championship){
        List<User> users = championship.getUsers();
        return users.stream().filter((user) -> user.getRole().getName().equals("Expert")).collect(Collectors.toList());
    }

    /***
     * Используется для поиска экспертов на чемпионате по определенной дисциплине
     * @param championship - чемпионат, на котором производим поиск
     * @param discipline - дисциплина, на которой производится поиск
     * @return - список экспертов на данном чемпионате по данной дисциплине
     */
    public List<User> getExperts(Championship championship, Discipline discipline){
        List<User> users = championship.getUsers();
        return users.stream().filter((user) ->
            user.getRole().getName().equals("Expert")
                    && user.getResults().get(0).getDiscipline().getDisciplineCode().equals(discipline.getDisciplineCode())
        ).collect(Collectors.toList());
    }

    /***
     * Используется для поиска участников на определенном чемпионате
     * @param championship - чемпионат, экспертов которого необходимо найти
     * @return - список участников
     */
    public List<User> getParticipants( Championship championship){
        List<User> users = championship.getUsers();
        return users.stream().filter((user) -> user.getRole().getName().equals("Competitor")).collect(Collectors.toList());
    }

    /***
     * Используется для поиска участников на чемпионате по определенной дисциплине
     * @param championship - чемпионат, на котором производим поиск
     * @param discipline - дисциплина, на которой производится поиск
     * @return - список участников на данном чемпионате по данной дисциплине
     */
    public List<User> getParticipants( Championship championship, Discipline discipline){
        List<User> users = championship.getUsers();
        return users.stream().filter((user) ->
                user.getRole().getName().equals("Competitor")
                        && user.getResults().get(0).getDiscipline().getDisciplineCode().equals(discipline.getDisciplineCode())
        ).collect(Collectors.toList());
    }
}
