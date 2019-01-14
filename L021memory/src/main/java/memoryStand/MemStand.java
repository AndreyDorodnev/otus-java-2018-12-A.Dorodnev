package memoryStand;

import java.util.Collection;

public class MemStand
{
    public void MemCalc(objectsFactory factory) throws InterruptedException {
        final int size = 2_000_000;
        Object[] array = new Object[size];
        long mem2 = getMem();

        for(int i=0;i<array.length;i++)
            array[i] = factory.getObject();

        long mem3 = getMem();

        printInfo(factory.getObject());
        System.out.println("Object size " + (mem3 - mem2)/array.length);
        //System.out.println("Object size (Instrumentation): " + ObjectSizeFetcher.getObjectSize(factory.getObject()));
        System.out.println("");
        array = null;

        Thread.sleep(1000);
    }
    private void printInfo(Object obj) {
        Class<?> cl = obj.getClass();
        System.out.println(cl.getName());
        if(obj instanceof Collection)
            System.out.println("Data size: " + ((Collection) obj).size());
        if(obj instanceof String)
            System.out.println("Data size: " + ((String) obj).length());
    }
    private long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

}
