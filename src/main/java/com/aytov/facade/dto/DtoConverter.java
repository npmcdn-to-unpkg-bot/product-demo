package com.aytov.facade.dto;

import com.aytov.domain.model.AbstractEntity;

import java.util.Collection;

public interface DtoConverter<T extends Transferable, E extends AbstractEntity> {
    /**
     * Transforms an Entity to a DTO.
     * @param o
     * @return
     */
    T asDto(E o);

    /**
     * Transforms a list of Entities to a
     * @param entities
     * @return
     */
    BaseListDto<T> asDto(Collection<E> entities);

    /**
     * Transforms and a DTO to an Entity.
     * @param dto
     * @return
     */
    E asEntity(T dto);
}
