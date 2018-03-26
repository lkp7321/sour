package com.lkp.TryCatch;

/**
 * 这个是自定义异常，继承自Exception
 */
public class Test {
    public static void main(String[] args) {
        try {
            method0(-1);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果数字在0和100之外就不合理了
     * @param math
     */
    private static void method0(int math) throws MyException {
        if(math>120 || math<0) {
            throw new MyException("哎呦，怎么值不对呢");
        }else {
            System.out.println("这次值就正常了");
        }
    }

}
