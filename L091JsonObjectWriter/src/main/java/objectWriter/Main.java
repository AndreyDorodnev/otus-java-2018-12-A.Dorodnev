package objectWriter;

import com.google.gson.Gson;
import model.Adress;
import model.FullAdress;
import model.User;
import model.UserWithCollection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        objectTest();
        arrayListTest();
        arrayTest();
        userWithCollectionTest();
        simpleArrayTest();
        simpleArrayListTest();
        mapTest();
    }

    private static void objectTest() throws IllegalAccessException {
        System.out.println("Object to json test");
        User user = new User("John",26,new FullAdress("street","New York",10));
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);
        System.out.println(jsonStr);
        JsonObjectWriter serializer = new JsonObjectWriter();
        String json = serializer.toJson(user);
        System.out.println(json);
    }

    private static void arrayListTest() throws IllegalAccessException {
        System.out.println("Arraylist of objects test");
        ArrayList<User> users = new ArrayList<User>();
        users.add(new User("John",26,new FullAdress("street1","New York",10)));
        users.add(new User("Dave",27,new FullAdress("street2","Moscow",11)));
        users.add(new User("Tom",28,new FullAdress("street3","Amsterdam",12)));
        Gson gson = new Gson();
        String jsonStr = gson.toJson(users);
        System.out.println(jsonStr);
        JsonObjectWriter serializer = new JsonObjectWriter();
        System.out.println(serializer.toJson(users));
    }

    private static void arrayTest() throws IllegalAccessException {
        System.out.println("Array of objects test");
        User[] users = new User[3];
        users[0] = new User("John",26,new FullAdress("street1","New York",10));
        users[1] = new User("Dave",27,new FullAdress("street2","Moscow",11));
        users[2] = new User("Tom",28,new FullAdress("street3","Amsterdam",12));
        Gson gson = new Gson();
        String jsonStr = gson.toJson(users);
        System.out.println(jsonStr);
        JsonObjectWriter serializer = new JsonObjectWriter();
        System.out.println(serializer.toJson(users));
    }

    private static void userWithCollectionTest() throws IllegalAccessException {
        System.out.println("Object with collection test");
        UserWithCollection user = new UserWithCollection("John",26,new Adress("street","New York",10));
        user.addPhone("12-34");
        user.addPhone("43-21");
        user.addPhone("98-765");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(user);
        System.out.println(jsonStr);
        JsonObjectWriter serializer = new JsonObjectWriter();
        String json = serializer.toJson(user);
        System.out.println(json);
    }

    private static void simpleArrayTest() throws IllegalAccessException {
        System.out.println("Array of primitives test");
        boolean buffer[] = new boolean[10];
        for(int i=0;i<10;i++){
            buffer[i]=true;
        }
        Gson gson = new Gson();
        String jsonStr = gson.toJson(buffer);
        System.out.println(jsonStr);
        JsonObjectWriter serializer = new JsonObjectWriter();
        String json = serializer.toJson(buffer);
        System.out.println(json);
    }

    private static void simpleArrayListTest() throws IllegalAccessException {
        System.out.println("Arraylist of primitives test");
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<10;i++){
            list.add(i);
        }
        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);
        System.out.println(jsonStr);
        JsonObjectWriter serializer = new JsonObjectWriter();
        System.out.println(serializer.toJson(list));
    }

    private static void mapTest() throws IllegalAccessException {
        System.out.println("Map test");
        Map<String , Integer> map = new HashMap<>();
        map.put("key1",1);
        map.put("key2",2);
        map.put("key3",3);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        System.out.println(jsonStr);
        JsonObjectWriter serializer = new JsonObjectWriter();
        System.out.println(serializer.toJson(map));
    }
}
