package ptr.studies.java.webmining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private LinkedHashMap<String, Integer> wordsSortedOptimally;

    public WordSorter(ArrayList<String> words, int k, int thresh) {
        this.words = words;
        this.wordsCopy = new ArrayList<>(words);
        this.k = k;
        this.thresh = thresh;
        this.wordsSortedNaively = new ArrayList<>();
        this.wordsSortedOptimally = new LinkedHashMap<>();
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
                if (i == this.k) {
                    break;
                }
            }
        }
        return results;
    }

    public ArrayList<WordCountPair> sortOptimally() {
        ArrayList<WordCountPair> results = new ArrayList<>();
        
        for (String word : wordsCopy) {
            if (wordsSortedOptimally.get(word) == null) {
                wordsSortedOptimally.put(word, 1);
            } else {
                wordsSortedOptimally.put(word, wordsSortedOptimally.get(word) + 1);
            }
        }
        
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordsSortedOptimally.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // comparing entry2 to entry1 to get descending order, entry1 to entry to would give ascending order
                return (entry2.getValue()).compareTo(entry1.getValue());
            }
        });

        for (int i=0; i<this.k; i++) {
            String word = entryList.get(i).getKey();
            Integer value = entryList.get(i).getValue();
            if (value >= thresh) {
                results.add(new WordCountPair(word, value));
            }
        }
        return results;
    }

    public void setWordList(ArrayList<String> list) {
        this.words = list;
    }
}
