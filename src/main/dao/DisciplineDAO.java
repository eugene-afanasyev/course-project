package main.dao;

import main.models.Discipline;

public interface DisciplineDAO extends DataAccessObject<Discipline> {
    Discipline findByName(String name);
    Discipline findByCode(String code);
    void changeRuName(int id, String ruName);
    void changeDescription(int id, String description);
}
