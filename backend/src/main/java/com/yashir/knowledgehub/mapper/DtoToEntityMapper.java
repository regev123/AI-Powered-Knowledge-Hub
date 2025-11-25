package com.yashir.knowledgehub.mapper;

/**
 * Interface for mapping DTO objects to Entity objects
 * @param <D> DTO type
 * @param <E> Entity type
 */
public interface DtoToEntityMapper<D, E> {
    /**
     * Maps a DTO to an Entity
     * @param dto the DTO to map
     * @return the mapped Entity
     */
    E toEntity(D dto);
}

