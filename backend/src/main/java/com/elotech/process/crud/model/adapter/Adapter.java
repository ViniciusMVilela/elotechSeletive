package com.elotech.process.crud.model.adapter;

public interface Adapter<Dto, Entity> {
    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
}
