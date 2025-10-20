package com.atlasbase.atlasbase_core.infrastructure.persistence.mapper;

public interface Mapper<D, E> {

	D toDomain(E entity);

	E toEntity(D domain);

}
