package main.services;

import main.dao.ResultDAO;
import main.models.*;

import java.util.List;
import java.util.function.Supplier;

public class ResultService<T extends ResultDAO> implements EntityService<Result> {
    private final Supplier<T> supplier;

    public ResultService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Result find( int id) {
        return supplier.get().findById(id);
    }

    @Override
    public void save(Result region) {
        supplier.get().save(region);
    }

    @Override
    public void delete( Result result) {
        supplier.get().remove(result);
    }

    @Override
    public void update(Result result) {
        supplier.get().update(result);
    }

    @Override
    public List<Result> findAll() {
        return supplier.get().findAll();
    }
    public void saveWithReferences( Result result, User user, Championship championship, Discipline discipline){

    }
}
