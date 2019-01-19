package start;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> copyList = new ArrayList<Integer>();
        MyArrayList<Integer> mylist = new MyArrayList<>();

        for(int i=0;i<10;i++){
            mylist.add(i);
            copyList.add(55);
        }
        printList(mylist);
        System.out.println("Insert elements");
        mylist.add(5,11);
        mylist.add(0,11);
        mylist.add(6,11);
        mylist.add(8,11);
        printList(mylist);
        System.out.println("Add all");
        mylist.addAll(copyList);
        printList(mylist);
        System.out.println("Sort");
        Collections.sort(mylist);
        printList(mylist);
        System.out.println("Remove all");
        mylist.removeAll(copyList);
        printList(mylist);
        System.out.println("Remove by index");
        mylist.remove(3);
        printList(mylist);
        System.out.println("First Index of 11");
        System.out.println(mylist.indexOf(11));
        System.out.println("Last Index of 11");
        System.out.println(mylist.lastIndexOf(11));
        System.out.println("Copy");
        Collections.copy(mylist,copyList);
        printList(mylist);
        System.out.println("Add all elements");
        Collections.addAll(mylist,66,66,66,66,66,66);
        printList(mylist);
        System.out.println("Contains all");
        System.out.println("myList contains copyList: " + mylist.containsAll(copyList));

        System.out.println("Add with ListIterator");
        //printList(copyList);
        final ListIterator<Integer> iterator = mylist.listIterator();
        //noinspection MagicNumber
        for (int i = 0; i < 4; i++) {
            iterator.next();
        }
        for (int i = 0; i < 3; i++) {
            iterator.add(i);
        }
        printList(mylist);
    }
    public static  <T>  void printList(List<T> list){
        for(T element: list)
            System.out.print(element + " ");
        System.out.println();
    }

}
