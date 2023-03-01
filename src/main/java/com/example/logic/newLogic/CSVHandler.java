package com.example.logic.newLogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVHandler {

    //Example usage
    public static void main(String[] args) {
        CSVHandler handler = new CSVHandler();

        //reading a skill from a csv file
        Skill test = handler.readSkill("test.csv");
        System.out.println(test.getAction("What lecture do I have on monday"));


        //Saving a skill to a csv file
        handler.writeSkill(test);
    }

    /**
     * This method will translate a skill into CSV-representation and saves it to a file
     * @param skill A skill that you want to save to a csv file
     */
    public void writeSkill(Skill skill){
        try {
            //TODO make sure that we select appropriate Filenames for each csv and save it to the correct folder
            BufferedWriter writer = new BufferedWriter(new FileWriter("saveTest.csv"));
            writer.write(skill.getQuestion() + "\n");
            for(Slot s : skill.getSlots()){
                writer.write("<"+s.getSlotName()+">"+ " " + s.getSlotContent()+"\n");
            }

            for (Map.Entry<String, String> entry : skill.getActions().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                writer.write("Action ");
                String[] indices = key.split("|");
                for(String idx : indices){
                    Slot s = skill.getSlots().get(Integer.parseInt(idx));
                    writer.write("<"+s.getSlotName()+"> "+s.getSlotContent()+" ");
                }
                writer.write(value + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method loads skill classes from the csv file representations
     * @param fileName The name of the csv file containing the representation of the skill
     * @return The skill class
     */
    public Skill readSkill(String fileName){
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Skill result = new Skill(lines.get(0));
        List<Slot> slotList = new ArrayList<Slot>();
        HashMap<String, String> actions = new HashMap<String, String>();
        for(int i = 1; i < lines.size(); i++){
            String[] words = lines.get(i).split(" ");
            if(!words[0].equalsIgnoreCase("action")){
                slotList.add(new Slot(words[0].substring(1, words[0].length()-1), words[1]));
            }
            else{
                StringBuilder sb = new StringBuilder();
                for(int j = 1; j < words.length; j+=2){
                    if(words[j].charAt(0) == '<' && words[j].charAt(words[j].length()-1) == '>'){
                        for(int k = 0; k < slotList.size(); k++){
                            if(slotList.get(k).getSlotName().equalsIgnoreCase(words[j].substring(1, words[j].length()-1)) && slotList.get(k).getSlotContent().equalsIgnoreCase(words[j+1])){
                                sb.append(String.valueOf(k)+"|");
                            }
                        }
                    }
                    else{
                        sb.setLength(sb.length() - 1);
                        String key = sb.toString();
                        String action = String.join(" ", Arrays.copyOfRange(words, j, words.length));
                        if(key != null){
                            actions.put(key, action);
                        }
                        else{
                            actions.put("-1", action);
                        }
                    }
                }
            }
        }
        result.setSlots(slotList);
        result.setActions(actions);
        return result;
    }
}
