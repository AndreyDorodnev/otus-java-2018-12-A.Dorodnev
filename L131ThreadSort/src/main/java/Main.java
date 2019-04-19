import sort.QuickSort;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();

        int buff[] = new int[1000];
        for (int i =0;i<buff.length;i++){
            buff[i] = random.nextInt(999);
        }

        try{
            QuickSort quickSort = new QuickSort();
            quickSort.quickThreadSort(buff,0,buff.length-1);
            System.out.println("result:");
            quickSort.showBuff(buff);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
