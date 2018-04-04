package com;

public class DemoObj {
    private Long id;
    private String name;
    public DemoObj() {
        super();
    }
    public DemoObj(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(String  name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
