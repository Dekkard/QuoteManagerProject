package com.quotemanager.repository;

import java.util.List;
import java.util.Optional;

public interface ServiceModel<Entity,EntityId> {
	public List<Entity> list();
	public Optional<Entity> find(EntityId id);
	public Optional<Entity> insert(Entity entity);
	public Optional<Entity> update(EntityId id, Entity entity);
	public void delete(EntityId id);
}
