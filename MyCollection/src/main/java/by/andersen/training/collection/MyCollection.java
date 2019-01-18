package main.java.by.andersen.training.collection;

import java.util.*;
import java.util.function.UnaryOperator;

public class MyCollection<T> implements List<T> {

    private int size = 0;

    private Node<T> first;

    private Node<T> last;

    private class Node<T> {

        T value;
        Node<T> next;
        Node<T> previous;

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    public MyCollection() {
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true:false;
    }

    @Override
    public boolean contains(Object o) {
        for(Node<T> item = first; item != null; item = item.next) {
            if(o.equals(item.value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Node<T> currentItem = first;

            @Override
            public boolean hasNext() {
                if(currentItem != null) {
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                T value = currentItem.value;
                currentItem = currentItem.next;
                return value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[size];
        int i = 0;
        for(Node<T> item = first; item != null; item = item.next, i++) {
            objects[i] = item.value;
        }
        return objects;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if(a.length < size) {
            throw new IndexOutOfBoundsException("Incorrect size");
        }
        Object[] result = a;
        for(int i = 0; i < a.length; i++) {
            result[i] = get(i);
        }
        return a;
    }

    @Override
    public boolean add(T t){
        Node<T> node = new Node<>(t,null,last);
        last = node;
        if(first == null) {
            first = node;
        } else {
            last.previous.next = node;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        for(Node<T> item = first; item != null; item = item.next, i++) {
            if(item.value.equals(o)) {
                if(i == 0) {
                    first.previous = null;
                    first = first.next;
                } else {
                    if(i == size - 1) {
                        last = last.previous;
                        last.next = null;
                    } else {
                        Node<T> previousNode = item.previous;
                        previousNode.next = item.next;
                        item.next.previous = previousNode;
                    }
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if(!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T o : c) {
            add(o);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if(!positionIndex(index)) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        MyCollection<T> myCollection = new MyCollection<>();
        for(T o : c) {
            myCollection.add(o);
        }
        int i = 0;
        Node<T> item;
        for(item = first; item != null && index != i; item = item.next, i++) {
        }
        if(i == 0) {
            first = myCollection.first;
            myCollection.last.next = item;
            size += myCollection.size();
        } else {
            if (item == null) {
                addAll(myCollection);
            } else {
                item.previous.next = myCollection.first;
                myCollection.last.next = item;
                size += myCollection.size();
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if(!remove(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        return false;
    }

    @Override
    public void clear() {
        int i = 0;
        Node<T> item = first;
        while(item != null) {
            Node<T> nextNode = item.next;
            item.next = null;
            item.value = null;
            item.previous = null;
            item = nextNode;
            i++;
        }
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        if(!positionIndex(index)) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        int i = 0;
        for(Node<T> item = first; item != null; item = item.next, i++) {
            if(i == index) {
                return item.value;
            }
        }
        return null;
    }

    @Override
    public T set(int index, T element) {
        if(!positionIndex(index)) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        int i = 0;
        Node<T> item;
        for(item = first; item != null && index != i; item = item.next ,i++) {
        }
        T previousValue = item.value;
        item.value = element;
        return previousValue;
    }

    @Override
    public void add(int index, T element) {
        if(!positionIndex(index)) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        int i = 0;
        Node<T> item;
        Node<T> newItem = new Node<>(element,null,null);
        for(item = first; item != null && index != i; item = item.next ,i++) {
        }
        if(item == null) {
            add(element);
            return;
        } else {
            if(index == 0) {
                Node<T> oldFirst = first;
                first = newItem;
                first.next = oldFirst;
            } else {
                Node<T> previousItem = item.previous;
                newItem.previous = previousItem;
                newItem.next = item;
                previousItem.next = newItem;
                item.previous = newItem;
            }
        }
        size++;
        return;
    }
    private boolean positionIndex(int index) {
        return index >= 0 && index <= size? true : false;
    }

    @Override
    public T remove(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        int i = 0;
        Node<T> item;
        for(item = first; item != null && index != i; item = item.next ,i++) {
        }
        T result = item.value;
        remove(item.value);
        return result;
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
        for(Node<T> item = first; item != null; item = item.next ,i++) {
            if(o.equals(item.value)) {
                return ++i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i = 0;
        int result = -1;
        for(Node<T> item = first; item != null; item = item.next ,i++) {
            if(o.equals(item.value)) {
                result = i;
            }
        }
        return ++result;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if((fromIndex < 0 || fromIndex >= size) || (toIndex < 0 || toIndex >= size)) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        if(fromIndex > toIndex) {
            int swap = toIndex;
            toIndex = fromIndex;
            fromIndex =  swap;
        }
        MyCollection<T> myCollection = new MyCollection<>();
        int i = 0;
        for(Node<T> item = first; item != null; item = item.next ,i++) {
            if(i >= fromIndex && i <= toIndex) {
                myCollection.add(item.value);
            }
        }
        return myCollection;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        quickSort(0,size - 1,c);
    }

    private void quickSort(int start, int end, Comparator<? super T> c) {
        if (start >= end)
            return;
        int current = (start + end) / 2;
        int  i = start;
        int j = end;
        while(i < j) {
            while(i < current && c.compare(get(current),get(i)) >= 0)
                i++;
            while(j > current && c.compare(get(current),get(j)) <= 0)
                j--;
            if(i < j) {
                T firstItem = get(i);
                T secondItem = get(j);
                remove(i);
                if(i != 0) {
                    add(j - 1, firstItem);
                    remove(j);
                    add(i, secondItem);
                } else {
                    add(j, firstItem);
                }
                if(i == current) {
                    current = j;
                } else {
                    if(j == current) {
                        current = i;
                    }
                }
            }
        }
        quickSort(start, current , c);
        quickSort(current + 1, end , c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        for(Node<T> item = first; item != null; item = item.next) {
            item.value = operator.apply(item.value);
        }
    }

    @Override
    public String toString() {
        String s = "[ ";
        for(Node<T> item = first; item != null; item = item.next) {
            s+= item.value + " ";
        }
        return s + "]";
    }
}
