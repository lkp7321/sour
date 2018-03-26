package lkp.bbn;

/**
 * java的运行方式
 * jvm：（面试）
 * 结果：
 * <<
 * SSClass
 * SuperClass init!
 * 123
 * >>
 * @author lz130
 */
class SSClass {
    static {
        System.out.println("SSClass");
    }
}
class SuperClass extends SSClass {
    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;

    public SuperClass()
    {
        System.out.println("init SuperClass");
    }
}
class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init");
    }

    static int a;

    public SubClass()
    {
        System.out.println("init SubClass");
    }
}
class NotInitialization {
    public static void main(String[] args)
    {
        System.out.println(SubClass.value);
    }
}
