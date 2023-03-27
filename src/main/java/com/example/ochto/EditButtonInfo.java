package com.example.ochto;

public class EditButtonInfo {
    private final String item;
    private final int index;

    public EditButtonInfo(String item, int index){
        this.item = item;
        this.index = index;
    }

    public String getString(){
        return item;
    }

    public int getIndex(){
        return index;
    }
}
