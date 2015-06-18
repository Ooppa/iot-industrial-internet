/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.daos;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class GenericHibernateDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

    private final Class<T> persistentClass;
    protected final List<Order> defaultOrder = new ArrayList<>();

    public GenericHibernateDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public T save(T t) {
        getSession().saveOrUpdate(t);
        return t;
    }

    @Override
    public T update(T t) {
        getSession().update(t);
        return t;
    }

    @Override
    public T merge(T t) {
        getSession().merge(t);
        return t;
    }

    /**
     * Read all objects that fulfill the criteria
     *
     * @param criterion
     * @return
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(List<Criterion> criterion) {

        return findByCriteriaFromTo(0, Integer.MAX_VALUE, criterion);
    }

    /**
     * Read all objects that fulfill the criteria from index from to index to
     *
     * @param from
     * @param to
     * @param criterion
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteriaFromTo(int from, int to, List<Criterion> criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass())
                .setFirstResult(from)
                .setMaxResults((to + 1) - from);
        criterion.stream().forEach((c) -> {
            crit.add(c);
        });
        defaultOrder.stream().forEach(c -> {
            crit.addOrder(c);
        });
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long countByCriteria(List<Criterion> criterion) {
        Criteria crit = getSession().createCriteria(getPersistentClass())
                .setProjection(Projections.rowCount());
        criterion.stream().forEach((c) -> {
            crit.add(c);
        });
        return (Long) crit.uniqueResult();
    }

    /**
     * Get an object of type T with the given id.
     *
     * @param id
     * @return
     */
    @Override
    public T get(ID id) {
        return (T) getSession().get(getPersistentClass(), id);
    }

    /**
     * Get all objects of type T
     *
     * @return
     */
    @Override
    public List<T> getAll() {
        return findByCriteria(new ArrayList<>());
    }

    /**
     * Get all objects of type T from index from to index to.
     *
     * @param from
     * @param to
     * @return
     */
    @Override
    public List<T> get(int from, int to) {
        return findByCriteriaFromTo(from, to, new ArrayList<>());
    }

    /**
     * Removes an object from the database.
     *
     * @param t
     */
    @Override
    public void remove(T t) {
        getSession().delete(t);
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

}
