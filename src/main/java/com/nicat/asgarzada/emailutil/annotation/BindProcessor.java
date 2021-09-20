package com.nicat.asgarzada.emailutil.annotation;

import com.nicat.asgarzada.emailutil.exception.BindProcessorException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Processes {@link Bind} annotation. first invokes getter of field,
 * then gets annotation key method and puts to map.
 *
 *
 * @see Bind
 * @author nasgarzada
 * @version 1.0.0
 */
public final class BindProcessor {
    private BindProcessor() {

    }

    /**
     * @param object any object
     * @return key value pair. Key comes from annotation {@link Bind#key()} method, value comes from field value
     */
    public static Map<String, String> process(Object object) {
        var bindingResult = new HashMap<String, String>();
        var clazz = object.getClass();
        var declaredFields = clazz.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            var fieldValue = invokeMethod(object, getterName(field), field);
            var annotation = field.getAnnotation(Bind.class);

            if (fieldValue instanceof String) {
                bindingResult.put(annotation.key(), (String) fieldValue);
            } else {
                throw new BindProcessorException(
                        String.format("field value type [%s] is different from [String] type",
                                field.getType().getSimpleName())
                );
            }

        });

        return bindingResult;
    }

    /**
     * @param field class field
     * @return generates getter name of class field. Please, generate getter with java standard.
     */
    private static String getterName(Field field) {
        return "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
    }

    /**
     * @param object any object
     * @param getterName name of getter of field
     * @param field class field
     * @return invoking getter of given field and return the result
     */
    private static Object invokeMethod(Object object, String getterName, Field field) {
        try {
            var clazz = object.getClass();
            return clazz.getDeclaredMethod(getterName).invoke(object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new BindProcessorException("failed to invoking method: " + getterName, e);
        }
    }
}
