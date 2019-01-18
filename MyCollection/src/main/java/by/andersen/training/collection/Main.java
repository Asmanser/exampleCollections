package main.java.by.andersen.training.collection;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        MyCollection<MyClass> myClasses = new MyCollection<>();
        myClasses.add(new MyClass(7));
        myClasses.add(new MyClass(2));
        myClasses.add(new MyClass(7));
        myClasses.add(new MyClass(2));
        myClasses.add(new MyClass(7));
        myClasses.add(new MyClass(2));
        System.out.println(myClasses);
        /*myClasses.sort(new Comparator<MyClass>() {
            @Override
            public int compare(MyClass o1, MyClass o2) {
                return o1.getA() - o2.getA();
            }
        });*/
        myClasses.replaceAll(myClass -> {
            myClass.a *= myClass.a;
            return myClass;
        });
        System.out.println(myClasses);
    }

    static class MyClass {
        private int a;

        public MyClass(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return Integer.toString(a);
        }
    }



}
