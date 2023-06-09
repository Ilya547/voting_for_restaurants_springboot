package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.error.IllegalRequestDataException;
import ru.javaops.bootjava.model.AbstractEntity;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(AbstractEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
//    public static void assureIdConsistent(AbstractEntity entity, int id) {
//        if (entity.isNew()) {
//            entity.setId((long) id);
//        } else if (entity.id() != id) {
//            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must has id=" + id);
//        }
//    }
}