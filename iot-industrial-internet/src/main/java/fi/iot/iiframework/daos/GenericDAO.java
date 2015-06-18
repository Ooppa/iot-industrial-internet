/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.daos;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;

public interface GenericDAO<T, ID extends Serializable> {

    /**
     * Save an object to database.
     *
     * @param t object to be saved
     * @return T
     */
    public T save(T t);

    /**
     * Persist an object to database.
     *
     * @param t
     * @return
     */
    public T merge(T t);

    /**
     * Update an object in the database.
     *
     * @param t
     * @return
     */
    public T update(T t);

    /**
     * Get an object with the given id.
     *
     * @param id id of the object
     * @return T
     */
    public T get(ID id);

    /**
     * Retrieves all objects of type T in the database.
     *
     * @return T
     */
    public List<T> getAll();

    /**
     * Retrieves objects from index from to index to.
     *
     * @param from
     * @param to
     * @return T
     */
    public List<T> get(int from, int to);

    /**
     * Removes an object with the given id.
     *
     * @param t to be removed
     */
    public void remove(T t);

    public List<T> findByCriteriaFromTo(int from, int to, List<Criterion> list);

    public Long countByCriteria(List<Criterion> list);

    public void flush();

    public void clear();
}
