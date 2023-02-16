package logic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SlotEditor {

    private static Scanner s;
    private static final String fileName = "keywords.csv";

    public static void writeCSV() {
        StringBuilder s = new StringBuilder();
        s.append("Day,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday,\n");
        s.append("Month,January,February,March,April,May,June,July,August,September,October,November,December,\n");
        s.append("TimeH,");
        for (int i = 0; i < 24; i++) {
            s.append(i + ",");
        }
        s.append("\nTimeM,");
        for (int i = 0; i <= 60; i++) {
            s.append(i + ",");
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

    public static void addKeyword(String name, String items) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(name + "," + items + "\n");
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void editExistingKeyword(String name, String items) {
        items = ","+items;
        File oldFile = new File(fileName);
        File newFile = new File("temp.csv");

        try {
            FileWriter fw = new FileWriter("temp.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            s = new Scanner(new File("keywords.csv"));
            s.useDelimiter("[,\n]");

            String first = "";
            while (s.hasNextLine()){
                first = s.next();

                if (first.equalsIgnoreCase(name)){
                    pw.println(first + s.nextLine() + items);
                }else{
                    pw.println(first + s.nextLine());
                }

            }
            s.close(); pw.flush(); pw.close(); oldFile.delete();
            newFile.renameTo(new File("keywords.csv"));

        } catch (Exception e){
            e.getStackTrace();
        }
    }

    public static void main(String[] args) {
        writeCSV();
        addKeyword("test", "a,b,e,c,d,e,agg,g");
        addKeyword("test2", "1,2,4,5,6,7,8,9,0,09,8,7,6,5");
        editExistingKeyword("test2", "a,d,0");
    }
}
