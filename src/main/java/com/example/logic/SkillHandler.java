package com.example.logic;

import java.io.File;
import java.util.*;

public class SkillHandler {
    private static ArrayList<Skill> skills;

    public SkillHandler() {
        skills = new ArrayList<>();
        loadSkills();
    }

    public String findSkill(String input){
        String cleanedInput = cleanWord(input);
        for(Skill skill: skills){
            ArrayList<Slot> inputSlots = extractSlotsFromInput(skill, cleanedInput);
            if(inputSlots.size() > 0){
                List<Slot> slots = skill.getSlots();
                String[] keys = new String[inputSlots.size()];
                for(int i = 0; i < keys.length; i++){
                    keys[i] = String.valueOf(slots.indexOf(inputSlots.get(i)));
                }
                String key = String.join("|", keys);
                if(skill.getActions().get(key) != null){
                    return skill.getActions().get(key);
                }
                else if(skill.getActions().get("-1") != null){
                    return skill.getActions().get("-1");
                }
            }
        }
        return findClosestMatch(input);
    }

    private String findClosestMatch(String input){
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

                spellStore.put(differenceCounter(cleanQs, cleanInput), String.join(" ", splitSkill));
                // O(wnm)
            }
        }

        int min = Integer.MAX_VALUE;
        for (Integer diff: spellStore.keySet()) {
            if (diff < min){
                min = diff;
            }
        }

        return "Did you mean: " + spellStore.get(min) + ". Please type it.";
    }

    private ArrayList<Slot> extractSlotsFromInput(Skill skill, String cleanedInput){
        ArrayList<Slot> matchedSlots = new ArrayList<Slot>();
        ArrayList<Integer> slotIndices = skill.getSlotIndex();
        String[] splitQuestion = skill.getQuestion().split(" ");
        for(int i = 0; i < splitQuestion.length; i++){
            splitQuestion[i] = cleanWord(splitQuestion[i]);
        }
        ArrayList<String> slotNames = new ArrayList<String>();
        for(Integer idx : slotIndices){
            slotNames.add(splitQuestion[idx]);
        }
        List<Slot> slots = skill.getSlots();
        // For each slot Location
        for(int i = 0; i < slotIndices.size(); i++){
            // Test for each slot from the skill
            for(Slot slot : slots){
                boolean contained = false;
                // If it is the right kind of slot (slotname matches)
                if(cleanWord(slot.getSlotName()).equals(slotNames.get(i))){
                    contained = true;
                    // substitute the slot content in the location
                    splitQuestion[slotIndices.get(i)] = cleanWord(slot.getSlotContent());

                    //For each word in the question up until the slot location
                    int cc = 0;
                    int idx = 0;
                    while(contained && idx <= slotIndices.get(i)){
                        for(int j = 0; j < splitQuestion[idx].length(); j++){
                            if(cc > cleanedInput.length()-1 || cleanedInput.charAt(cc) != splitQuestion[idx].charAt(j)){
                                contained = false;
                                break;
                            }
                            cc++;
                        }
                        idx++;
                    }
                }
                if(contained){
                    matchedSlots.add(slot);
                    break;
                }
            }
        }


        return matchedSlots;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public static String cleanWord(String input) {
        return input.replaceAll("[^\\p{L}\\p{N}]+", "").toLowerCase();
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

    public String findASkill(String input) {
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

        return "Did you mean: " + spellStore.get(min) + ". Please type it.";
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



