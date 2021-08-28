package com.locatel.prueba.services.interfaces;

import java.util.List;

public interface IService<Entity, Key> {
    List<Entity> all();

    Entity find(Key id);

    Entity create(Entity model);

    Entity update(Key id, Entity model);

    Object delete(Key id);
}
