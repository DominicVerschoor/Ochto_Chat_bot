package com.example.logic.newLogic;

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
