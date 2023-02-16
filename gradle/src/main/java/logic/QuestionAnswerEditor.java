package logic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionAnswerEditor {
    private static Scanner s;
    private static final String fileName = "QuestionAnswer.csv";

    public static void writeCSV() {
        StringBuilder s = new StringBuilder();
        s.append("question|");

        for (int i = 0; i < 20; i++) {
            s.append("ans" + i + "|");
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(String.format(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert writer != null;

        writer.println(s);
        writer.close();
    }

//    public static void addAnswer(){
//        try {
//            FileWriter fw = new FileWriter(fileName, true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            PrintWriter pw = new PrintWriter(bw);
//
//            s = new Scanner(new File(fileName));
//            s.useDelimiter("[|\n]");
//
//            pw.write("ans"+increment+"|");
//
//            s.close(); pw.flush(); pw.close();
//        } catch (Exception e){
//            e.getStackTrace();
//        }
//
//        increment++;
//    }

    public static void addQuestion(String question, ArrayList<String> answers) {
        StringBuilder output = new StringBuilder();

        output.append(question + "|");
        for (String ans: answers) {
            output.append(ans + "|");
        }

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
        writeCSV();
        ArrayList<String> test = new ArrayList<>();
        test.add("Today is <!monday>");
        test.add("today is <!tuesday>");

        addQuestion("What <?day> is it", test);

        ArrayList<String> test2 = new ArrayList<>();
        test2.add("its <!13>");
        test2.add("it is <!14>");
        test2.add("its <!1> aint that cool");
        test2.add("woah a <!23>");

        addQuestion("What <?time> is it", test2);
    }
}
