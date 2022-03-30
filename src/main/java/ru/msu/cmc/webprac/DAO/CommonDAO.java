package ru.msu.cmc.webprac.DAO;

import java.util.Collection;

public interface CommonDAO<T> {
    Collection<T> getAll();

    void save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void update(T entity);
}
