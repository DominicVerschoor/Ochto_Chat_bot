package com.example.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private String action;
    private ArrayList<String> rules;
    private ArrayList<String> responses;
    private File file;
    private List<String> everything;
    public CSVReader(File file) {
        this.file = file;
        rules = new ArrayList<>();
        responses = new ArrayList<>();
        read();
    }

    public void read(){
        everything = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            everything.add(br.readLine()); // Skip first line
            action = br.readLine().substring(4); // Action is always second line
            everything.add(action);

            String currentLine;
            while ((currentLine = br.readLine()) != null){

                if (currentLine.startsWith("Rule")){
                    rules.add(currentLine.substring(4));
                    everything.add(currentLine);

                } else if (currentLine.startsWith("Action")) {
                    responses.add(currentLine.substring(6));
                    everything.add(currentLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAction() {
        return action;
    }

    public ArrayList<String> getRules() {
        return rules;
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public String[] getEverything(){
        return everything.toArray(new String[0]);
    }
}
