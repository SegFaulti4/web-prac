package ru.msu.cmc.webprac.DAO;

import java.util.Collection;

public interface CommonDAO<T, ID> {
    Collection<T> getAll();

    T getByID(ID id);

    void save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void update(T entity);
}
