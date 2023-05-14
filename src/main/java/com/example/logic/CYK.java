package com.example.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CYK {
    private ArrayList<ArrayList<String>> table;
    private HashMap<String, ArrayList<String>> rules;
    public static void main(String[] args) {
        System.out.println("Hello");
    }
    public CYK(ArrayList<String> input, HashMap<String, ArrayList<String>> rules)
    {
        table = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; i++) {
            ArrayList<String> currentRow = new ArrayList<>();
            table.add(currentRow);
        }
        ArrayList<String> row =  new ArrayList<>();
        this.rules = rules;
        row  = generateLastRow(row, input, this.rules);
    }

    private ArrayList<String> generateLastRow(ArrayList<String> row, ArrayList<String> input, HashMap<String, ArrayList<String>> rules)
    {
        for (int i = 0; i < input.size(); i++) {
            ArrayList<String> terminals = findTerminals(i, input, rules);
            String finalFormatForTerminals = getFormatForTerminals(terminals);
            row.add(finalFormatForTerminals);
        }
        return row;
    }

    private String getFormatForTerminals(ArrayList<String> terminals) {
        String finalFormat = "";
        if (terminals.size() == 0)
        {
            return "";
        }
        for (int i = 0; i < terminals.size(); i++) {
            finalFormat += terminals.get(i);
            if (i != terminals.size() - 1) {
                finalFormat += ",";
            }
        }
        return  finalFormat;
    }

    private ArrayList<String> findTerminals(int elementIndex, ArrayList<String> input, HashMap<String, ArrayList<String>> rules) {
        String inputElement = input.get(elementIndex);
        ArrayList<String> terminals = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : rules.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            for (String currentTerminal: value) {
                if (currentTerminal.equals(inputElement))
                {
                    terminals.add(currentTerminal);
                }
            }
        }
        return terminals;
    }
}
