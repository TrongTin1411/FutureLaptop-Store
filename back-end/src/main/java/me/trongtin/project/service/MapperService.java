package me.trongtin.project.service;

public interface MapperService<T, DTO> {

    DTO mapper(T t);

}
