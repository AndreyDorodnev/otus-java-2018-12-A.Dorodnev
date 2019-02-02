package framework;

import annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public final class MyTestFramework {

    private static final String BEFORE_EACH = BeforeEach.class.getName();
    private static final String BEFORE_ALL = BeforeAll.class.getName();
    private static final String AFTER_EACH = AfterEach.class.getName();
    private static final String AFTER_ALL = AfterAll.class.getName();
    
    private MyTestFramework(){

    }

    public static void run(String className) {
        try{
            Class<?> testClass = Class.forName(className);
            run(testClass);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void run(Class<?> testClass) {
        Method[] testMethods = Arrays.stream(testClass.getDeclaredMethods()).filter(method -> {
            if(method.isAnnotationPresent(Test.class))
                return true;
            return false;
        }).toArray(Method[]::new);

        Method[] methods = testClass.getDeclaredMethods();
        HashMap<String, ArrayList<Method>> methodHashMap = createAnnotationMethodsMap(methods);

        invokeStaticMethodWithAnnotation(testClass, methodHashMap.get(BEFORE_ALL));
        for (Method method:testMethods){
            Object instance =  ReflectionHelper.instantiate(testClass);
            invokeMethodWithAnnotation(instance,methodHashMap.get(BEFORE_EACH));
            ReflectionHelper.callMethod(instance,method.getName(),method.getParameterTypes());
            invokeMethodWithAnnotation(instance,methodHashMap.get(AFTER_EACH));
        }
        invokeStaticMethodWithAnnotation(testClass,methodHashMap.get(AFTER_ALL));
    }

    private static HashMap<String, ArrayList<Method>> createAnnotationMethodsMap(Method[] methods){
        HashMap<String, ArrayList<Method>> methodHashMap = new HashMap<>();
        methodHashMap.put(BEFORE_EACH,new ArrayList<>());
        methodHashMap.put(BEFORE_ALL,new ArrayList<>());
        methodHashMap.put(AFTER_ALL,new ArrayList<>());
        methodHashMap.put(AFTER_EACH,new ArrayList<>());
        for(Method method:methods){
            if(method.isAnnotationPresent(BeforeEach.class)){
                methodHashMap.get(BEFORE_EACH).add(method);
            }else if(method.isAnnotationPresent(BeforeAll.class)){
                methodHashMap.get(BEFORE_ALL).add(method);
            }else if(method.isAnnotationPresent(AfterEach.class)){
                methodHashMap.get(AFTER_EACH).add(method);
            }else if(method.isAnnotationPresent(AfterAll.class)){
                methodHashMap.get(AFTER_ALL).add(method);
            }
        }
        return methodHashMap;
    }
    private static void invokeStaticMethodWithAnnotation(Class<?> instance,ArrayList<Method> methodsList){
        if(methodsList!=null){
            for (Method method: methodsList){
                ReflectionHelper.callStaticMethod(instance,method.getName(),method.getParameterTypes());
            }
        }
    }
    private static void invokeMethodWithAnnotation(Object instance,ArrayList<Method> methodsList){
        if(methodsList!=null){
            for (Method method: methodsList){
                ReflectionHelper.callMethod(instance,method.getName(),method.getParameterTypes());
            }
        }
    }

}
