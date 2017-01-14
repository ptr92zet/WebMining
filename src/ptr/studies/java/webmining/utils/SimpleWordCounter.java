package ptr.studies.java.webmining.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SimpleWordCounter {

    public ArrayList<String> words;
    
    public SimpleWordCounter(ArrayList<String> wordList) {
        this.words = wordList;
    }
    
    public LinkedHashMap<String, Integer> count() {
        LinkedHashMap<String, Integer> wordsCounted = new LinkedHashMap<>();
        for (String word : words) {
            if (wordsCounted.get(word) == null) {
                wordsCounted.put(word, 1);
            } else {
                wordsCounted.put(word, wordsCounted.get(word) + 1);
            }
        }
        return wordsCounted;
    }
}
