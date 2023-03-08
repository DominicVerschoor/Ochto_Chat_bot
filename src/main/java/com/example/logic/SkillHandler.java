package com.example.logic;

import java.io.File;
import java.util.ArrayList;

public class SkillHandler {
    private static ArrayList<Skill> skills;

    public SkillHandler() {
        skills = new ArrayList<>();
        loadSkills();
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public Skill findSkill(String input) {
        Skill outputSkill = null;
        String[] splitInput = input.split(" ");

        for (Skill skill : skills) {
            String[] splitSkill = skill.getQuestion().split(" ");

            if (splitInput.length == splitSkill.length) {
                outputSkill = skill;

                for (int i = 0; i < splitInput.length; i++) {

                    if (!(splitSkill[i].contains("<") && splitSkill[i].contains(">"))
                            && !splitInput[i].equalsIgnoreCase(splitSkill[i])) {
                        outputSkill = null;
                        break;
                    }
                }
            }
        }
        return outputSkill;
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



