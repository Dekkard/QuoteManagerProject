package com.quotemanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;

/**
 * ServiceModel: Interface used to guide the construction of JPA Services
 * 
 * @param Entity   Generic attribute that defines which data is suppose to be
 *                 retrived or sent
 * @param EntityId Type of id that's used to identify the object in the DataBase
 */
public interface ServiceModel<Entity, EntityId> {
	/**
	 * {@code list()} Signature for methods with the intention to produce a list of
	 * entities
	 * 
	 * @return List containing all entries of a entity.
	 */
	public List<Entity> list();

	/**
	 * {@code find()} Signature used to return a object based on the id provided
	 * 
	 * @param id Identification attribute to retrive the object
	 * @return Optional container of the object
	 */
	public Optional<Entity> find(@Validated EntityId id);

	/**
	 * {@code insert()} Signature used to inset a certain object in the DataBase
	 * 
	 * @param entity object to be inserted
	 * @return Optional container with the inserted object
	 */
	public Optional<Entity> insert(@Validated Entity entity);

	/**
	 * {@code update()} Signature used to update a object already inserted to the
	 * DataBase
	 * 
	 * @param id     Identification attribute to retrive the object
	 * @param entity object to be updated
	 * @return Optional container with the updated object
	 */
	public Optional<Entity> update(EntityId id, @Validated Entity entity);

	/**
	 * {@code delete()} Signature used to indicate a object to be removed from the
	 * DataBase
	 * 
	 * @param entity object to be deleted
	 */
	public void delete(EntityId id);
}
