package com.example.logic;

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

    /**
     * This method will translate a skill into CSV-representation and saves it to a file
     *
     * @param skill A skill that you want to save to a csv file
     */
    public static void writeSkill(Skill skill, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Questions/"+fileName));
            writer.write(skill.getQuestion() + "\n");
            for (Slot s : skill.getSlots()) {
                writer.write("<" + s.getSlotName() + ">" + " " + s.getSlotContent() + "\n");
            }

            for (Map.Entry<String, String> entry : skill.getActions().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                writer.write("Action ");
                String[] indices = key.split("[|]");
                for (String idx : indices) {
                    Slot s = skill.getSlots().get(Integer.parseInt(idx));
                    writer.write("<" + s.getSlotName() + "> " + s.getSlotContent() + " ");
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
     *
     * @param fileName The name of the csv file containing the representation of the skill
     * @return The skill class
     */
    public static Skill readSkill(File fileName) {
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

        Skill result = new Skill(lines.get(0));
        List<Slot> slotList = new ArrayList<>();
        HashMap<String, String> actions = new HashMap<>();
        for (int line = 1; line < lines.size(); line++) {
            String[] words = lines.get(line).split(" ");
            if (!words[0].equalsIgnoreCase("action")) {
                String slotName = "";
                String slotContent = "";
                for (int i = 0; i < words.length; i++) {
                    slotName = slotName + cleanWord(words[i]);
                    if (words[i].contains(">")) {
                        for (int j = i + 1; j < words.length; j++) {
                            slotContent = slotContent + words[j];
                        }
                        break;
                    }
                }
                slotList.add(new Slot(slotName, slotContent));
            } else {
                StringBuilder sb = new StringBuilder();
                for (int j = 1; j < words.length; j += 2) {
                    if (words[j].contains("<") && words[j].contains(">")) {
                        for (int k = 0; k < slotList.size(); k++) {
                            if (slotList.get(k).getSlotName().equalsIgnoreCase(words[j].substring(1, words[j].length() - 1))
                                    && slotList.get(k).getSlotContent().equalsIgnoreCase(words[j + 1])) {
                                sb.append(String.valueOf(k) + "|");
                            }
                        }
                    } else {
                        sb.setLength(sb.length() - 1);
                        String key = sb.toString();
                        String action = String.join(" ", Arrays.copyOfRange(words, j, words.length));
                        if (key != null) {
                            actions.put(key, action);
                            break;
                        } else {
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

    public static String cleanWord(String input) {
        return input.replaceAll("[^\\p{L}\\p{N}]+", "");
    }
}
