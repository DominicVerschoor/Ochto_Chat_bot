package com.example.logic;

public class Slot {
    private String slotName;
    private String slotContent;

    public Slot(String slotName, String slotContent){
        this.slotName = slotName;
        this.slotContent = slotContent;
    }

    public String getSlotName(){
        return slotName;
    }

    public String getSlotContent(){
        return slotContent;
    }
}
