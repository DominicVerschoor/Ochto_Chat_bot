package com.example.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class CYKHandler {
    ArrayList<HashMap<String, ArrayList<String>>> rules;
    ArrayList<ArrayList<Action>> actions;

    public static void main(String[] args) {
        CYKHandler handler = new CYKHandler();
        System.out.println(handler.retrieveAnswer("How is the weather in maastricht"));
    }

    public CYKHandler() {
        rules = readRules();
        rules.replaceAll(this::convertToCNF);
        actions = readActions();
    }

    public String retrieveAnswer(String prompt) {
        prompt = cleanWord(prompt);
        for (int i = 0; i < rules.size(); i++) {
            SpellChecker spellChecker = new SpellChecker(prompt);
            spellChecker.generateDictionary(rules.get(i));
            ArrayList<String> correctedPrompts = spellChecker.correctedPrompts();
            for (String curPrompt : correctedPrompts) {
                System.out.println(curPrompt);
                CYK run = new CYK(rules.get(i), actions.get(i), curPrompt);
                if (run.belongs()) {
                    return run.getAction();
                }
            }
        }
        return "No answer found";
    }

    public static String cleanWord(String input) {
        return input.replaceAll("[^\\p{L}\\p{N}\\s]+", "");
    }

    private HashMap<String, ArrayList<String>> convertToCNF(HashMap<String, ArrayList<String>> rules) {
        HashMap<String, ArrayList<String>> newRules = new HashMap<String, ArrayList<String>>(rules);

        // START 
        //newRules.put("<start>", new ArrayList<String>(List.of("<s>")));

        //TERM
        HashMap<String, ArrayList<String>> placeHolder = new HashMap<String, ArrayList<String>>();
        HashMap<String, String> newNTs = new HashMap<String, String>();
        for (Map.Entry<String, ArrayList<String>> entry : newRules.entrySet()) {
            ArrayList<String> rhs = entry.getValue();
            for (int i = 0; i < rhs.size(); i++) {
                String[] w = rhs.get(i).split(" ");
                ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));
                for (int j = 0; j < words.size(); j++) {
                    if (!(words.get(j).charAt(0) == '<') && words.size() > 1) {
                        if (newNTs.get(words.get(j)) == null) {
                            newNTs.put(words.get(j), "<" + words.get(j) + "T>");
                            placeHolder.put("<" + words.get(j) + "T>", new ArrayList<String>(List.of(words.get(j))));
                            words.set(j, "<" + words.get(j) + "T>");

                        } else {
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
        for (Map.Entry<String, ArrayList<String>> entry : newRules.entrySet()) {
            ArrayList<String> rhs = entry.getValue();
            for (int i = 0; i < rhs.size(); i++) {
                String[] w = rhs.get(i).split(" ");
                ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));
                if (words.size() > 2) {
                    ArrayList<String> copy = new ArrayList<String>(words);

                    words.subList(1, words.size()).clear();
                    words.add("<" + words.get(0).substring(1, words.get(0).length() - 1) + String.valueOf(idx) + String.valueOf(i) + "0EXT>");
                    w = words.toArray(new String[words.size()]);
                    rhs.set(i, String.join(" ", w));

                    for (int j = 1; j < copy.size() - 2; j++) {
                        ArrayList<String> newRHS = new ArrayList<String>();
                        newRHS.add(copy.get(j) + " <" + copy.get(j).substring(1, copy.get(j).length() - 1) + String.valueOf(idx) + String.valueOf(i) + String.valueOf(j) + "EXT>");
                        placeHolder.put("<" + copy.get(j - 1).substring(1, copy.get(j - 1).length() - 1) + String.valueOf(idx) + String.valueOf(i) + String.valueOf(j - 1) + "EXT>", newRHS);
                    }

                    ArrayList<String> newRHS = new ArrayList<String>(List.of(copy.get(copy.size() - 2) + " " + copy.get(copy.size() - 1)));
                    placeHolder.put("<" + copy.get(copy.size() - 3).substring(1, copy.get(copy.size() - 3).length() - 1) + String.valueOf(idx) + String.valueOf(i) + String.valueOf(copy.size() - 3) + "EXT>", newRHS);
                }
            }
            entry.setValue(rhs);
            idx++;
        }
        newRules.putAll(placeHolder);

        // UNIT 
        boolean foundUnitRule;
        do {
            foundUnitRule = false;
            for (Map.Entry<String, ArrayList<String>> entry : newRules.entrySet()) {
                ArrayList<String> rhs = entry.getValue();
                for (int i = 0; i < rhs.size(); i++) {
                    String[] w = rhs.get(i).split(" ");
                    ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));
                    if (words.size() == 1 && words.get(0).charAt(0) == '<') {
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

    private ArrayList<HashMap<String, ArrayList<String>>> readRules() {
        ArrayList<HashMap<String, ArrayList<String>>> result = new ArrayList<HashMap<String, ArrayList<String>>>();
        File folder = new File("Questions/");
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            result.add(CSVHandler.getRules(file));
        }
        return result;
    }

    private ArrayList<ArrayList<Action>> readActions() {
        ArrayList<ArrayList<Action>> result = new ArrayList<ArrayList<Action>>();
        File folder = new File("Questions/");
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            result.add(CSVHandler.getActions(file));
        }
        return result;
    }

    private ArrayList<HashMap<String, ArrayList<String>>> generateDictionary() {
        ArrayList<HashMap<String, ArrayList<String>>> output = new ArrayList<>();
        File folder = new File("Questions/");
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            output.add(getTerminals(file));
        }
        return output;
    }

    public static HashMap<String, ArrayList<String>> getTerminals(File fileName) {
        HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : lines) {
            String[] words = line.split(" ");
            String cleanLine = line.substring(6 + words[1].length(), line.length());
            if (words[0].equalsIgnoreCase("Rule")) {
                boolean terminal = false;
                String[] entries = cleanLine.split("[|]");
                ArrayList<String> newEntries = new ArrayList<String>(Arrays.asList(entries));
                for (int i = 0; i < newEntries.size(); i++) {
                    if (newEntries.get(i).charAt(0) == ' ') {
                        newEntries.set(i, newEntries.get(i).substring(1, newEntries.get(i).length()));
                    }
                    if (newEntries.get(i).charAt(newEntries.get(i).length() - 1) == ' ') {
                        newEntries.set(i, newEntries.get(i).substring(0, newEntries.get(i).length() - 1));
                    }
                }
                for (int i = 0; i < newEntries.size(); i++) {
                    String[] entryWords = newEntries.get(i).split(" ");
                    if (entryWords.length > 1 || entryWords[0].charAt(0) == '<') {
                        for (int j = 0; j < entryWords.length; j++) {
                            String word = entryWords[j].replaceAll("\\s", "");
                            if (word.charAt(0) == '<') {
                                word = word.substring(1, word.length() - 1);
                            } else {
                                word = "W";
                                terminal = false;
                            }
                            entryWords[j] = word;
                        }
                    }
                    ArrayList<String> temp = new ArrayList<String>(Arrays.asList(entryWords));
                    for (int j = 0; j < temp.size() - 1; j++) {
                        if (temp.get(j).equalsIgnoreCase("W") && temp.get(j + 1).equalsIgnoreCase("W")) {
                            temp.remove(j);
                        }
                    }
                    entryWords = temp.toArray(new String[temp.size()]);
                    newEntries.set(i, String.join("", entryWords));
                }
                if (terminal) {
                    result.put(words[1].substring(1, words[1].length() - 1), newEntries);
                }
            }
        }
        return result;
    }
}
