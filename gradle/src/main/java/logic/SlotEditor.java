package logic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SlotEditor {

    private static Scanner s;
    private static final String fileName = "slots.csv";
    private static Slot day = new Slot("day");
    private static Slot month = new Slot("month");
    private static Slot time = new Slot("time");

    public static void writeCSV() {
        StringBuilder output = new StringBuilder();

        day.addItems("Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday");
        month.addItems("January,February,March,April,May,June,July,August,September,October,November,December");
        time.addItems("0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23");

        output.append(day.toString()).append("\n");
        output.append(month.toString()).append("\n");
        output.append(time.toString()).append("\n");

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

    public static void addKeyword(Slot slot) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(slot.toString() + "\n");
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void editExistingKeyword(Slot slot, String newItems) {
        File oldFile = new File(fileName);
        File newFile = new File("temp.csv");

        try {
            FileWriter fw = new FileWriter("temp.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            s = new Scanner(new File(fileName));
            s.useDelimiter("[,\n]");
            String first = "";
            while (s.hasNextLine()) {
                first = s.next();

                if (first.equalsIgnoreCase(slot.getName())) {
                    slot.addItems(newItems);
                    pw.println(slot.toString());
                    s.nextLine();
                } else {
                    pw.println(first + s.nextLine());
                }

            }
            s.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            newFile.renameTo(new File(fileName));

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) {
        writeCSV();
        Slot test1 = new Slot("AddTest1");
        test1.addItems("a,b,c,d,e,f,g,h");
        addKeyword(test1);
        editExistingKeyword(test1, "1,2,3,4,a");
    }
}
