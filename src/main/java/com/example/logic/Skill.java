package com.example.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Skill {
    private String question;
    private List<Slot> slots;
    private HashMap<String, String> actions;

    public Skill(String question){
        this.question = question;
    }

    /**
     * This method retrieves the correct answer based on a prompt
     * @param prompt The prompt entered by the user
     * @return The correct action based on the slots contained in the prompt
     */
    public String getAction(String prompt){
        List<Slot> promptSlots = extractSlots(prompt);
        StringBuilder sb = new StringBuilder();
        for(Slot s : promptSlots){
            sb.append(String.valueOf(slots.indexOf(s))+"|");
        }
        sb.setLength(sb.length() - 1);
        String result = actions.get(sb.toString());
        if(result != null){
            return result;
        }
        return "Sorry, I don't think I can answer that.";
    }

    /**
     * This method extracts all the slots that are contained in a prompt
     * @param prompt The prompt entered by the user
     * @return A list of the slots contained in the prompt
     */
    private List<Slot> extractSlots(String prompt){
        List<Slot> result = new ArrayList<Slot>();
        //TODO make sure we handle puncuation correctly here
        String[] splitQuestion = question.split(" ");
        String[] splitPrompt = prompt.split(" ");
        for(int i = 0; i < splitQuestion.length; i++){
            String qWord = splitQuestion[i];
            String pWord = splitPrompt[i];
            if(i > (splitPrompt.length - 1)){
                break;
            }
            else{
                if(!qWord.equalsIgnoreCase(pWord) && (qWord.charAt(0) == '<' && qWord.charAt(qWord.length()-1) == '>')){
                    String slotName = qWord.substring(1, qWord.length()-1);
                    for(Slot slot : slots){
                        if(slot.getSlotName().equalsIgnoreCase(slotName) && slot.getSlotContent().equalsIgnoreCase(pWord)){
                            result.add(slot);
                        }
                    }
                }
            }
        }
        return result;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public List<Slot> getSlots(){
        return slots;
    }

    public void setSlots(List<Slot> slots){
        this.slots = slots;
    }

    public HashMap<String, String> getActions(){
        return actions;
    }

    public void setActions(HashMap<String, String> actions){
        this.actions = actions;
    }
}


