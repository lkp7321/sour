package com.lkp.jn;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        GetSome gs = new DoSome();
        gs.getTime();
        gs = new DoLog();
        gs.getLog();
    }
}
