package com.lkp.ln;

public abstract class EmExample {
    private static final EmExample TOP = new EmExample("上面") {
        @Override
        public void toSay(String name) {
            System.out.println("他在"+name);
        }
    };
    public static final EmExample DOW = new EmExample("下面") {
        public void toSay(String name){
            System.out.println("他在"+name);
        }
    } ;
    private String name;
    private EmExample(String name) {
        this.name = name;
    }
    public abstract void toSay(String name);

}
