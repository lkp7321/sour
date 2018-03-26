package Model.mo;

import java.io.IOException;

/**
 * 面向对象设计模式
 * 创建型模式：简单工厂模式，工厂方法模式，抽象工厂模式，建造者模式，原型模式，单例模式。(6)
 * 结构型模式：外观模式、适配器模式、代理模式、装饰模式、桥接模式、组合模式、享元模式。(7)
 * 行为型模式：模版方法模式、观察者模式、状态模式、职责链模式、命令模式、访问者模式、策略模式、备忘录模式、迭代器模式、解释器模式。(10)
 * @author lz130
 */
public class Demo {
    /**
     * 创建模式
     *  1.简单工厂模式
     *  2.工厂方法模式
     *  3.设计模式
     */

    public static void main(String[] args) {
//        // 1 简单工厂
//        // 提供一个类用来创建所有的对象，有新加入的类会修改原代码
//        Factory.createDog().eat();
//        Factory.createCat().eat();
//        // 1 简单工厂
//        Animal an = Factory.create("dog");
//        if(an!=null){
//            an.eat();
//        }else{
//            System.out.println("is not Animal");
//        }
//        // 2 工厂模式
//        // 创建一个工厂接口，对象的创建都由工厂的实现类完成，有新加入的类会增加工作量，但不用修改原代码
//        AnimalFactory adf = new DogFactory();
//        adf.create().eat();
//        AnimalFactory acf = new CatFactory();
//        acf.create().eat();
//        // 3.单例模式
//        // 饿汉式单例模式，类加载的时候就创建了一个对象，安全，不会出现问题。
//        // 懒汉式单例模式，需要创建的时候才创建一个对象，不安全，可能会出现问题。
//        // 问题：懒加载，线程安全。
//        // 给懒汉模式加synchronized同步锁
//        System.out.println(SingleTon.getInstance());
//        System.out.println(SingleTon.getInstance());
          // 装饰设计模式

    }

}
abstract class Animal {
    public abstract void eat();
}
class dog extends Animal {
    @Override
    public void eat() {
        System.out.println("狗吃肉");
    }
}
class cat extends  Animal {
    @Override
    public void eat() {
        System.out.println("猫吃鱼");
    }
}
class Factory {
    private Factory() {
    }
    public static dog createDog() {
        return new dog();
    }
    public static cat createCat() {
        return new cat();
    }
    public static Animal create(String type) {
        if("dog".equals(type)) {
            return new dog();
        }else if("cat".equals(type)){
            return new cat();
        }else{
            return null;
        }
    }
}

//---------工厂模式-----------
interface AnimalFactory {
    Animal create();
}
class DogFactory implements AnimalFactory {
    @Override
    public Animal create() {
        return new dog();
    }
}
class CatFactory implements AnimalFactory {
    @Override
    public Animal create() {
        return new cat();
    }
}

//---------- -单例模式--------------
class SingleTon {
    /**
     * 饿汉式单例模式
     */
    private SingleTon() {
    }
    private static SingleTon instance = new SingleTon();
    public static SingleTon getInstance() {
       return instance;
    }
}
class SingleTons {
    /**
     * 懒汉式单例模式
     */
    private SingleTons() {
    }
    private static SingleTons instance = null;
    public synchronized static SingleTons getInstance() {
        if(instance == null){
            instance = new SingleTons();
        }
        return instance;
    }
}
