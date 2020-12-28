package main.services;

import main.dao.DataAccessObject;
import main.models.User;

import java.util.List;

public interface EntityService<EntityType>{
    public EntityType find(int id);
    public void save(EntityType entity);
    public void delete(EntityType entity);
    public void update(EntityType entity);
    public List<EntityType> findAll();
}
