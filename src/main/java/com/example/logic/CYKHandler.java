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
        String output = "No answer found";
        prompt = cleanWord(prompt);
        SpellChecker spellChecker = new SpellChecker(prompt);
        spellChecker.generateDictionary(rules);
        ArrayList<String> correctedPrompts = spellChecker.correctedPrompts();
        for (int i = 0; i < rules.size(); i++) {
            for (String curPrompt : correctedPrompts) {
                CYK run = new CYK(rules.get(i), actions.get(i), curPrompt);
                output = read(curPrompt);
                if (run.belongs()) {
                    return run.getAction(output);
                }
                if (output.contains("<") && output.contains(">")) {
                    return output;
                }
            }
        }
        return output;
    }

    private String read(String prompt) {
        StringBuilder output = new StringBuilder();
        output.append("Can you also give me the");
        ArrayList<File> files = getAllQuestions();
        HashMap<String, ArrayList<String>> terminalMap = new HashMap<>();
        int min = Integer.MAX_VALUE;
        String finalPrompt = "";
        String finalContent = "";
        int counter = 0;
        for (File file : files) {
            CSVReader reader = new CSVReader(file);
            ArrayList<String> ruleContent = reader.getRuleContent();

            for (String content : ruleContent) {
                int compare = comparePrompts(prompt, content);
                if (compare < min) {
                    finalContent = content;
                    finalPrompt = prompt;
                    min = compare;
                    terminalMap = reader.getTerminalMap();
                    counter = compare;
                }
            }
        }

        if (counter == 1) {
            return "";
        } else if (extractSlots(finalPrompt, finalContent, terminalMap) != null) {
            ArrayList<String> missingSlots = extractSlots(finalPrompt, finalContent, terminalMap);
            assert missingSlots != null;
            for (String slot : missingSlots) {
                output.append(", ").append(slot);
            }

        } else {
            return "Can you give me more information";
        }

        if (counter > 5) {
            return "Sorry I don't know";
        }
        return output.toString();
    }

    private ArrayList<String> extractSlots(String prompt, String content, HashMap<String, ArrayList<String>> terminalMap) {
        ArrayList<String> slots = new ArrayList<>();
        String[] splitContent = content.toLowerCase().split(" ");

        for (String con : splitContent) {
            if (con.contains("<") && con.contains(">")) {
                slots.add(con);
            }
        }

        Iterator<String> iterator = slots.iterator();
        while (iterator.hasNext()) {
            String slot = iterator.next();
            ArrayList<String> currentTerminal = terminalMap.get(slot);
            for (String ter : currentTerminal) {
                if (prompt.toLowerCase().contains(ter.replaceAll("[^\\p{L}\\p{N}]+", "").toLowerCase())) {
                    iterator.remove();
                    break; // Exit the inner loop once a match is found
                }
            }
            if (slots.isEmpty()) {
                return null;
            }
        }

        return slots;
    }

    private int comparePrompts(String prompt, String content) {
        String[] splitPrompt = prompt.split(" ");
        String[] splitContent = content.split(" ");

        int length = Integer.min(splitContent.length, splitPrompt.length);
        int counter = Math.max(Math.abs(splitContent.length - splitPrompt.length), 2);
        for (int i = 0; i < length; i++) {
            if (!splitContent[i].equalsIgnoreCase(splitPrompt[i])) {
                counter++;
            }
        }

        return counter;
    }

    private ArrayList<File> getAllQuestions() {
        String folderPath = "Questions";
        ArrayList<File> allFiles = new ArrayList<>();

        // Get a list of all the files in the folder
        File folder = new File(folderPath);
        File[] fileList = folder.listFiles();

        // Loop over each file in the folder and read the first line
        assert fileList != null;
        for (File file : fileList) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine();
                allFiles.add(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return allFiles;
    }

    private static String cleanWord(String input) {
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
}
