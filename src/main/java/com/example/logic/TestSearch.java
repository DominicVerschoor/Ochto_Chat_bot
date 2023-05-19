package com.example.logic;

import java.io.StreamCorruptedException;
import java.util.*;
import java.util.regex.Pattern;

import javafx.collections.transformation.FilteredList;

public class TestSearch {

    public static String[] markedWords;

    public static void main(String[] args) {
        // Create a map to store the categories and their associated words
        Map<String, Set<String>> categories = new HashMap<>();
        categories.put("Fruit", new HashSet<>(Arrays.asList("apple", "banana", "orange")));
        categories.put("Color", new HashSet<>(Arrays.asList("red", "green", "blue")));
        categories.put("Time", new HashSet<>(Arrays.asList("16am", "4pm", "noon")));

        Set<String> dictionary = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : categories.entrySet()) {
            String category = entry.getKey();
            Set<String> categoryWords = entry.getValue();
            dictionary.addAll(categoryWords);
        }

        // Get user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("write sth: ");
        String userInput = scanner.nextLine();

        // Remove all symbols and punctuation from the user input
        userInput = userInput.replaceAll("[^\\p{L}\\p{N} ]+", "");

        // Split user input into words and normalize them
        String[] words = userInput.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = normalizeWord(words[i]);
        }

        markedWords = new String[words.length];
        for (int i = 0; i < words.length; i++){
            markedWords[i] = "NULL";
        }

        // Check which categories are present in the user's input
        String[] usedCategories = new String[words.length];
        Arrays.fill(usedCategories, "W");

        for (Map.Entry<String, Set<String>> currentCategory : categories.entrySet()) {
            String category = currentCategory.getKey();
            Set<String> categoryWords = currentCategory.getValue();

            for (String categoryWord : categoryWords) {
                int wordIndex = containsWord(words, categoryWord, dictionary);
                if (wordIndex != -1){
                    usedCategories[wordIndex] = category;
                }
            }
            
        }

        int replaceCounter = 0;
        for (int i = 0; i < words.length; i++){
            if (markedWords[i] == "NULL"){
                words[i-replaceCounter] = words[i];
                usedCategories[i-replaceCounter] = usedCategories[i];
            } else if (markedWords[i] == "WordExtention"){
                words[i-1-replaceCounter] = words[i-1] + words[i];
                replaceCounter++;
            }
        }
        int newlength = words.length-replaceCounter;
        String[] filteredWords = new String[newlength];
        String[] filteredCategories = new String[newlength];

        for (int i = 0; i < newlength; i++){
            filteredWords[i] = words[i];
            filteredCategories[i] = usedCategories[i];
        }

        for (String category : filteredCategories) {
            System.out.print(category + " ");
        }
        System.out.println();   
    }

    private static String normalizeWord(String word) {
        // Remove leading/trailing whitespace and convert to lowercase
        word = word.trim().toLowerCase();

        // Remove any leading zeros from numbers
        if (word.matches("\\d+")) {
            word = Integer.parseInt(word) + "";
        }

        return word;
    }

    private static int containsWord(String[] words, String target, Set<String> dictionary) {
        SpellChecker spellChecker = new SpellChecker(dictionary);

        // Loop through all words in input
        for (int currentId = 0; currentId < words.length; currentId++) {


            if (markedWords[currentId] != "Marked"){
                // Check whether current word is in the dictionary and spelled correctly
                if (spellChecker.isCorrectlySpelled(words[currentId])) {

                    if (words[currentId].equals(target)) {
                        return currentId;
                    } else {
                        StringBuilder extendedWord = new StringBuilder(words[currentId]);
                        for (int adjId = currentId + 1; adjId < words.length; adjId++) {
                            extendedWord.append(words[adjId]);
                            if (extendedWord.toString().equals(target)) {
                                return currentId;
                            }
                        }
                    }
                } else if (currentId != words.length-1 && spellChecker.isCorrectlySpelled(words[currentId] + words[currentId + 1])){
                    String wordsCombined = words[currentId] + words[currentId + 1];
                    if (wordsCombined.equals(target)) {
                        markedWords[currentId + 1] = "Marked";
                        return currentId;
                    } else {
                        StringBuilder extendedWord = new StringBuilder(wordsCombined);
                        for (int adjId = currentId + 1; adjId < words.length; adjId++) {
                            extendedWord.append(words[adjId]);
                            if (extendedWord.toString().equals(target)) {
                                markedWords[currentId + 1] = "Marked";
                                return currentId;
                            }
                        }
                    }
                } else {
                    List<String> suggestions = spellChecker.getSuggestions(words[currentId]);
                    if (!suggestions.isEmpty()) {
                        for (String suggestion : suggestions) {
                            if (suggestion.equals(target)) {
                                return currentId;
                            } else {
                                StringBuilder extendedWord = new StringBuilder(suggestion);
                                for (int adjId = currentId + 1; adjId < words.length; adjId++) {
                                    extendedWord.append(words[adjId]);
                                    if (extendedWord.toString().equals(target)) {
                                        return currentId;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }
}
