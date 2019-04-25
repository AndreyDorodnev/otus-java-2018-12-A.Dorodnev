package sort;

public class QuickSort {

    public void quickThreadSort(int[] buff) throws InterruptedException {
        int start = 0;
        int end = buff.length - 1;
        int middle2 = partition(buff, start, end);
        int middle1 = partition(buff,start,middle2);
        int middle3 = partition(buff,middle2,end);

        Thread t1 = new Thread(()->{
            QuickSort fs = new QuickSort();
            fs.qSort(buff,0,middle1);
        });
        Thread t2 = new Thread(()->{
            QuickSort fs = new QuickSort();
            fs.qSort(buff,middle1,middle2);
        });
        Thread t3 = new Thread(()->{
            QuickSort fs = new QuickSort();
            fs.qSort(buff,middle2,middle3);
        });
        Thread t4 = new Thread(()->{
            QuickSort fs = new QuickSort();
            fs.qSort(buff,middle3,end);
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

    public void qSort(int[] buff,int start,int end)
    {
        if (start >= end) return;
        int middle = partition(buff, start, end);
        qSort(buff, start, middle);
        qSort(buff, middle + 1, end);
    }

    private int partition(int[] buff,int lo, int hi)
    {
        int pivot = buff[lo];
        int i = lo - 1;
        int j = hi + 1;
        while(true)
        {
            do
                i++;
            while (buff[i] < pivot);
            do
                j--;
            while (buff[j] > pivot);
            if (i >= j)
                return j;
            swap(buff,i,j);
        }

    }

    private void swap(int[] buff,int i,int j){
        int k = buff[i];
        buff[i] = buff[j];
        buff[j] = k;
    }

    public void showBuff(int[] buff){
        for (int j : buff) {
            System.out.println(j);
        }
    }

}