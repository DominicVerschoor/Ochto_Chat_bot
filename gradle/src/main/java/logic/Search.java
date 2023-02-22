package logic;

import java.io.*;
import java.util.*;

public class Search {
    private static Scanner s;

    public static String returnAnswer(String fileName, ArrayList<String> slotResults) {

        for (int i = 0; i < slotResults.size(); i++) {
            slotResults.set(i, slotResults.get(i).replace("[",""));
            slotResults.set(i, slotResults.get(i).replace("]",""));
        }

        try {
            FileReader fr = new FileReader("Questions/" + fileName);
            BufferedReader br = new BufferedReader(fr);

            s = new Scanner(new File("Questions/" + fileName));
            s.useDelimiter("[|\n]");

            String firstLine = br.readLine();
            int slotNum = firstLine.split("[|]", 0).length - 1;

            while (s.hasNextLine()) {
                s.nextLine();
                String[] slotCheck = new String[slotNum];
                for (int i = 0; i < slotNum; i++) {
                    slotCheck[i] = s.next();
                }

                boolean equals = true;
                for (int i = 0; i < slotNum; i++) {
                    if (!Objects.equals(slotCheck[i], slotResults.get(i))) {
                        equals = false;
                        break;
                    }
                }
                if (equals) {
                    return s.next();
                }
            }
            s.close();

        } catch (Exception e) {
            e.getStackTrace();
        }

        return "I dunno :P";
    }

    public static ArrayList<String> findCorrectCSV(String chat, String questionFiles) {
        String[] chatWords = chat.split(" ", 0);
        String[] qsWords = questionFiles.split(" ", 0);
        ArrayList<String> slots = new ArrayList<>();


        if (chatWords.length != qsWords.length) {
            return null;
        } else {
            for (int i = 0; i < chatWords.length; i++) {
                if ((qsWords[i].contains("[") && qsWords[i].contains("]"))) {
                    slots.add(chatWords[i]);
                }

                if (!(qsWords[i].contains("[") && qsWords[i].contains("]"))
                        && !Objects.equals(chatWords[i], qsWords[i])) {
                    return null;
                }
            }
        }

        return slots;
    }

    public static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> output = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                output.add(fileEntry.getName());
            }
        }
        return output;
    }

    public static void main(String[] args) {
        final File folder = new File("Questions/");
        ArrayList<String> filesNames = listFilesForFolder(folder);
        ArrayList<String> slotResults = new ArrayList<>();
        String finalFilename = "";

        for (String files : filesNames) {
            slotResults = findCorrectCSV("Test1Qs [p3] [p4]", files);

            if (slotResults != null) {
                finalFilename = files;
                break;
            }
        }

        System.out.println(returnAnswer(finalFilename, slotResults));
    }
}
