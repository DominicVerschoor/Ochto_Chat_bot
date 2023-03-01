package com.example.logic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionAnswerEditor {
    private static Scanner s;
    private static String fileName;
    private static ArrayList<Slot> slots;

    public QuestionAnswerEditor(String fileName, ArrayList<Slot> slots){
        this.fileName = "Questions/"+fileName;
        this.slots = slots;
    }

    public static void generateQuestion() {
        StringBuilder output = new StringBuilder();

        for (Slot slt: slots) {
            output.append(slt.getName()).append("|");
        }
        output.append("answer").append("\n");

        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.write(String.valueOf(output));

            pw.flush(); pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAnswer(ArrayList<String> slotResult, String answer) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < slots.size(); i++) {
            slots.get(i).addItems(slotResult.get(i));
        }

        for (String str: slotResult) {
            output.append(str).append("|");
        }
        output.append(answer);

        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.write(String.valueOf(output + "\n"));

            pw.flush(); pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editExistingQuestions(String name, String items) {
        //TODO
        items = "," + items;
        File oldFile = new File("keywords.csv");
        File newFile = new File("temp.csv");

        try {
            FileWriter fw = new FileWriter("temp.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            s = new Scanner(new File("keywords.csv"));
            s.useDelimiter("[,\n]");

            String first = "";
            while (s.hasNextLine()) {
                first = s.next();

                if (first.equalsIgnoreCase(name)) {
                    pw.println(first + s.nextLine() + items);
                } else {
                    pw.println(first + s.nextLine());
                }

            }
            s.close();pw.flush();pw.close();oldFile.delete();
            newFile.renameTo(new File("keywords.csv"));

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<Slot> testAns = new ArrayList<>();
        ArrayList<String> testPl = new ArrayList<>();

        testPl.add("pl1");
        testPl.add("pl2");
        testAns.add(new Slot("s1"));
        testAns.add(new Slot("s2"));

        QuestionAnswerEditor test1 = new QuestionAnswerEditor("Test1Qs [sl1] [sl1]", testAns);
        generateQuestion();
        addAnswer(testPl, "Test answer");
    }
}
