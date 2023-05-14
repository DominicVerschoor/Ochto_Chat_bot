package com.example.logic;

import java.util.*;
import java.util.regex.Pattern;

public class TestSearch {

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

        // Print the categories that were used in the user's input
        for (String usedCategory : usedCategories) {
            System.out.print(usedCategory + " ");
        }
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

        for (int currentId = 0; currentId < words.length; currentId++) {

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

        return -1;
    }
}
