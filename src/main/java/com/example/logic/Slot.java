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

    public void setBoth(String nameContent){
        String[] split = nameContent.split(">");
        setSlotName(cleanWord(split[0]));
        setSlotContent(cleanWord(split[1]));
    }

    public void setSlotContent(String slotContent) {
        this.slotContent = slotContent;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }


    public static String cleanWord(String input) {
        return input.replaceAll("[^\\p{L}\\p{N}]+", "");
    }
}
