package by.bsuir.app.dao;

import by.bsuir.app.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface with general methods of dao pattern for all entities
 *
 * @author Buyvid Dima
 */
public interface BaseDao <K,T extends BaseEntity> {
    /**
     * Method to find all matching entities in db
     * @return list of entities from db
     */
    List<T> findAll();

    /**
     * Method, which use to find entity on db by id, if its exists
     * @param id unique parameter, need to find entity on db
     * @return entity from db, if its exists
     */
    Optional<T> findById(Long id) ;

    /**
     * Method, which use to deleting entity from db by id
     * @param t - parameter of entity which will be deleted
     * @return boolean, that indicates success of deleting  (true - if entity
     * with id exists, not - false)
     */
    boolean delete(T t) ;

    /**
     * Method, which use to deleting entity from db by id
     * @param id - parameter that need to search entity on db
     * @return boolean, that indicates success of deleting  (true - if entity
     * with id exists, not - false)
     */
    boolean deleteById(Long id) ;

    /**
     * Method which creates or update entity on db
     * @param t - it is argument, which information will be taken
     * for creating or updating by comparing primary key
     * @return boolean, that indicates success of creating of entity
     */
    boolean saveOrUpdate(T t) ;
    //boolean update(T t) ;
}
