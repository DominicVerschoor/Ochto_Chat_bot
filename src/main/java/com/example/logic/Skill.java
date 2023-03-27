package com.example.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Skill {
    private String question;
    private List<Slot> slots;
    private HashMap<String, String> actions;

    public Skill(String question) {
        this.question = question;
    }

    /**
     * This method retrieves the correct answer based on a prompt
     *
     * @param prompt The prompt entered by the user
     * @return The correct action based on the slots contained in the prompt
     */
    public String getAction(String prompt) {
        List<Slot> promptSlots = extractSlots(prompt);
        StringBuilder sb = new StringBuilder();
        for (Slot s : promptSlots) {
            sb.append(String.valueOf(slots.indexOf(s)) + "|");
        }
        sb.setLength(sb.length() - 1);
        String result = actions.get(sb.toString());
        if (result != null) {
            return result;
        }
        return "Sorry, I don't think I can answer that.";
    }

    /**
     * This method extracts all the slots that are contained in a prompt
     *
     * @param prompt The prompt entered by the user
     * @return A list of the slots contained in the prompt
     */
    private List<Slot> extractSlots(String prompt) {
        //input = input.replaceAll("[^\\p{L}\\p{N}]+", ""); remove everything except numbers
        //String current = skill.getQuestion().replaceAll("[^\\w<>]", ""); remove everything except<>
        List<Slot> result = new ArrayList<>();
        //TODO make sure we handle puncuation correctly here
        String[] splitQuestion = question.split(" ");
        String[] splitPrompt = prompt.split(" ");
        for (int i = 0; i < splitQuestion.length; i++) {
            String qWord = splitQuestion[i];
            String pWord = splitPrompt[i];
            if (i > (splitPrompt.length - 1)) {
                break;
            } else {
                if (!qWord.equalsIgnoreCase(pWord)
                        && (qWord.charAt(0) == '<' && qWord.charAt(qWord.length() - 1) == '>')) {
//                    String slotName = qWord.substring(1, qWord.length()-1);
                    String qSlotName = cleanWord(qWord);
                    for (Slot slot : slots) {
                        String sSlotName = cleanWord(slot.getSlotName());
                        String cleanedPWord = cleanWord(pWord);
                        String cleanedSlotContent = cleanWord(slot.getSlotContent());
                        if (sSlotName.equalsIgnoreCase(qSlotName) && cleanedSlotContent.equalsIgnoreCase(cleanedPWord)) {
                            result.add(slot);
                        }
                    }
                }
            }
        }
        return result;
    }

    public String[] getSurroundWords(String target) {
        String[] question = getQuestion().split(" ");
        String[] output = new String[2];

        for (int i = 0; i < question.length; i++) {
            if (question[i].equalsIgnoreCase(target)) {

                if (i == 0) {
                    output[1] = question[i + 1];
                } else if (i == question.length - 1) {
                    output[0] = question[i - 1];
                } else {
                    output[0] = question[i - 1];
                    output[1] = question[i + 1];
                }
            }
        }

        return output;
    }

    public ArrayList<Integer> getSlotIndex() {
        ArrayList<Integer> output = new ArrayList<>();
        String[] qs = getQuestion().split(" ");

        for (int i = 0; i < qs.length; i++) {
            if (qs[i].contains("<") && qs[i].contains(">")) {
                output.add(i);
            }
        }

        return output;
    }

    public String cleanWord(String input) {
        return input.replaceAll("[^\\p{L}\\p{N}]+", "");
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public HashMap<String, String> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, String> actions) {
        this.actions = actions;
    }
}


