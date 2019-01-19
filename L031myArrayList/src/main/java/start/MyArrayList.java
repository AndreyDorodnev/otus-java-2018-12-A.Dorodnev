package start;

import java.util.*;
import java.util.function.*;

public class MyArrayList<T> implements List<T> {

    private Object[] elements;
    private int size;
    private static final Object[] emptyElements = {};
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public MyArrayList(){
        elements = emptyElements;
    }
    public MyArrayList(int startCapacity)
    {
        if(startCapacity>0)
            elements = new Object[startCapacity];
        else if(startCapacity==0)
            elements = emptyElements;
        else throw new IllegalArgumentException("Illegal capacity!");
    }
    public MyArrayList(Collection<? extends T> collection)
    {
        final int dataLength = collection.size();
        if(dataLength>MAX_ARRAY_SIZE)
            throw new OutOfMemoryError();
        elements = new Object[dataLength];
        int i=0;
        for(T element:collection){
            elements[i++] = element;
        }
        size = dataLength;
    }
    private void checkCapacityRange(int capacity)
    {
        if((capacity>MAX_ARRAY_SIZE)||(capacity<0)){
            throw new OutOfMemoryError();
        }
    }

    private boolean increaseCapacity()
    {
        int newCapacity = elements.length + (elements.length/2);
        newCapacity = Math.max(newCapacity, DEFAULT_CAPACITY);
        checkCapacityRange(newCapacity);
        elements = Arrays.copyOf(elements, newCapacity);
        return true;
    }
    private boolean increaseCapacity(int requiredCapacity)
    {
        int currentCapacity = elements.length;
        int newCapacity = currentCapacity + (currentCapacity/2);
        if(newCapacity>=requiredCapacity)
        {
            checkCapacityRange(newCapacity);
        }
        else {
            checkCapacityRange(requiredCapacity);
            if(elements==emptyElements) {
                newCapacity = Math.max(requiredCapacity, DEFAULT_CAPACITY);
            }
            else {
                newCapacity = requiredCapacity;
            }
        }
        elements = Arrays.copyOf(elements, newCapacity);
        return true;
    }
    private boolean illegalIndex(int index){
        if((index>=0)&&(index<size))
            return true;
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean contains(Object element) {
        return indexOf(element) >= 0;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public boolean add(T element) {
        if(elements.length==size){
            increaseCapacity();
        }
        elements[size] = element;
        size++;
        return true;
    }

    public boolean remove(Object element) {
        int index = indexOf(element);
        if(index>0){
            remove(index);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean addAll(Collection<? extends T> collection) {
        Object[] data = collection.toArray();
        //int dataSize = collection.size(); //optimize????
        if(data.length==0)
            return false;
        if(data.length >(elements.length-size))
            increaseCapacity(size + data.length);
//      System.arraycopy(data,0,elements,size,data.length); //
        for(int i=size,j=0;i<data.length+size;i++,j++)
            elements[i] = data[j];
        size = size + data.length;
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> collection) {
        if(!illegalIndex(index))
            throw new IllegalArgumentException("Illegal index!");
        Object[] data = collection.toArray();
        if(data.length==0)
            return false;
        if(data.length>(elements.length - size))
            increaseCapacity(size + data.length);
        int startPos = size - index;
        if(startPos>0){
            for(int i=size-1;i>startPos;i--){
                elements[i+data.length] = elements[i];
            }
//            System.arraycopy(elements, index,
//                             elements, index + data.length,
//                             startPos);
        }
//        System.arraycopy(data, 0, elements, index, data.length);
        for(T element:collection){
            elements[index++]=element;
        }
        size = size + data.length;
        return true;
    }


    public void sort(Comparator<? super T> comparator) {
        Arrays.sort((T[]) elements, 0, size, comparator);
    }

    public void clear() {
        elements = emptyElements;
        size =0;
    }

    public T get(int index) {
        if(!illegalIndex(index))
            throw new IllegalArgumentException("Illegal index!");
        return (T)elements[index];
    }

    public T set(int index, T element) {
        if(!illegalIndex(index))
            throw new IllegalArgumentException("Illegal index!");
        T removedElement = (T)elements[index];
        elements[index] = element;
        return  removedElement;
    }

    public void add(int index, T element) {
        if(!illegalIndex(index))
            throw new IllegalArgumentException("Illegal index!");
        if(size==elements.length)
            increaseCapacity();
//        for(int i=size;i>index;i--){
//            elements[i] = elements[i-1];
//        }
        System.arraycopy(elements, index,elements, index + 1,size-index);
        elements[index] = element;
        size++;
    }

    public T remove(int index) {
        if(illegalIndex(index)){
            T removedElement = (T)elements[index];
//            for(int i=index;i<size-1;i++){
//                elements[i] = elements[i+1];
//            }
            System.arraycopy(elements, index+1, elements, index, size-index-1);
            elements[--size] = null;
            return removedElement;
        }
        else {
            return null;
        }
    }

    public int indexOf(Object element) {
        if(element==null){
            for(int i=0;i<size;i++){
                if(elements[i] == null)
                    return i;
            }
        }
        else{
            for(int i=0;i<size;i++){
                if(element.equals(elements[i]))
                    return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object element) {
        if(element==null){
            for(int i=size-1;i>0;i--){
                if(elements[i] == null)
                    return i;
            }
        }
        else{
            for(int i=size-1;i>0;i--){
                if(element.equals(elements[i]))
                    return  i;
            }
        }
        return -1;
    }

    public boolean retainAll(Collection<?> collection) {
        //Slow
        for(int i=0;i<size;i++){
            if(!collection.contains(elements[i]))
                remove(i);
        }
        return true;
    }

    public boolean removeAll(Collection<?> collection) {
        for(Object obj:collection){
            if(contains(obj)){
                remove(indexOf(obj));
            }
        }
        return true;
    }

    public boolean containsAll(Collection<?> collection) {
        for(Object obj:collection)
            if(!contains(obj))
                return false;
        return true;
    }

    public <T> T[] toArray(T[] array) {
        if(array.length<size)
            return (T[]) Arrays.copyOf(elements, size, array.getClass());
        for(int i=0;i<size;i++)
            array[i]=(T)elements[i];
        return array;
    }

    public Iterator iterator() {
        return new Iter();
    }

    public ListIterator listIterator() {
        return new ListIter(-1);
    }

    public ListIterator listIterator(int index) {
        if(!illegalIndex(index))
            throw new IllegalArgumentException("Illegal index!");
        return new ListIter(index);
    }

    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    private class Iter implements Iterator<T>{

        int cursor=-1;
        boolean hasReturned = false;

        @Override
        public boolean hasNext() {
            return cursor<size-1;
        }

        @Override
        public T next() {
            if(cursor+1>=size)
                throw new NoSuchElementException();
            hasReturned =true;
            return (T)elements[++cursor];
        }

        @Override
        public void remove() {
            if(!hasReturned)
                throw new IllegalStateException();
            MyArrayList.this.remove(cursor-1);
            hasReturned = false;
        }
    }
    private class ListIter extends Iter implements ListIterator<T>{

        public ListIter(int index)
        {
            super();
            cursor = index;
        }
        @Override
        public boolean hasPrevious() {
            return cursor>0;
        }

        @Override
        public T previous() {
            if(cursor<=0)
                throw new NoSuchElementException();
            return (T)elements[--cursor];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void set(T element) {
            if((cursor>=0)&&(cursor<size))
                MyArrayList.this.set(cursor,element);
        }

        @Override
        public void add(T element) {
            int index = cursor+1;
            MyArrayList.this.add(index,element);
            hasReturned = false;
            cursor++;
        }

    }

}
