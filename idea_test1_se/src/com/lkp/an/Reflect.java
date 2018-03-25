package com.lkp.an;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * JAVA 反射
 */
public class Reflect {
    public static void main(String[] args) throws Exception {
        //反射方法：1
        Person p = new Person();
        Class class1 = p.getClass();
        //反射方法：2
        Class class2 = Person.class;
        System.out.println(class1 == class2);
        //反射方法：3
        Class class3 = Class.forName("com.lkp.an.Person");
        System.out.println(class2 == class3);
        //（公共）获取构造方
        allPublicConstructs(class3);
        //(所有，含私有)获取构造方法
        allConstructs(class3);
        //（单个：公共）获取构造方法
        singleConstruct(class3);
        //（单个：私有）获取构造方法
        singPrivateConstruct(class3);
        //访问所有公共成员变量
        allPublciFields(class3);
        //访问所有成员变量
        allFields(class3);
        //访问单个成员变量
        getSingleFields(class3);
        //访问方法
        getSinglemethod(class3);
        //访问配置文件
        settingsMethod();
    }

    /**
     * 通过配置文件读取calssName，反射使用Method
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    private static void settingsMethod() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        System.out.println("访问配置文件获取类的方法");
        Properties prop = new Properties();
        FileReader fileReader = new FileReader("test.txt");
        prop.load(fileReader);
        fileReader.close();
        String className = prop.getProperty("className");
        String classMethod = prop.getProperty("methodName");
        Class personClass = Class.forName(className);
        Constructor constructor = personClass.getConstructor();
        Object object = constructor.newInstance();
        Method me = personClass.getDeclaredMethod("toSay",String.class);
        me.setAccessible(true);
        me.invoke(object,"aini");
    }
    /**
     * 获取单个方法
     * @param class3
     * @throws NoSuchMethodException
     */
    private static void getSinglemethod(Class class3) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        System.out.println("访问类的方法");
        Method method1 = class3.getDeclaredMethod("toSay", String.class);
        method1.setAccessible(true);
        Constructor constructor = class3.getConstructor();
        Object objFiel = constructor.newInstance();
        method1.invoke(objFiel,"李坤鹏");
        System.out.println(objFiel);
    }

    /**
     * 获取单个成员变量
     * @param class3
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    private static void getSingleFields(Class class3) throws NoSuchFieldException, NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        System.out.println("访问单个成员变量");
        Field fieldName = class3.getDeclaredField("name");
        Constructor construct = class3.getConstructor();
        Object objFiel = construct.newInstance();
        fieldName.setAccessible(true);
        fieldName.set(objFiel,"HaHa");
        System.out.println(objFiel);
    }

    /**
     * 访问所有成员变量
     * @param class3
     */
    private static void allFields(Class class3) {
        System.out.println("访问所有成员变量");
        Field[] fields1 = class3.getDeclaredFields();
        for (Field field:fields1
             ) {
            System.out.println(field);
        }
    }

    /**
     * 获取所有公共成员变量
     * @param class3
     */
    private static void allPublciFields(Class class3) {
        System.out.println("访问所有公共成员变量");
        Field[] fields = class3.getFields();
        for (Field field:fields
             ) {
            System.out.println(field);
        }
    }

    /**
     * 获取单个私有构造方法
     * @param class3
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    private static void singPrivateConstruct(Class class3) throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        System.out.println("访问单个的私有构造方法");
        Constructor cons = class3.getDeclaredConstructor(String.class);
        //设置权限,使私有构造可以初始化对象
        cons.setAccessible(true);
        Object objs = cons.newInstance("啦啦");
        System.out.println(objs);
    }

    /**
     * 单个构造方法
     * @param class3
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    private static void singleConstruct(Class class3) throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        System.out.println("访问单个的公共构造方法");
        Constructor con = class3.getConstructor();
        System.out.println(con);
        //通过构造产生一个无参对象
        Object obj = con.newInstance();
        System.out.println(obj);
    }

    /**
     * 获取所有构造方法（含私有）
     * @param class3
     */
    private static void allConstructs(Class class3) {
        System.out.println("访问所有构造方法");
        Constructor[] constrs1 = class3.getDeclaredConstructors();
        for (Constructor con:constrs1
             ) {
            System.out.println(con);
        }
    }
    /**
     * 获取所有构造方法（公共）
     * @param class3
     */
    private static void allPublicConstructs(Class class3) {
        System.out.println("访问所有（公共）构造方法");
        Constructor[] constrs = class3.getConstructors();
        for (Constructor con : constrs
                ) {
            System.out.println(con);
        }
    }

}
