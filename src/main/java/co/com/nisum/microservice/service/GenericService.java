package co.com.nisum.microservice.service;

import java.util.Optional;

public interface GenericService <T,I>{

    public T save(T entity);

    public T update(T entity);

    public void delete(T entity);

    public Optional<T> findById(I clieId);

    public void validate(T entity);

}
