package memoryStand;

import java.lang.reflect.*;

public class MemStand
{
    private long objectSize;

    public void CalcSize(ObjectsFactory factory) throws InterruptedException
    {
        final int size = 2000000;
        while (factory.HasObjects())
        {
            Object[] array = new Object[size];

            long mem2 = getMem();
            for (int i = 0; i < array.length; i++)
                array[i] = factory.GetObject();

            long mem3 = getMem();
            objectSize = (mem3 - mem2) / array.length;

            printInfo(factory);

            array = null;
            Thread.sleep(1000); //wait for 1 sec
            factory.Next();
        }
    }

    private void printInfo(ObjectsFactory factory)
    {
        int length=0;
        Class<?> cl = factory.GetObject().getClass();
        System.out.println("Object type: " + cl.getName());

        if(factory instanceof CollectionsFactory)
        {
            length = ((CollectionsFactory) factory).getLength();
            System.out.println("data length: " + length);
        }
        else
        {
            Field[] fields = cl.getDeclaredFields();
            if(fields.length>0)
            {
                System.out.println("Fields:");
                for(int i=0;i<fields.length;i++)
                {
                    if(!Modifier.isStatic(fields[i].getModifiers()))
                        System.out.println(fields[i]);
                }

            }
        }
        System.out.println("Object size: " + objectSize);
        System.out.println();
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
