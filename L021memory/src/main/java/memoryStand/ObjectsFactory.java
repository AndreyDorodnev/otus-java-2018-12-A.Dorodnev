package memoryStand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public abstract class ObjectsFactory
{
    protected boolean hasObjects;
    int selectedNum;
    abstract Object getObject();
    abstract void next();
    boolean HasObjects() {return  hasObjects;}
}
interface CollectionsFactory
{
    int getLength();
}
 class  StringFactory extends ObjectsFactory implements CollectionsFactory
 {

    int maxNum;

     public StringFactory()
     {
        maxNum = 6;
        hasObjects = true;
     }

     @Override
     Object getObject()
     {
         switch (selectedNum)
         {
             case 0:
                 return new String("");
             case 1:
                 return new String(new byte[0]);
             case 2:
                 return new String(new byte[1]);
             case 3:
                 return new String(new byte[2]);
             case 4:
                 return new String(new byte[3]);
             case 5:
                 return new String(new byte[4]);
             case 6:
                 return new String(new byte[5]);
             default:
                 return null;
         }
     }

     public int getLength() {
         return selectedNum;
     }

     @Override
     void next()
     {
         if(selectedNum>=maxNum)
             hasObjects = false;
         selectedNum++;
     }
 }
 class ClassFactory extends ObjectsFactory
 {
     int maxNum;
     public ClassFactory()
     {
         maxNum = 3;
         hasObjects = true;
     }
     @Override
     Object getObject()
     {
         switch (selectedNum)
         {
             case 0:
                 return 1;
             case 1:
                 return 1f;
             case 2:
                 return 1d;
             case 3:
                 return new ArrayListFactory<Integer>();
             case 4:
                 return new MemStand();
             default:
                 return null;
         }
     }
     @Override
     void next()
     {
         if(selectedNum>=maxNum)
             hasObjects = false;
         selectedNum++;
     }
 }
 class ArrayListFactory<T> extends ObjectsFactory implements CollectionsFactory
 {
        private ArrayList<ArrayList<T>> objects;

        public ArrayListFactory()
        {
            hasObjects = false;
            selectedNum = 0;
            objects = new ArrayList<ArrayList<T>>();
        }

        void add(ArrayList<T> list)
        {
            objects.add(list);
            hasObjects = true;
        }

        @Override
        Object getObject()
        {
           return new ArrayList<T>(objects.get(selectedNum));
        }
        @Override
        void next()
        {
            selectedNum++;
            if(selectedNum>objects.size()-1)
                hasObjects = false;
        }

     public int getLength() {
         return objects.get(selectedNum).size();
     }
 }
class HashMapFactory<K,V> extends ObjectsFactory implements CollectionsFactory
{
    private ArrayList<HashMap<K,V>> objects;

    public HashMapFactory()
    {
        hasObjects = false;
        selectedNum = 0;
        objects = new ArrayList<HashMap<K, V>>();
    }

    void add(HashMap<K,V> map)
    {
        objects.add(map);
        hasObjects = true;
    }

    @Override
    Object getObject()
    {
        return new HashMap<K,V>(objects.get(selectedNum));
    }
    @Override
    void next()
    {
        selectedNum++;
        if(selectedNum>objects.size()-1)
            hasObjects = false;
    }

    public int getLength() {
        return objects.get(selectedNum).size();
    }
}