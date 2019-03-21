package objectWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class JsonObjectWriter {

    public JsonObjectWriter() {
    }

    public String toJson(Object object) throws IllegalAccessException {
        if (object==null)
            throw new NullPointerException();
        Class clazz = object.getClass();
        if(clazz.isArray())
            return addToJsonArray(clazz,object).toJSONString();
        return addToJsonObject(clazz,object).toJSONString();
    }

    public String toJson(Collection object) throws IllegalAccessException {
        if (object==null)
            throw new NullPointerException();
        return addToJsonCollection(object.getClass(),object).toJSONString();
    }

    public String toJson(Object[] object) throws IllegalAccessException {
        if (object==null)
            throw new NullPointerException();
        return addToJsonArray(object.getClass(),object).toJSONString();
    }

    public String toJson(Map object) throws IllegalAccessException {
        if (object==null)
            throw new NullPointerException();
        return addToJsonMap(object.getClass(),object).toJSONString();
    }

    private JSONArray addToJsonArray(Class clazz, Object object) throws IllegalAccessException {
        JSONArray list = new JSONArray();
        for(int i = 0; i< Array.getLength(object); i++){
            if(isFinal(Array.get(object,i)))
                list.add(addToJsonObject(Array.get(object,i).getClass(),Array.get(object,i)));
            else
                list.add(Array.get(object,i));
        }
        return list;
    }

    private JSONArray  addToJsonCollection(Class clazz,Object object) throws IllegalAccessException {
        Object[] values;
        JSONArray list = new JSONArray();
        values = ((Collection)object).toArray();
        for(Object obj:values){
            if(isFinal(obj))
                list.add(addToJsonObject(obj.getClass(),obj));
            else
                list.add(obj);
        }
        return list;
    }

    private JSONObject addToJsonMap(Class clazz,Object object) throws IllegalAccessException {
        JSONObject jsonObject = new JSONObject();
        Object[] values;
        Object[] keys;
        values = ((Map) object).values().toArray();
        keys = ((Map)object).keySet().toArray();
        for(int i=0;i<keys.length;i++){
            if(isFinal(values[i]))
                jsonObject.put(keys[i], addToJsonObject(values[i].getClass(),values[i]));
            else
                jsonObject.put(keys[i],values[i]);
        }
        return jsonObject;
    }

    private JSONObject addToJsonObject(Class clazz, Object object) throws IllegalAccessException {
        JSONObject jObject = new JSONObject();
        for (Field field:clazz.getSuperclass().getDeclaredFields()){
            addToJsonField(jObject,field,object);
        }
        for(Field field:clazz.getDeclaredFields()){
            addToJsonField(jObject,field,object);
        }
        return jObject;
    }

    private void addToJsonField(JSONObject jobject,Field field,Object object) throws IllegalAccessException {
        field.setAccessible(true);
        if(isFinal(field.get(object))){
            Object subObject = field.get(object);
            if(field.getType().isArray())
                jobject.put(field.getName(), addToJsonArray(field.getType(),subObject));
            else if(subObject instanceof Collection)
                jobject.put(field.getName(),addToJsonCollection(field.getType(),subObject));
            else if(subObject instanceof Map)
                jobject.put(field.getName(),addToJsonMap(field.getType(),subObject));
            else{
                jobject.put(field.getName(), addToJsonObject(field.getType(),subObject));
            }
        }
        else{
            jobject.put(field.getName(),field.get(object).toString());
        }
    }

    private boolean isFinal(Object object){
        if(object instanceof Number||object instanceof Boolean||object instanceof String)
            return false;
        if(object.getClass().isPrimitive()||object.getClass().isInterface()||object.getClass().isEnum())
            return false;
        return true;
    }

}
