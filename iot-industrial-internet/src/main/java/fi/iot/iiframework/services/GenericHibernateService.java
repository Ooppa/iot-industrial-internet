/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services;

import fi.iot.iiframework.daos.GenericDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.criterion.Criterion;

@Transactional
public class GenericHibernateService<T, ID extends Serializable>
        implements GenericService<T, ID> {

    protected GenericDAO dao;

    @Override
    public T save(T t) {
        return (T) dao.save(t);
    }

    @Override
    public T update(T t) {
        return (T) dao.update(t);
    }

    @Override
    public T merge(T t) {
        return (T) dao.merge(t);
    }

    @Override
    public T get(ID id) {
        return (T) dao.get(id);
    }

    @Override
    public List<T> get(int from, int to) {
        return dao.get(from, to);
    }

    @Override
    public void delete(T t) {
        dao.remove(t);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public List<T> getBy(int from, int to, List<Criterion> criterion) {
        return dao.findByCriteriaFromTo(from, to, criterion);
    }

    @Override
    public Long countByCriteria(List<Criterion> criterion) {
        return dao.countByCriteria(criterion);
    }

    @Override
    public Long count() {
        return countByCriteria(new ArrayList<>());
    }

    protected List<Criterion> buildCriterionList(Criterion... criterion) {
        List<Criterion> list = new ArrayList<>();
        list.addAll(Arrays.asList(criterion));
        return list;
    }

    @Override
    public List<T> save(List<T> lt) {
        lt.forEach(t -> dao.save(t));
        return lt;
    }

    @Override
    public void flush() {
        dao.flush();
    }

    @Override
    public void clear() {
        dao.clear();
    }

}
