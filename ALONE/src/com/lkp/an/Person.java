package com.lkp.an;

/**
 * 实体类
 */
public class Person {
    private String name;
    String addr;
    public int id;
    public Person(){
    }
    private Person(String name){
    this.name = name;
    }
    public Person(String name, String addr, int id){
        this.name = name;
        this.addr = addr;
        this.id = id;
    }
    private void toSay(String name){
        System.out.println("say hello "+name);
    }
    public String toHelloSay(){
        System.out.print("oo");
        return "ss";
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", id=" + id +
                '}';
    }
}
