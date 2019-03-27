package dbcommon;

import annotations.Entity;
import exceptions.NoEntityException;

import java.lang.reflect.Field;

public class ReflectionHelper {

    private ReflectionHelper(){

    }

    public static Object getFieldData(Object object,String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
    public static void setFieldData(Object instance,String fieldName,Object data) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance,data);
    }
    public static void checkClass(Class clazz) throws NoEntityException {
        if(!clazz.isAnnotationPresent(Entity.class))
            throw new NoEntityException("Class " + clazz.getName() + " is not entity");
    }

}
