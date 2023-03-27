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
        String cleanInput = cleanWord(input);
        HashMap<Integer, String> spellStore = new HashMap<>();

        for (Skill skill : skills) { //O(w) w= number of skills
            String[] splitSkill = skill.getQuestion().split(" ");

            Set<String> allKeys = skill.getActions().keySet();

            for (String key : allKeys) { // O(n) n=number of keys
                String[] currentKey = key.split("[|]");
                ArrayList<Integer> slotIndex = skill.getSlotIndex();

                for (int i = 0; i < currentKey.length; i++) { //O(m) m=number of slots
                    Slot currentSlot = skill.getSlots().get(Integer.parseInt(currentKey[i]));
                    splitSkill[slotIndex.get(i)] = currentSlot.getSlotContent();
                }

                String cleanQs = cleanWord(String.join("", splitSkill));

                if (cleanInput.equalsIgnoreCase(cleanQs)) {
                    return skill.getActions().get(key);
                }else{
                    spellStore.put(differenceCounter(cleanQs, cleanInput), String.join(" ", splitSkill));
                }
                // O(wnm)
            }
        }

        int min = 999999999;
        for (Integer diff: spellStore.keySet()) {
            if (diff < min){
                min = diff;
            }
        }

        return "Did you mean: " + spellStore.get(min);
    }

    public int differenceCounter(String s1, String s2){
        int count = 0;
        int length = Math.min(s1.length(), s2.length());
        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) != s2.charAt(i)) { // Compare the characters at each index
                count++;
            }
        }

        count += Math.abs(s1.length() - s2.length());
        return count;
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



