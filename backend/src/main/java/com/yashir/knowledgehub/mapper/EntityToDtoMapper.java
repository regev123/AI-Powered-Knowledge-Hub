package com.yashir.knowledgehub.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface for mapping Entity objects to DTO objects
 * @param <E> Entity type
 * @param <D> DTO type
 */
public interface EntityToDtoMapper<E, D> {
    /**
     * Maps an Entity to a DTO
     * @param entity the entity to map
     * @return the mapped DTO
     */
    D toDto(E entity);

    /**
     * Maps a list of Entities to a list of DTOs
     * @param entities the entities to map
     * @return the mapped DTOs
     */
    default List<D> toDtoList(List<E> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

