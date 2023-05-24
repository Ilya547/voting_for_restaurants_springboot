package ru.javaops.bootjava.web.mapper;

public interface RequestResponseMapper<E, RequestType, ResponseType> {

    E toEntity(RequestType dto);

    ResponseType toDto(E entity);
}
