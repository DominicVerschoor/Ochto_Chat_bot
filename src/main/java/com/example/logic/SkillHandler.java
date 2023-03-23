package com.example.logic;

import java.io.File;
import java.util.*;

public class SkillHandler {
    private static ArrayList<Skill> skills;

    public SkillHandler() {
        skills = new ArrayList<>();
        loadSkills();
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public static String cleanWord(String input) {
        return input.replaceAll("[^\\p{L}\\p{N}]+", "");
    }

    public String getBetweenWords(String input, String[] surrounding) {
        String[] splitInput = input.split(" ");
        String output = "";

        if (surrounding[0] == null) {
            for (String s : splitInput) {
                if (s.equalsIgnoreCase(surrounding[1])) {
                    break;
                }
                output += s;
            }
        } else if (surrounding[1] == null) {
            for (int i = 0; i < splitInput.length; i++) {
                if (splitInput[i].equalsIgnoreCase(surrounding[0])) {
                    String[] leftovers = Arrays.copyOfRange(splitInput, i + 1, splitInput.length);
                    output = String.join("", leftovers);
                    break;
                }
            }
        } else {
            int startIndex = 0;
            int endIndex = 0;
            for (int i = 0; i < splitInput.length; i++) {
                if (splitInput[i].equalsIgnoreCase(surrounding[0])) {
                    startIndex = i + 1;
                }

                if (splitInput[i].equalsIgnoreCase(surrounding[1])) {
                    endIndex = i;
                    break;
                }
            }
            String[] between = Arrays.copyOfRange(splitInput, startIndex, endIndex);
            output = String.join("", between);
        }

        return output;
    }

    public String findSkill(String input) {
        String finalKey = "";
//        String[] splitInput = input.split(" ");
        String cleanInput = cleanWord(input);

        for (Skill skill : skills) {
            String[] splitSkill = skill.getQuestion().split(" ");

            Set<String> allKeys = skill.getActions().keySet();

            for (String key : allKeys) {
                String[] currentKey = key.split("[|]"); //currentKey[0] = 0, currentKey[1] = 4
                ArrayList<Integer> slotIndex = skill.getSlotIndex();
                // slotIndex[0] = 6 = <DAY> = currentKey[0] = 0 = Monday

                for (int i = 0; i < slotIndex.size(); i++) {
                    Slot currentSlot = skill.getSlots().get(Integer.parseInt(currentKey[i]));
                    splitSkill[slotIndex.get(i)] = currentSlot.getSlotContent();
                }

                String cleanQs = cleanWord(String.join("", splitSkill));
                // WhatLecturedoIhaveonMondayat12am

                if (cleanInput.equalsIgnoreCase(cleanQs)) {
                    return skill.getActions().get(key);
                }
            }

//            for (int i = 0; i < splitSkill.length; i++) {
//
////                if (splitSkill[i].contains("<") && splitSkill[i].contains(">")){
////                    surround = skill.getSurroundWords(splitSkill[i]);
////
////                }
////
////
////                if (!(splitSkill[i].contains("<") && splitSkill[i].contains(">"))
////                        && !splitInput[i].equalsIgnoreCase(splitSkill[i])) {
////                    outputSkill = null;
////                    break;
////                }
//            }
        }


//        if (outputSkill == null) {
//            Skill temp_outputSkill = null;
//            for (Skill skill : skills) {
//                String[] splitSkill = skill.getQuestion().split(" ");
//
//                if (splitInput.length == splitSkill.length) {
//                    temp_outputSkill = skill;
//                    int incorrect_count = 0;
//                    for (int i = 0; i < splitInput.length; i++) {
//                        if (!(splitInput[i].equalsIgnoreCase(splitSkill[i]))) {
//                            incorrect_count++;
//                        }
//
//                    }
//                    if (incorrect_count <= 2) {
//                        outputSkill = temp_outputSkill;
//                    }
//
//                }
//            }
//        }


        return "SRY";
    }

    public void loadSkills() {
        // Create a File object for the folder
        File folder = new File("Questions/");

        // Get a list of all the files in the folder
        File[] files = folder.listFiles();

        // Loop through the list of files
        assert files != null;
        for (File file : files) {
            skills.add(CSVHandler.readSkill((file)));
        }
    }
}



