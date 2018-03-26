package com.lkp.fn;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 泛型高级
 */
public class GenericDemo {
    public static void main(String[] args){
        //如果前后都是具体类型的时候，前后必须一致
        // Collection<Object> = new ArrayList<Animal>();//这是错误的
        Collection<?> c0 = new ArrayList<Object>();
        Collection<?> c1 = new ArrayList<Animal>();
        //？ extends E ：表是向下限定，E及其字类
        //Collection<? extends Animal> cs0 = new ArrayList<>(Object)();//这是错误的
        Collection<? extends Animal> cs1 = new ArrayList<Animal>();
        //？ super E ：向上限定，E及其父类
        Collection<? super Dog> cs2 = new ArrayList<Animal>();
        char[] chars = {'a','b','c','d'};
        for (char c : chars
             ) {
            System.out.println(c);
        }
    }
}

class Animal{}

class Dog extends Animal {}

class Cat extends Animal {}
