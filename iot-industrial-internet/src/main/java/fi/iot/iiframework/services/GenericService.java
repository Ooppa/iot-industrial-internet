/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.services;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;

public interface GenericService<T, ID extends Serializable> {
    public void save(T t);
    
    public void save(List<T> lt);

    public T get(ID id);
    
    public List<T> get(int from, int to);
    
    public List<T> getBy(int from, int to, List<Criterion> criterion);

    public void delete(T t);

    public List<T> getAll();
    
    public Long countByCriteria(List<Criterion> criterion);
    
    public Long count();
    
}
