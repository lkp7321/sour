package com.lkp.ln;

/**
 * 枚举
 */
public enum Example {
    FRONT("前") {
        @Override
        public void toSay() {
            System.out.println("前");
        } ;
    },
    BEHIND("后"){
        @Override
        public void toSay() {
            System.out.println("后");
        } ;
    },
    LEFT("左"){
        @Override
        public void toSay() {
            System.out.println("左");
        } ;
    },
    RIGHT("右"){
        @Override
        public void toSay() {
            System.out.println("右");
        } ;
    };

    private String name;
    private Example(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public abstract void toSay();
}
