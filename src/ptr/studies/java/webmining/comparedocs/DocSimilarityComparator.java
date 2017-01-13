package ptr.studies.java.webmining.comparedocs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

public class DocSimilarityComparator {

    private HashMap<String, HashMap<String, Integer>> documents;
    private LinkedHashSet<String> dictionary;
    private HashMap<String, ArrayList<Double>> vectors;
    
    public DocSimilarityComparator(HashMap<String, HashMap<String, Integer>> docs) {
        this.documents = docs;
        this.dictionary = new LinkedHashSet<>();
    }
    
    public void createDictionary() {
        for (String document : documents.keySet()) {
           for (String word : documents.get(document).keySet()) {
               if (!dictionary.contains(word)) {
                   dictionary.add(word);
               }
           }
        }
    }
    
    public void createVectors() {
        for (String document : documents.keySet()) {
            ArrayList vector = new ArrayList<>();
            for (String word : documents.get(document).keySet()) {
                //TODO: Create  vector for each document.
            }
            
        }
    }
}
