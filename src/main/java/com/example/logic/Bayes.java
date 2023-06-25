package com.example.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


public class Bayes {
    ArrayList<String> prompts;
    ArrayList<String> actions;
    ArrayList<String> classes;
    ArrayList<Double> logPrior;
    ArrayList<ArrayList<Double>> logLikelihood;
    ArrayList<String> vocabulary;

    public Bayes(){
        Dataset data = new Dataset();
        prompts = data.getAllRules();
        actions = data.getAllActions();
        logPrior = new ArrayList<Double>();
        classes = new ArrayList<String>(new HashSet<>(actions));
        logLikelihood = new ArrayList<ArrayList<Double>>();
        HashSet<String> vocab = new HashSet<String>();
        for(int i = 0; i < prompts.size(); i++){
            vocab.addAll(Arrays.asList(prompts.get(i).split("\\s+")));
        }
        vocabulary = new ArrayList<String>(vocab);
        trainBayes();
    }

    public static void main(String[] args) {
        Bayes classifier = new Bayes();
        System.out.println(classifier.getPromptAnswer("Where is"));
    }

    private String getPromptAnswer(String prompt){
        ArrayList<Double> sum = new ArrayList<Double>();
        String[] words = prompt.split("\\s+");
        for(int i = 0; i < classes.size(); i++){
            double s = logPrior.get(i);
            for(String word : words){
                if(vocabulary.contains(word)){
                    s *= logLikelihood.get(i).get(vocabulary.indexOf(word));
                }
            }
            sum.add(s);
        }
        return classes.get(sum.indexOf(Collections.max(sum)));
    }

    private void trainBayes(){
        for(int i = 0; i < classes.size(); i++){
            double nDoc = prompts.size();
            int nC = Collections.frequency(actions, classes.get(i));
            logPrior.add((nC/nDoc));

            ArrayList<String> documents = new ArrayList<String>();
            for(int j = 0; j < actions.size(); j++){
                if(actions.get(j).equalsIgnoreCase(classes.get(i))){
                    documents.add(prompts.get(j));
                }
            }

            double countSum = 0;
            for(String word : vocabulary){
                countSum += countOccurences(word, documents);
            }
            ArrayList<Double> wLL = new ArrayList<Double>();
            for(String word : vocabulary){
                double count = countOccurences(word, documents);
                wLL.add(((count+1)/(countSum + vocabulary.size())));
            }
            logLikelihood.add(wLL);
        }

    }

    private double countOccurences(String word, ArrayList<String> strings){
        double count = 0;
        for(String words : strings){
            for(String w : words.split("\\s+")){
                if(w.equalsIgnoreCase(word)){
                    count += 1;
                }
            }
        }
        return count;
    }
}
