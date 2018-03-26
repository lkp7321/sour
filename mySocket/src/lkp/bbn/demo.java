package lkp.bbn;

/**
 * java 运行原理
 * 静态块和静态变量同时修饰一个变量：
 * 如果变量定义时没有赋值，默认时直接使用静态块中的值
 * 如果变量定义时有值，根据顺序选择赋值
 */
public class demo {
    /* 结果：1 */
    static {
        i = 20;
    }
    public static int i = 1;//有值 1，没值 20
    public static void main(String[] args) {
        System.out.println(i);
    }
}

/**
 * 测试
 */
class decr {
    /* 结果：20 */
    public static  int i = 1; //20
    // public static  int i; //20
    static {
        i = 20;
    }
    public static void main(String[] args) {
        System.out.println(i);
    }
}

/**
 * 测试
 */
class deco {
    /* 结果：20 */
    static {
        i = 20;
    }
    public static int i;
    public static void main(String[] args) {
        System.out.println(i);
    }
}

/**
 * 测试
 */
class droc {
    private static int a;
    public static void main(String[] args) {
        int x = 0;
        for (int i = 0; i < 3; i++) {
            // x = a+i + ++a; // 1:1 4:2 7:3
            x = a+i + ++a + a++; // 2:2 9:4 16:6
            System.out.println(x);
        }
        System.out.println("a："+a);
        System.out.println("x："+x);
    }
}
// static 就是类可以直接调用，静态的，不需要依赖对象产生
class drco {
    private int a;
    public static void main(String[] args) {
        drco d = new drco();
        for (int i = 0; i < 3; i++) {// 1 4 11
            d.a = d.a+i + ++d.a;
            System.out.println(d.a);
        }
        System.out.println("a："+d.a);
    }
}
// 局部变量
class drcos {
    private static int a = 2; // 就近原则
    public static void main(String[] args) {
        int a = 0;
        for (int i = 0; i < 3; i++) {//
            a = a + i + ++a;
            System.out.println(a);
        }
        System.out.println("a："+a);
    }
}
