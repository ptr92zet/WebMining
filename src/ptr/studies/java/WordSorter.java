package ptr.studies.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WordSorter {

    private int k;
    private int thresh;
    private ArrayList<String> words;
    private ArrayList<String> wordsCopy;
    private ArrayList<WordCountPair> wordsSortedNaively;
    private HashMap<String, Integer> wordsSorterOptimally;

    public WordSorter(ArrayList<String> words, int k, int thresh) {
        this.words = words;
        this.wordsCopy = new ArrayList<>(words);
        this.k = k;
        this.thresh = thresh;
        this.wordsSortedNaively = new ArrayList<>();
        this.wordsSorterOptimally = new HashMap<>();
    }

    public ArrayList<WordCountPair> sortNaive() {
        ArrayList<WordCountPair> results = new ArrayList<>();
        
        for (String word : words) {
            WordCountPair pair = new WordCountPair(word, 1);
            if (wordsSortedNaively.contains(pair)) {
                int index = wordsSortedNaively.indexOf(pair);
                int currentWordCount = wordsSortedNaively.get(index).getValue();
                wordsSortedNaively.get(index).setValue(currentWordCount + 1);
            } else {
                wordsSortedNaively.add(pair);
            }
        }
        Collections.sort(wordsSortedNaively);
        Collections.reverse(wordsSortedNaively);

        int i = 0;
        for (WordCountPair pair : wordsSortedNaively) {
            if (pair.getValue() >= thresh) {
                results.add(pair);
                i++;
                if (i == k) {
                    break;
                }
            }
        }
        System.out.println("Naive sort results (word -> count):");
        for (WordCountPair w : results) { 
            System.out.println(w);
        }
        return results;
    }

    public void sortOptimally() {
//        ArrayList<WordCountPair> results = new ArrayList<>();
        
        for (String word : words) {
            if (wordsSorterOptimally.get(word) == null) {
                wordsSorterOptimally.put(word, 1);
            } else {
                wordsSorterOptimally.put(word, wordsSorterOptimally.get(word) + 1);
            }
        }
        
        
        //TODO: Pick k words if it's value is greater than thres
        
//        for (int i=0; i<k; i++) {
//            if (Collections.max(wordsSorterOptimally.values()) >= thresh) {
//                
//            }
//        }
//        
//        List<Map.Entry<String, Integer>> list = new LinkedList<>(wordsSorterOptimally.entrySet());
//        Collections.max(wordsSorterOptimally.entrySet()):
//        wordsSorterOptimally.values();
//        int i = 0;
//        for (String word : wordsSorterOptimally.keySet()) {
//            if (wordsSorterOptimally.get(word) >= thresh) {
//                results.add(pair);
//                i++;
//                if (i == k) {
//                    break;
//                }
//            }
//        }
//        System.out.println("Naive sort results (word -> count):");
//        for (WordCountPair w : results) { 
//            System.out.println(w);
//        }
//        return results;
    }

    public void setWordList(ArrayList<String> list) {
        this.words = list;
    }
}
