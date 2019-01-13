package memoryStand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public abstract class ObjectsFactory
{
    protected boolean hasObjects;
    int selectedNum;
    abstract Object GetObject();
    abstract void Next();
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
        maxNum = 5;
        hasObjects = true;
     }

     @Override
     Object GetObject()
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
             default:
                 return null;
         }
     }

     public int getLength() {
         return selectedNum;
     }

     @Override
     void Next()
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
     Object GetObject()
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
     void Next()
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

        void Add(ArrayList<T> list)
        {
            objects.add(list);
            hasObjects = true;
        }

        @Override
        Object GetObject()
        {
           return new ArrayList<T>(objects.get(selectedNum));
        }
        @Override
        void Next()
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

    void Add(HashMap<K,V> map)
    {
        objects.add(map);
        hasObjects = true;
    }

    @Override
    Object GetObject()
    {
        return new HashMap<K,V>(objects.get(selectedNum));
    }
    @Override
    void Next()
    {
        selectedNum++;
        if(selectedNum>objects.size()-1)
            hasObjects = false;
    }

    public int getLength() {
        return objects.get(selectedNum).size();
    }
}