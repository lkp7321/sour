package com.dso;

public class WindowsListService implements ListService {

    @Override
    public String showListCmd() {
        return "dir";
    }
}
