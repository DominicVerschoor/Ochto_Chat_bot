package com.example.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CYKHandler {
    ArrayList<HashMap<String, ArrayList<String>>> rules;
    ArrayList<ArrayList<Action>> actions;

    public static void main(String[] args) {
        CYKHandler handler = new CYKHandler();
        System.out.println(handler.retrieveAnswer("Which lectures are there on Monday at 9"));
    }

    public CYKHandler(){
        rules = readRules();
        for(int i = 0; i < rules.size(); i++){
            rules.set(i, convertToCNF(rules.get(i)));
        }
        actions = readActions();
    }

 
    public String retrieveAnswer(String prompt){
        for(int i = 0; i < rules.size(); i++){
            CYK run = new CYK(rules.get(i), actions.get(i), prompt);
            if(run.belongs()){
                return run.getAction();
            }
        }
        return "No answer found";
    }

    private HashMap<String, ArrayList<String>> convertToCNF(HashMap<String, ArrayList<String>> rules){
        HashMap<String, ArrayList<String>> newRules = new HashMap<String, ArrayList<String>>(rules);

        // START 
        //newRules.put("<start>", new ArrayList<String>(List.of("<s>")));

        //TERM
        HashMap<String, ArrayList<String>> placeHolder = new HashMap<String, ArrayList<String>>();
        HashMap<String, String> newNTs = new HashMap<String, String>();
        for(Map.Entry<String, ArrayList<String>> entry : newRules.entrySet()){
            ArrayList<String> rhs = entry.getValue();
            for(int i = 0; i < rhs.size(); i++){
                String[] w = rhs.get(i).split(" ");
                ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));
                for(int j = 0; j < words.size(); j++){
                    if(!(words.get(j).charAt(0) == '<') && words.size() > 1){
                        if(newNTs.get(words.get(j)) == null){
                            newNTs.put(words.get(j), "<"+words.get(j)+"T>");
                            placeHolder.put("<" + words.get(j) + "T>", new ArrayList<String>(List.of(words.get(j))));
                            words.set(j, "<"+words.get(j)+"T>");

                        }
                        else{
                            words.set(j, newNTs.get(words.get(j)));
                        }
                    }
                }
                w = words.toArray(new String[words.size()]);
                rhs.set(i, String.join(" ", w)); 
            }
            entry.setValue(rhs);
        }
        newRules.putAll(placeHolder);

        // BIN
        placeHolder = new HashMap<String, ArrayList<String>>();
        int idx = 0;
        for(Map.Entry<String, ArrayList<String>> entry : newRules.entrySet()){
            ArrayList<String> rhs = entry.getValue();
            for(int i = 0; i < rhs.size(); i++){
                String[] w = rhs.get(i).split(" ");
                ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));
                if(words.size() > 2){
                    ArrayList<String> copy = new ArrayList<String>(words);

                    words.subList(1, words.size()).clear();
                    words.add("<" + words.get(0).substring(1, words.get(0).length()-1) + String.valueOf(idx) + String.valueOf(i) + "0EXT>");
                    w = words.toArray(new String[words.size()]);
                    rhs.set(i, String.join(" ", w)); 

                    for(int j = 1; j < copy.size()-2; j++){
                        ArrayList<String> newRHS = new ArrayList<String>();
                        newRHS.add(copy.get(j) + " <" + copy.get(j).substring(1, copy.get(j).length()-1) + String.valueOf(idx) + String.valueOf(i) +String.valueOf(j) + "EXT>");
                        placeHolder.put("<" + copy.get(j-1).substring(1, copy.get(j-1).length()-1) + String.valueOf(idx) + String.valueOf(i) + String.valueOf(j-1) + "EXT>", newRHS);
                    }

                    ArrayList<String> newRHS = new ArrayList<String>(List.of(copy.get(copy.size()-2) + " " + copy.get(copy.size()-1)));
                    placeHolder.put("<"+ copy.get(copy.size()-3).substring(1, copy.get(copy.size()-3).length()-1) + String.valueOf(idx) + String.valueOf(i) + String.valueOf(copy.size()-3) + "EXT>", newRHS);
                }
            }
            entry.setValue(rhs);
            idx++;
        }
        newRules.putAll(placeHolder);

        // UNIT 
        boolean foundUnitRule;
        do{
            foundUnitRule = false;
            for(Map.Entry<String, ArrayList<String>> entry : newRules.entrySet()){
                ArrayList<String> rhs = entry.getValue();
                for(int i = 0; i < rhs.size(); i++){
                    String[] w = rhs.get(i).split(" ");
                    ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));
                    if(words.size() == 1 && words.get(0).charAt(0) == '<'){
                        ArrayList<String> substitute = newRules.get(words.get(0));
                        rhs.remove(i);
                        rhs.addAll(substitute);
                        foundUnitRule = true;
                    }
                }
                entry.setValue(rhs);
            }
        } while (foundUnitRule);
        return newRules;
    }

    private ArrayList<HashMap<String, ArrayList<String>>> readRules(){
        ArrayList<HashMap<String, ArrayList<String>>> result = new ArrayList<HashMap<String, ArrayList<String>>>();
        File folder = new File("Questions/");
        File[] files = folder.listFiles();
        assert files != null;
        for(File file : files){
            result.add(CSVHandler.getRules(file));
        }
        return result;
    }

    private ArrayList<ArrayList<Action>> readActions(){
        ArrayList<ArrayList<Action>> result = new ArrayList<ArrayList<Action>>();
        File folder = new File("Questions/");
        File[] files = folder.listFiles();
        assert files != null;
        for(File file : files){
            result.add(CSVHandler.getActions(file));
        }
        return result;
    }
}
