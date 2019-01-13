package memoryStand;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class MemoryStandMain
{
    public static void main(String... args) throws InterruptedException
    {
        //MemStandStart();
        Test();
    }

    private static void Test() throws InterruptedException
    {
        int size = 2000000;

        System.out.println("Starting the loop");
//        while (true) {
        long mem = getMem();
        System.out.println("Mem: " + mem);

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<6;i++)
            list.add(i);
        HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
        for(int i=0;i<10;i++)
            map.put(i,i);
        Object[] array = new Object[size];

        long mem2 = getMem();
        System.out.println("Ref size: " + (mem2 - mem) / array.length);

        for (int i = 0; i < array.length; i++)
        {
            //array[i] = 1f;
            //array[0] = new Object();
            //array[i] = new String("aaa"); //String pool
            //array[i] = new String(new char[0]); // java8 or java9
            //array[i] = new String(new byte[4]); //without String pool
            //array[i] = new byte[1]; //16 or 24 with -XX:-UseCompressedOops
            //array[i] = new ArrayList<Integer>(list);
            //array[i] = new HashMap<Integer,Integer>(map);
        }

        long mem3 = getMem();
        System.out.println("Element size: " + (mem3 - mem2) / array.length);

        array = null;
        System.out.println("Array is ready for GC");

        Thread.sleep(1000); //wait for 1 sec

//        }
    }
    private static void MemStandStart() throws InterruptedException
    {
        MemStand ms = new MemStand();
        ClassStart(ms);
        StringStart(ms);
        ArrayListStart(ms);
        HashMapStart(ms);

    }
    private static void ArrayListStart(MemStand stand) throws InterruptedException
    {
        ArrayListFactory<Integer> af = new ArrayListFactory<Integer>();
        System.out.println("Create ArrayList<Integer>");
        for(int i=0;i<10;i++)
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int j=0;j<i;j++)
                list.add(j);
            af.Add(list);
        }
        stand.CalcSize(af);
    }
    private static void HashMapStart(MemStand stand) throws InterruptedException
    {
        HashMapFactory<Integer,Integer> hf = new HashMapFactory<Integer, Integer>();
        System.out.println("Create HashMap<Integer,Integer>");
        for(int i=0;i<10;i++)
        {
            HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
            for(int j=0;j<i;j++)
                map.put(j,j);
            hf.Add(map);
        }
        stand.CalcSize(hf);
    }
    private static void StringStart(MemStand stand) throws InterruptedException
    {
        StringFactory sf = new StringFactory();
        System.out.println("Create String");
        stand.CalcSize(sf);
    }
    private static void ClassStart(MemStand stand) throws InterruptedException
    {
        ClassFactory cf = new ClassFactory();
        System.out.println("Create classes");
        stand.CalcSize(cf);
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
