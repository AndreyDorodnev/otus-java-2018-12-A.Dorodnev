package memoryStand;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryStandMain
{
    public static void main(String[] args) throws InterruptedException {
        MemStand ms = new MemStand();
        ObjectsFactory factory;
        System.out.println("Start test");
        System.out.println();
        stringTest(ms);
        arrayListTest(ms);
        hashMapTest(ms);
    }
    public static void stringTest(MemStand ms) throws InterruptedException {
        ObjectsFactory factory;
        factory = ()-> new String("");
        ms.MemCalc(factory);
        factory = ()-> new String(new byte[1]);
        ms.MemCalc(factory);
        factory = ()-> new String(new byte[2]);
        ms.MemCalc(factory);
        factory = ()-> new String(new byte[10]);
        ms.MemCalc(factory);
        factory = ()-> new String(new byte[20]);
        ms.MemCalc(factory);
    }
    public static void arrayListTest(MemStand ms) throws InterruptedException {
        ObjectsFactory factory;
        ArrayList<Integer> list = new ArrayList<Integer>();
        factory = ()-> new ArrayList<Integer>(list);
        ms.MemCalc(factory);
        for(int i=0;i<10;i++){
            list.add(i);
            factory = ()-> new ArrayList<Integer>(list);
            ms.MemCalc(factory);
        }
    }
    public static void hashMapTest(MemStand ms) throws InterruptedException {
        ObjectsFactory factory;
        HashMap<Integer,Integer> map = new HashMap<>();
        factory = ()-> new HashMap<Integer, Integer>(map);
        ms.MemCalc(factory);
        for(int i=0;i<10;i++){
            map.put(i, i);
            factory = ()-> new HashMap<Integer, Integer>(map);
            ms.MemCalc(factory);
        }
    }

}
