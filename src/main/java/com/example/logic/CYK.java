package com.example.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CYK {
    private ArrayList<ArrayList<String>> table;
    private HashMap<String, ArrayList<String>> rules;
    //private ArrayList<String> orderOfRules;
    public static void main(String[] args) {
        /*
        HashMap<String, ArrayList<String>> testRules = new HashMap<>();
        String firstKey = "S";
        ArrayList firstValue = new ArrayList<>(Arrays.asList("AB", "BC"));
        String secondKey = "A";
        ArrayList secondValue = new ArrayList<>(Arrays.asList("BA", "a"));
        String thirdKey = "B";
        ArrayList thirdValue = new ArrayList<>(Arrays.asList("CC", "b"));
        String forthKey = "C";
        ArrayList forthValue = new ArrayList<>(Arrays.asList("AB", "a"));
        testRules.put(firstKey, firstValue);
        testRules.put(secondKey, secondValue);
        testRules.put(thirdKey, thirdValue);
        testRules.put(forthKey, forthValue);
         */

        HashMap<String, ArrayList<String>> testRules = new HashMap<>();
        String firstKey = "S";
        ArrayList firstValue = new ArrayList<>(Arrays.asList("Action"));
        String secondKey = "Action";
        ArrayList secondValue = new ArrayList<>(Arrays.asList("Location", "Schedule"));
        String thirdKey = "Schedule";
        ArrayList thirdValue = new ArrayList<>(Arrays.asList("WTimeExp", "TimeExpW"));
        String forthKey = "TimeExp";
        ArrayList forthValue = new ArrayList<>(Arrays.asList("DayTime", "TimeDay"));
        String fifthKey = "Location";
        ArrayList fifthValue = new ArrayList<>(Arrays.asList("WRoom", "WProWRoom", "WRoomW"));
        String sixthKey = "Time";
        ArrayList sixthValue = new ArrayList<>(Arrays.asList("9", "12"));
        String seventhKey = "Pro";
        ArrayList seventhValue = new ArrayList<>(Arrays.asList("I", "you", "she", "he"));
        String eighthKey = "Room";
        ArrayList eighthValue = new ArrayList<>(Arrays.asList("DS", "SP"));
        String ninthKey = "Day";
        ArrayList ninthValue = new ArrayList<>(Arrays.asList("Monday", "Sunday"));
        String tenthKey = "W";
        ArrayList tenthValue = new ArrayList<>(Arrays.asList("Which", "lectures", "are", "there", "on", "at"));
        testRules.put(firstKey, firstValue);
        testRules.put(secondKey, secondValue);
        testRules.put(thirdKey, thirdValue);
        testRules.put(forthKey, forthValue);
        testRules.put(fifthKey, fifthValue);
        testRules.put(sixthKey, sixthValue);
        testRules.put(seventhKey, seventhValue);
        testRules.put(eighthKey, eighthValue);
        testRules.put(ninthKey, ninthValue);
        testRules.put(tenthKey, tenthValue);

        ArrayList<String> input = new ArrayList<>(Arrays.asList("W", "Day", "Time"));
        CYK cyk = new CYK(input, testRules);
    }
    public CYK(ArrayList<String> input, HashMap<String, ArrayList<String>> rules/*, ArrayList<String> orderOfRules*/)
    {
        //this.orderOfRules = orderOfRules;
        table = new ArrayList<>();
        /*
        for (int i = 0; i < input.size() - 1; i++) {
            ArrayList<String> currentRow = new ArrayList<>();
            table.add(currentRow);
        }
        */

        this.rules = rules;

        ArrayList<String> row =  new ArrayList<>();
        //i = how many elements we should remove  on the row
        row  = generateLastRow(row, input, this.rules);
        table.add(input);
        for (int i = 1; i < input.size(); i++) {
            ArrayList<String> currentrow =  new ArrayList<>();
            //i = how many elements we should remove  on the row
            currentrow  = generateRow(currentrow, input, this.rules, i);
            table.add(currentrow);
        }
    }

    private ArrayList<String> generateRow(ArrayList<String> currentrow, ArrayList<String> input, HashMap<String, ArrayList<String>> rules, int i) {
        //go through every cell in the row
        for (int j = 0; j < input.size() - i; j++) {
            ArrayList<String> currentCell = generateCurrentCell(i,j,rules);
            String finalFormatForTerminals = getFormatForTerminals(currentCell);
            currentrow.add(finalFormatForTerminals);
        }
        return  currentrow;
    }

    private ArrayList<String> generateCurrentCell(int i, int j, HashMap<String, ArrayList<String>> rules) {
        ArrayList<String> currentCell = new ArrayList<>();
        ArrayList<String> firstList = new ArrayList<>();
        for (int k = 0; k < i; k++) {
            String first = table.get(k).get(j);
            firstList.add(first);
        }
        int l = j + 1;
        ArrayList<String> secondList = new ArrayList<>();
        for (int k = i - 1; k >= 0; k--) {
            String second = table.get(k).get(l);
            l+=1;
            secondList.add(second);
        }
        for (int k = 0; k < firstList.size(); k++) {
            ArrayList<String> dotProduct = getDotProduct(firstList.get(k), secondList.get(k));
            for (int m = 0; m < dotProduct.size(); m++) {
                ArrayList<String> terminals = findTerminalsVersion2(dotProduct.get(m));
                for (int n = 0; n < terminals.size(); n++) {
                    currentCell.add(terminals.get(n));
                }

            }
        }
        
        
        return currentCell;
    }

    private ArrayList<String> findTerminalsVersion2(String s) {
        ArrayList<String> terminals = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : rules.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            for (String currentTerminal: value) {
                if (currentTerminal.equals(s))
                {
                    terminals.add(key);
                }
            }
        }
        return terminals;
    }

    private ArrayList<String> getDotProduct(String s, String s1) {
        String[] firstArray = s.split(",");
        String[] secondArray = s1.split(",");
        ArrayList<String> finalArray = new ArrayList<>();
        for (int i = 0; i < firstArray.length; i++) {
            for (int j = 0; j < secondArray.length; j++) {
                String current = firstArray[i] + secondArray[j];
                finalArray.add(current);
            }
        }
        return finalArray;
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
                    terminals.add(key);
                }
            }
        }
        return terminals;
    }
}
