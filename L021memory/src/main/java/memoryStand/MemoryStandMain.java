package memoryStand;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryStandMain
{
    public static void main(String... args) throws InterruptedException
    {
        memStandStart();
    }
    private static void memStandStart() throws InterruptedException
    {
        MemStand ms = new MemStand();
        classStart(ms);
        stringStart(ms);
        arrayListStart(ms);
        hashMapStart(ms);

    }
    private static void arrayListStart(MemStand stand) throws InterruptedException
    {
        ArrayListFactory<Integer> af = new ArrayListFactory<Integer>();
        System.out.println("Create ArrayList<Integer>");
        for(int i=0;i<10;i++)
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int j=0;j<i;j++)
                list.add(j);
            af.add(list);
        }
        stand.calcSize(af);
    }
    private static void hashMapStart(MemStand stand) throws InterruptedException
    {
        HashMapFactory<Integer,Integer> hf = new HashMapFactory<Integer, Integer>();
        System.out.println("Create HashMap<Integer,Integer>");
        for(int i=0;i<10;i++)
        {
            HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
            for(int j=0;j<i;j++)
                map.put(j,j);
            hf.add(map);
        }
        stand.calcSize(hf);
    }
    private static void stringStart(MemStand stand) throws InterruptedException
    {
        StringFactory sf = new StringFactory();
        System.out.println("Create String");
        stand.calcSize(sf);
    }
    private static void classStart(MemStand stand) throws InterruptedException
    {
        ClassFactory cf = new ClassFactory();
        System.out.println("Create classes");
        stand.calcSize(cf);
    }

}
