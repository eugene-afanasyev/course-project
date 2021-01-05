package main.services;

import main.dao.DisciplineDAO;
import main.models.Discipline;
import main.models.Region;

import java.util.List;
import java.util.function.Supplier;

public class DisciplineService<T extends DisciplineDAO> implements EntityService<Discipline> {
    private final Supplier<T> supplier;

    public DisciplineService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Discipline find( int id) {
        return supplier.get().findById(id);
    }

    @Override
    public void save(Discipline region) {
        supplier.get().save(region);
    }

    @Override
    public void delete( Discipline discipline) {
        supplier.get().remove(discipline);
    }

    @Override
    public void update(Discipline discipline) {
        supplier.get().update(discipline);
    }

    @Override
    public List<Discipline> findAll() {
        return supplier.get().findAll();
    }
}
