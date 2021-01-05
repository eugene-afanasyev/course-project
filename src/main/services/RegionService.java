package main.services;

import main.dao.RegionDAO;
import main.models.Region;
import main.models.Role;

import java.util.List;
import java.util.function.Supplier;

public class RegionService<T extends RegionDAO> implements EntityService<Region> {
    private final Supplier<T> supplier;

    public RegionService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Region find( int id) {
        return supplier.get().findById(id);
    }

    @Override
    public void save(Region region) {
        supplier.get().save(region);
    }

    @Override
    public void delete( Region region) {
        supplier.get().remove(region);
    }

    @Override
    public void update(Region region) {
        supplier.get().update(region);
    }

    @Override
    public List<Region> findAll() {
        return supplier.get().findAll();
    }
}
