package com.example.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Slot {
    private String name;
    private ArrayList<String> items;

    public Slot(String name) {
        this.name = "<" + name + ">";
        items = new ArrayList<>();
    }

    public void addItems(String newItems) {
        String[] splitString = newItems.split(",", 0);

        for (String str : splitString) {
            if (!items.contains(str)) {
                items.add(str);
            }
        }
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        String str = name + ",";

        for (String item : items) {
            str += item + ",";
        }

        return str;
    }
}
