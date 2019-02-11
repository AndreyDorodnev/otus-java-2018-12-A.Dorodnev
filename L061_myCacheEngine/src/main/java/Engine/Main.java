package Engine;

//VM options: -Xmx128m

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        new Main().eternalCacheExample();
//        new Main().lifeCacheExample();
//        new Main().IdleCacheExample();
//        new Main().FullMemoryExampleOld();
        new Main().FullMemoryExample();
    }

    private void eternalCacheExample() {
        int size = 5;
//        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 0, 0, true);
        MyCacheEngineSoft<Integer, String> cache = new MyCacheEngineSoft<>(size,0,0,true);
        for (int i = 0; i < 10; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < 10; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    private void lifeCacheExample() throws InterruptedException {
        int size = 5;
//        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 1000, 0, false);
        MyCacheEngineSoft<Integer, String> cache = new MyCacheEngineSoft<>(size,1000,0,false);
        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(1200);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }
    private void IdleCacheExample() throws InterruptedException{
        int size = 5;
//        CacheEngine<Integer,String> cache = new CacheEngineImpl<>(size,0,1000,false);
        MyCacheEngineSoft<Integer, String> cache = new MyCacheEngineSoft<>(size,0,1000,false);
        for(int i=0;i<size;i++){
            cache.put(new MyElement<>(i,"String" + i));
        }
        for(int i=0;i<size;i++){
            MyElement<Integer,String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element!=null ? element.getValue() : "null"));
        }
        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        for(int k=0;k<5;k++) {
            for (int i = 0; i < size - 1; i++) {
                Thread.sleep(100);
                MyElement<Integer, String> element = cache.get(i);
                System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
            }
        }
        MyElement<Integer,String> element = cache.get(4);
        System.out.println("String for " + 4 + ": " + (element!=null ? element.getValue() : "null"));

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    private void FullMemoryExampleOld(){
        final int SIZE = 26;
        final int COUNT = SIZE-1;
        final int ARRAY_SIZE = 1024*1024;

        MyElement<Integer,Integer[]> element;
        CacheEngine<Integer,Integer[]> cache = new CacheEngineImpl<>(SIZE,0,0,true);
        for(int i=0;i<COUNT;i++){
            cache.put(new MyElement<>(i,new Integer[ARRAY_SIZE]));
        }
        for(int i=0;i<COUNT;i++){
            element = cache.get(i);
            System.out.println("Data for " + i + ": " + (element!=null ? "Array" : "null"));
        }
        //OutOFMemoryError
        cache.put(new MyElement<>(25,new Integer[ARRAY_SIZE]));
    }

    private void FullMemoryExample(){
        final int SIZE = 26;
        final int COUNT = SIZE-1;
        final int ARRAY_SIZE = 1024*1024;

        MyElement<Integer,Integer[]> element;
        MyCacheEngineSoft<Integer, Integer[]> cache = new MyCacheEngineSoft<>(SIZE,0,0,true);

        for(int i=0;i<COUNT;i++){
            cache.put(new MyElement<>(i,new Integer[ARRAY_SIZE]));
        }
        for(int i=0;i<COUNT;i++){
            element = cache.get(i);
            System.out.println("Data for " + i + ": " + (element!=null ? "Array" : "null"));
        }
        System.out.println(cache.getInfo());
        cache.clearStat();

        System.out.println("Add element not fit in memory\n");
        cache.put(new MyElement<>(25,new Integer[ARRAY_SIZE]));

        for(int i=0;i<SIZE;i++){
            element = cache.get(i);
            System.out.println("Data for " + i + ": " + (element!=null ? "Array" : "null"));
        }
        System.out.println(cache.getInfo());
        cache.clearStat();

        for(int i=0;i<SIZE/2;i++){
            cache.put(new MyElement<>(i,new Integer[ARRAY_SIZE]));
        }
        for(int i=0;i<SIZE;i++){
            element = cache.get(i);
            System.out.println("Data for " + i + ": " + (element!=null ? "Array" : "null"));
        }
        System.out.println(cache.getInfo());
        cache.clearStat();

        cache.dispose();
    }

}
