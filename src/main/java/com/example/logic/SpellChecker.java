package com.example.logic;

import java.util.*;

public class SpellChecker {
    private Set<String> dictionary;
    private Map<Character, List<Character>> keyboardMap;
    private String userInput;
    private String terminalInput;

    public SpellChecker(Set<String> dictionary) {
        this.dictionary = dictionary;
        this.keyboardMap = new HashMap<>();
        createKeyboardMap();
    }

    public SpellChecker(){
        this.keyboardMap = new HashMap<>();
        createKeyboardMap();
    }

    public boolean checkSingleWord(String userInput, String terminalInput){
        if (userInput.length() < 3 && userInput.matches("[a-zA-Z]+")) {
            return false;
        }

        int distance = getLevenshteinDistance(userInput.toLowerCase(), terminalInput.toLowerCase());

        return distance <= 3;
    }

    public boolean isCorrectlySpelled(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    public List<String> getSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();

        // If its a single letter word dont check
        if (!(word.length() < 3 && word.matches("[a-zA-Z]+"))) {

            // Check Levenshtein distance
            int minDistance = Integer.MAX_VALUE;
            for (String dictWord : dictionary) {
                int distance = getLevenshteinDistance(word, dictWord);
                if (distance < minDistance) {
                    minDistance = distance;
                    suggestions.clear();
                    suggestions.add(dictWord);
                } else if (distance == minDistance) {
                    suggestions.add(dictWord);
                }
            }
            if (minDistance > 3){suggestions.clear();}
//            System.out.println(minDistance);
        }

        return suggestions;
    }

    private int getLevenshteinDistance(String word, String dictWord) {
        int wordLength = word.length();
        int dictLength = dictWord.length();
        int[][] comparisonMatrix = new int[wordLength + 1][dictLength + 1];

        // Initialize comparison matrix
        for (int i = 1; i <= wordLength; i++) {
            comparisonMatrix[i][0] = i;
        }
        for (int j = 1; j <= dictLength; j++) {
            comparisonMatrix[0][j] = j;
        }

        // Fill comparison matrix
        for (int i = 1; i <= wordLength; i++) {
            for (int j = 1; j <= dictLength; j++) {
                // If characters are equal cost = 0, if characters are same symbol
                // check if they are keyboard neighbors if yes cost = 1 if no cost = 2, if they are different forms cost = 3
                int cost;
                if (word.charAt(i - 1) == dictWord.charAt(j - 1)) {
                    cost = 0; // letters are equal
                } else if ((Character.isLetter(word.charAt(i - 1)) && Character.isLetter(dictWord.charAt(j - 1)))
                        || (Character.isDigit(word.charAt(i - 1)) && Character.isDigit(dictWord.charAt(j - 1)))) {
                    // check if they are keyboard neighbors
                    if (keyboardMap.get(dictWord.charAt(j - 1)).contains(word.charAt(i - 1))) {
                        cost = 1; // letters are neighbors
                    } else {
                        cost = 2; // letters are not neighbors
                    }
                } else {
                    cost = 3; // letters are different forms
                }
                // Takes the minimum cost of the left, diagonal left and above cells then add the cose
                comparisonMatrix[i][j] = Math.min(comparisonMatrix[i - 1][j] + 1, Math.min(comparisonMatrix[i][j - 1] + 1, comparisonMatrix[i - 1][j - 1] + cost));
            }
        }

        // Returns final cell
        return comparisonMatrix[wordLength][dictLength];
    }

    private void createKeyboardMap() {
        keyboardMap.put('1', List.of('2'));
        keyboardMap.put('2', Arrays.asList('1', '3'));
        keyboardMap.put('3', Arrays.asList('2', '4'));
        keyboardMap.put('4', Arrays.asList('3', '5'));
        keyboardMap.put('5', Arrays.asList('4', '6'));
        keyboardMap.put('6', Arrays.asList('5', '7'));
        keyboardMap.put('7', Arrays.asList('6', '8'));
        keyboardMap.put('8', Arrays.asList('7', '9'));
        keyboardMap.put('9', Arrays.asList('8', '0'));
        keyboardMap.put('0', List.of('9'));
        keyboardMap.put('q', Arrays.asList('w', 'a', 's'));
        keyboardMap.put('w', Arrays.asList('q', 'e', 'a', 's', 'd'));
        keyboardMap.put('e', Arrays.asList('w', 'r', 's', 'd', 'f'));
        keyboardMap.put('r', Arrays.asList('e', 't', 'd', 'f', 'g'));
        keyboardMap.put('t', Arrays.asList('r', 'y', 'f', 'g', 'h'));
        keyboardMap.put('y', Arrays.asList('t', 'u', 'g', 'h', 'j'));
        keyboardMap.put('u', Arrays.asList('y', 'i', 'h', 'j', 'k'));
        keyboardMap.put('i', Arrays.asList('u', 'o', 'j', 'k', 'l'));
        keyboardMap.put('o', Arrays.asList('i', 'p', 'k', 'l'));
        keyboardMap.put('p', Arrays.asList('o', 'l'));
        keyboardMap.put('a', Arrays.asList('q', 'w', 's', 'z', 'x'));
        keyboardMap.put('s', Arrays.asList('q', 'w', 'e', 'a', 'd', 'z', 'x', 'c'));
        keyboardMap.put('d', Arrays.asList('w', 'e', 'r', 's', 'f', 'x', 'c', 'v'));
        keyboardMap.put('f', Arrays.asList('e', 'r', 't', 'd', 'g', 'c', 'v', 'b'));
        keyboardMap.put('g', Arrays.asList('r', 't', 'y', 'f', 'h', 'v', 'b', 'n'));
        keyboardMap.put('h', Arrays.asList('t', 'y', 'u', 'g', 'j', 'b', 'n', 'm'));
        keyboardMap.put('j', Arrays.asList('y', 'u', 'i', 'h', 'k', 'n', 'm'));
        keyboardMap.put('k', Arrays.asList('u', 'i', 'o', 'j', 'l', 'm'));
        keyboardMap.put('l', Arrays.asList('i', 'o', 'p', 'k'));
        keyboardMap.put('z', Arrays.asList('a', 's', 'x'));
        keyboardMap.put('x', Arrays.asList('a', 's', 'd', 'z', 'c'));
        keyboardMap.put('c', Arrays.asList('s', 'd', 'f', 'x', 'v'));
        keyboardMap.put('v', Arrays.asList('d', 'f', 'g', 'c', 'b'));
        keyboardMap.put('b', Arrays.asList('f', 'g', 'h', 'v', 'n'));
        keyboardMap.put('n', Arrays.asList('g', 'h', 'j', 'b', 'm'));
        keyboardMap.put('m', Arrays.asList('h', 'j', 'k', 'n'));
    }
}