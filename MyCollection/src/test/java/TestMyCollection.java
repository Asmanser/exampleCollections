package test.java;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import main.java.by.andersen.training.collection.MyCollection;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

public class TestMyCollection {

    private MyCollection<MyClass> myClasses = new MyCollection<>();
    private MyCollection<MyClass> myFilledList = new MyCollection<>();

    private MyClass myClass1 = new MyClass(1);
    private MyClass myClass2 = new MyClass(10);
    private MyClass myClass3 = new MyClass(5);
    private MyClass myClass4 = new MyClass(3);
    private MyClass myClass5 = new MyClass(6);
    {
        myFilledList.add(myClass1);
        myFilledList.add(myClass2);
        myFilledList.add(myClass3);
        myFilledList.add(myClass4);
        myFilledList.add(myClass5);
    }
    static class MyClass {
        private int a;

        public MyClass(int a) {
            this.a = a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyClass myClass = (MyClass) o;
            return a == myClass.a;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a);
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    @Test
    public void addSomeElements() {
        MyClass[] array = {myClass1,myClass2,myClass3,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void addCollectionInCollection() {
        List<MyClass> list = new ArrayList<>();
        list.add(myClass1);
        list.add(myClass2);
        list.add(myClass3);
        list.add(myClass4);
        list.add(myClass5);
        MyClass[] array = {myClass1,myClass2,myClass3,myClass4,myClass5};
        myClasses.addAll(list);
        Assert.assertArrayEquals(array,this.myClasses.toArray());
    }

    @Test
    public void deleteElementByObject() {
       myFilledList.remove(new MyClass(5));
       MyClass[] array = {myClass1,myClass2,myClass4,myClass5};
       Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void deleteFirstElementByObject() {
        myFilledList.remove(new MyClass(1));
        MyClass[] array = {myClass2,myClass3,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void deleteLastElementByObject() {
        myFilledList.remove(new MyClass(6));
        MyClass[] array = {myClass1,myClass2,myClass3,myClass4};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void checkEmptyWhenCollectionEmpty() {
        Assert.assertTrue(myClasses.isEmpty());
    }

    @Test
    public void checkEmptyWhenCollectionNotEmpty() {
        Assert.assertFalse(myFilledList.isEmpty());
    }

    @Test
    public void checkSizeCollection() {
        Assert.assertEquals((long)myFilledList.size(),5);
    }

    @Test
    public void checkSizeCollectionWhenCollectionHasALotOfElements() {
        for(int i = 0; i < 2_000_000;i++) {
            myClasses.add(new MyClass(i));
        }
        Assert.assertEquals((long)myClasses.size(),2_000_000);
    }

    @Test
    public void containElement() {
        Assert.assertTrue(myFilledList.contains(new MyClass(10)));
    }

    @Test
    public void notContainElement() {
        Assert.assertFalse(myFilledList.contains(new MyClass(2)));
    }

    @Test
    public void containCollection() {
        List<MyClass> list = new ArrayList<>();
        list.add(new MyClass(5));
        list.add(new MyClass(10));
        Assert.assertTrue(myFilledList.containsAll(list));
    }

    @Test
    public void notContainCollection() {
        List<MyClass> list = new ArrayList<>();
        list.add(new MyClass(7));
        list.add(new MyClass(10));
        Assert.assertFalse(myFilledList.containsAll(list));
    }

    @Test
    public void addCollectionByIndex() {
        List<MyClass> list = new ArrayList<>();
        MyClass myClass6 = new MyClass(7);
        MyClass myClass7 = new MyClass(8);
        list.add(myClass6);
        list.add(myClass7);
        myFilledList.addAll(3,list);
        MyClass[] array = {myClass1,myClass2,myClass3,myClass6,myClass7,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void addCollectionOnFirstPlaceByIndex() {
        List<MyClass> list = new ArrayList<>();
        MyClass myClass6 = new MyClass(7);
        MyClass myClass7 = new MyClass(8);
        list.add(myClass6);
        list.add(myClass7);
        myFilledList.addAll(0,list);
        MyClass[] array = {myClass6,myClass7,myClass1,myClass2,myClass3,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addCollectionByNegativIndex() {
        List<MyClass> list = new ArrayList<>();
        MyClass myClass6 = new MyClass(7);
        MyClass myClass7 = new MyClass(8);
        list.add(myClass6);
        list.add(myClass7);
        myFilledList.addAll(-1,list);
        MyClass[] array = {myClass6,myClass7,myClass1,myClass2,myClass3,myClass4,myClass5};
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addCollectionByIndexWhichMoreThanSize() {
        List<MyClass> list = new ArrayList<>();
        MyClass myClass6 = new MyClass(7);
        MyClass myClass7 = new MyClass(8);
        list.add(myClass6);
        list.add(myClass7);
        myFilledList.addAll(myFilledList.size() + 1,list);
        MyClass[] array = {myClass6,myClass7,myClass1,myClass2,myClass3,myClass4,myClass5};
    }

    @Test
    public void addCollectionOnLastPlaceByIndex() {
        List<MyClass> list = new ArrayList<>();
        MyClass myClass6 = new MyClass(7);
        MyClass myClass7 = new MyClass(8);
        list.add(myClass6);
        list.add(myClass7);
        myFilledList.addAll(myFilledList.size() ,list);
        MyClass[] array = {myClass1,myClass2,myClass3,myClass4,myClass5,myClass6,myClass7};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void removeCollectionInMyCollection() {
        List<MyClass> list = new ArrayList<>();
        MyClass myClass6 = new MyClass(1);
        MyClass myClass7 = new MyClass(10);
        list.add(myClass6);
        list.add(myClass7);
        myFilledList.removeAll(list);
        MyClass[] array = {myClass3,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void clearCollection() {
        myFilledList.clear();
        Assert.assertTrue(myFilledList.isEmpty());
    }

    @Test
    public void getElement() {
        MyClass myClass = myFilledList.get(3);
        Assert.assertTrue(myClass.equals(new MyClass(3)));
    }

    @Test
    public void getFirstElement() {
        MyClass myClass = myFilledList.get(0);
        Assert.assertTrue(myClass.equals(new MyClass(1)));
    }

    @Test
    public void getLastElement() {
        MyClass myClass = myFilledList.get(myFilledList.size() - 1);
        Assert.assertTrue(myClass.equals(new MyClass(6)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getElementAndIndexIsNegativ() {
        MyClass myClass = myFilledList.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getElementAndIndexMoreSIze() {
        MyClass myClass = myFilledList.get(myFilledList.size() + 1);
    }

    @Test
    public void setElement() {
        MyClass myClass = new MyClass(11);
        myFilledList.set(2,myClass);
        Assert.assertTrue(myFilledList.get(2).equals(new MyClass(11)));
    }

    @Test
    public void setFirstElement() {
        MyClass myClass = new MyClass(11);
        myFilledList.set(0,myClass);
        Assert.assertTrue(myFilledList.get(0).equals(new MyClass(11)));
    }

    @Test
    public void setLastElement() {
        MyClass myClass = new MyClass(11);
        myFilledList.set(myFilledList.size()-1,myClass);
        Assert.assertTrue(myFilledList.get(myFilledList.size()-1).equals(new MyClass(11)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setElementAndIndexIsNegativ() {
        MyClass myClass = new MyClass(11);
        myFilledList.set(-1,myClass);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setElementAndIndexMoreSize() {
        MyClass myClass = new MyClass(11);
        myFilledList.set(myFilledList.size() + 1,myClass);
    }

    @Test
    public void addElementByIndex() {
        MyClass myClass = new MyClass(11);
        myFilledList.add(1,myClass);
        MyClass[] array = {myClass1,myClass,myClass2,myClass3,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void addFirstElementByIndex() {
        MyClass myClass = new MyClass(11);
        myFilledList.add(0,myClass);
        MyClass[] array = {myClass,myClass1,myClass2,myClass3,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void addLastElementByIndex() {
        MyClass myClass = new MyClass(11);
        myFilledList.add(myFilledList.size(),myClass);
        MyClass[] array = {myClass1,myClass2,myClass3,myClass4,myClass5,myClass};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addLastElementByIndexWhichNegativ() {
        MyClass myClass = new MyClass(11);
        myFilledList.add(-1,myClass);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addLastElementByIndexWhichMoreSize() {
        MyClass myClass = new MyClass(11);
        myFilledList.add(myFilledList.size() + 1,myClass);
    }

    @Test
    public void removeElementByIndex() {
        myFilledList.remove(2);
        MyClass[] array = {myClass1,myClass2,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }
    @Test
    public void removeFirstElementByIndex() {
        myFilledList.remove(0);
        MyClass[] array = {myClass2,myClass3,myClass4,myClass5};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test
    public void removeLastElementByIndex2() {
        myFilledList.remove(myFilledList.size() - 1);
        MyClass[] array = {myClass1,myClass2,myClass3,myClass4};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeElementByIndexWhichNegativ() {
        myFilledList.remove(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeElementByIndexWhichEqualsSize() {
        myFilledList.remove(myFilledList.size());
    }

    @Test
    public void indexOfElement() {
        MyClass myClass = new MyClass(10);
        int index = myFilledList.indexOf(myClass);
        Assert.assertTrue(myClass.equals(myFilledList.get(index - 1)));
    }

    @Test
    public void indexOfFirstElement() {
        MyClass myClass = new MyClass(1);
        int index = myFilledList.indexOf(myClass);
        Assert.assertTrue(myClass.equals(myFilledList.get(index - 1)));
    }

    @Test
    public void indexOfLastElement() {
        MyClass myClass = new MyClass(6);
        int index = myFilledList.indexOf(myClass);
        Assert.assertTrue(myClass.equals(myFilledList.get(index - 1)));
    }

    @Test
    public void lastIndexOfElement() {
        MyClass myClass = new MyClass(3);
        myFilledList.add(myClass);
        int index = myFilledList.lastIndexOf(myClass);
        Assert.assertTrue(myClass.equals(myFilledList.get(index - 1)));
    }

    @Test
    public void subList() {
        List<MyClass> list = myFilledList.subList(2,4);
        MyClass[] array = {myClass3,myClass4,myClass5};
        Assert.assertArrayEquals(array,list.toArray());
    }

    @Test
    public void sort() {
        myFilledList.sort(new Comparator<MyClass>() {
            @Override
            public int compare(MyClass o1, MyClass o2) {
                return o1.a - o2.a;
            }
        });
        MyClass[] array = {myClass1,myClass4,myClass3,myClass5,myClass2};
        Assert.assertArrayEquals(array,this.myFilledList.toArray());
    }

}
