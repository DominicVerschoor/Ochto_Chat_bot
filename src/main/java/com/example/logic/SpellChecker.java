package com.example.logic;

import java.util.*;

public class SpellChecker {
    private Set<String> dictionary;
    private Map<Character, List<Character>> keyboardMap;
    private String userInput;
    private final int distanceThreshold = 4;
    private final int minWordLength = 4;

    public SpellChecker(String userInput) {
        this.userInput = userInput;
        this.dictionary = new HashSet<>();
        this.keyboardMap = new HashMap<>();
        createKeyboardMap();
    }

    public boolean checkSingleWord(String userInput, String terminalInput) {
        userInput = cleanWord(userInput);
        terminalInput = cleanWord(terminalInput);

        if (userInput.length() < 4) {
            return userInput.equalsIgnoreCase(terminalInput);
        }

        int distance = getLevenshteinDistance(userInput.toLowerCase(), terminalInput.toLowerCase());

        return distance < distanceThreshold;
    }

    public boolean isNotCorrectlySpelled(String word) {
        return !dictionary.contains(word.toLowerCase());
    }

    public List<String> getSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();

        // If its a 3 or less letter word or contains numbers dont check
        if (!(word.length() < minWordLength && word.matches("[a-zA-Z]+"))) {

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
            if (minDistance > distanceThreshold) {
                suggestions.clear();
            }
//            System.out.println(minDistance);
        }

        return suggestions;
    }

    public ArrayList<String> correctedPrompts() {
        ArrayList<String> output = new ArrayList<>();
        String[] splitInput = userInput.split(" ");

        processPrompts(splitInput, output);
        output = removeIncorrectPrompt(output);

        if (output.size() == 0){
            output.add(userInput);
        }

        return output;
    }

    private void processPrompts(String[] prompt, ArrayList<String> output) {
        for (int currentWord = 0; currentWord < prompt.length; currentWord++) {
            String word = prompt[currentWord];
            if (isNotCorrectlySpelled(word)) {
                List<String> suggestions = getSuggestions(word);
                for (String sug : suggestions) {
                    String[] tempOutput = Arrays.copyOf(prompt, prompt.length);
                    tempOutput[currentWord] = sug;
                    output.add(String.join(" ", tempOutput));
                    processPrompts(tempOutput, output); // Recursively process the updated prompt
                }
            }
        }
    }

    private ArrayList<String> removeIncorrectPrompt(ArrayList<String> prompt){
        for (int i = 0; i < prompt.size(); i++) {
            String[] splitPrompt = prompt.get(i).split(" ");
            for (String word:splitPrompt) {
                if (isNotCorrectlySpelled(word) && word.length() >= minWordLength){
                    prompt.remove(i);
                    i--;
                    break;
                }
            }
        }

        ArrayList<String> duplicatesRemoved = new ArrayList<>();
        HashSet<String> set = new HashSet<>();

        for (String element : prompt) {
            if (set.add(element)) {
                duplicatesRemoved.add(element);
            }
        }

        return duplicatesRemoved;
    }

    private int getLevenshteinDistance(String word, String dictWord) {
        word = word.toLowerCase();
        dictWord = dictWord.toLowerCase();
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
                // Takes the minimum cost of the left, diagonal left and above cells then add the cost
                comparisonMatrix[i][j] = Math.min(comparisonMatrix[i - 1][j] + cost, Math.min(comparisonMatrix[i][j - 1] + cost, comparisonMatrix[i - 1][j - 1] + cost));
            }
        }

        // Returns final cell
        return comparisonMatrix[wordLength][dictLength];
    }

    private void createKeyboardMap() {
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

    public String cleanWord(String input) {
        return input.replaceAll("[^\\p{L}\\p{N}]+", "");
    }

    public void generateDictionary(ArrayList<HashMap<String, ArrayList<String>>> rules) {
        for (HashMap<String, ArrayList<String>> rule : rules) {
            for (Map.Entry<String, ArrayList<String>> entry : rule.entrySet()) {
                ArrayList<String> rhs = entry.getValue();
                for (String s : rhs) {
                    if (!s.contains("<") && !s.contains(">") && s.matches("[a-zA-Z]+") && s.length() >= minWordLength) {
                        dictionary.add(s.toLowerCase());
                    }
                }
            }
        }
    }

    private void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker("Which lectures are there on monday");
        System.out.println(checker.getLevenshteinDistance("Which lectures are there on monday", "Which lectures are there on <day> at <time>"));
        System.out.println(checker.getLevenshteinDistance("Which lectures are there on monday", "Which lectures are there at <time> on <day>"));
        System.out.println(checker.getLevenshteinDistance("Which lectures are there on monday", "at <day> on <time> which lectures do i have"));
        System.out.println(checker.getLevenshteinDistance("Which lectures are there on monday", "on <day> at <DAY> which lectures do i have"));


//        Set<String> rules = new HashSet<>();
//        rules.add("which");
//        rules.add("lectures");
//        rules.add("have");
//        rules.add("monday");
//        rules.add("sonday");
//
//        SpellChecker test = new SpellChecker("Whic leures do I have on Moday at 9");
//        test.setDictionary(rules);
//
//        ArrayList<String> outputs = test.correctedPrompts();
//
//        for (String s : outputs) {
//            System.out.println(s);
//        }


//        String[] correct = "There are many people who enjoy listening to music and playing sports such as football, basketball, and tennis. They like to go outdoors, explore nature, and go on adventures. They also enjoy socializing with friends and family, and having fun in their free time.".split(" ");
//        String[] misspelled = "Ther are any peple who njoy liseng to musik and payng sportes succh as fotball, bascketball, and tenise. They lik to go outors, explor natuer, and go on adventurs. Tey aso enjo socializin with frends and famly, and hain funm in their fre time.".split(" ");
//        String[] variant = "There are lots humans who like singing to songs while enjoying games such as basketball, football, plus golf. They enjoy to go outside, explain things, and go on trips. Those also enjoy talking with family and friends, and receiving enjoyment in our off days.".split(" ");
//
//
//        SpellChecker checker = new SpellChecker();
//
//        System.out.println(checker.getLevenshteinDistance("there", "which"));
//        System.out.println(checker.getLevenshteinDistance("there", "lectures"));
//        System.out.println(checker.getLevenshteinDistance("there", "are"));
//        System.out.println(checker.getLevenshteinDistance("there", "there"));
//        System.out.println(checker.getLevenshteinDistance("there", "on"));
//        System.out.println(checker.getLevenshteinDistance("there", "mnday"));
//        System.out.println(checker.getLevenshteinDistance("there", "at"));
//        System.out.println(checker.getLevenshteinDistance("there", "9"));
//
//        int cost1 = 26;
//        int cost2 = 0;
//        for (int i = 0; i < correct.length; i++) {
//
//            if (checker.checkSingleWord(misspelled[i], correct[i])){
//                if (!(misspelled[i].length() < 3) && !misspelled[i].equalsIgnoreCase(correct[i])){
//                    misspelled[i] = correct[i];
//                    cost1--;
//                }
//            }
//
//            if (checker.checkSingleWord(variant[i], correct[i])){
//                if (!(variant[i].length() < 3) && !variant[i].equalsIgnoreCase(correct[i])){
//                    variant[i] = correct[i];
//                    cost2++;
//                }
//            }
//        }
//
//        System.out.println(cost1);
//        System.out.println(cost2);
    }
}