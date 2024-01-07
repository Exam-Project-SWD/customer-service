package com.example.customerservice.mapper;

import java.util.Collections;
import java.util.List;

// Mappers decouple entities from DTOs and removes responsibility of mapping between them from other classes.
// It would be nice to use a third party library, but couldn't get MapStruct annotation processing to also work with Spring Boot.
// So we're rolling our own classes.
public interface Mapper<T, U> {
    T fromDto(U dto);

    U fromEntity(T entity);

    // Empty lists are immutable which might not be desired in all cases.
    default List<T> fromDto(List<U> dtoList) {
        if (dtoList == null) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(this::fromDto).toList();
    }

    default List<U> fromEntity(List<T> entityList) {
        if (entityList == null) {
            return Collections.emptyList();
        }
        return entityList.stream().map(this::fromEntity).toList();
    }
}
