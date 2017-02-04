package ptr.studies.java.webmining.comparedocs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import ptr.studies.java.webmining.utils.DocVectorPair;

public class DocSimilarityComparator {

    private LinkedHashMap<String, LinkedHashMap<String, Integer>> documents;
    private LinkedHashSet<String> dictionary;
    private LinkedHashMap<String, ArrayList<Double>> vectors;
    private LinkedHashSet<DocVectorPair> vectorPairs;
    private final int SIMILAR_NUM = 10;
    
    public DocSimilarityComparator(LinkedHashMap<String, LinkedHashMap<String, Integer>> docs) {
        this.documents = docs;
        this.dictionary = new LinkedHashSet<>();
        this.vectors = new LinkedHashMap<>();
        this.vectorPairs = new LinkedHashSet<>();
    }
    
    public void createDictionary() {
        for (String document : documents.keySet()) {
           for (String word : documents.get(document).keySet()) {
               if (!dictionary.contains(word)) {
                   dictionary.add(word);
               }
           }
        }
        
        System.out.println("DICTIONARY SIZE IN WORDS: " + dictionary.size());
    }
    
    public void createVectors() {
        System.out.println("NUMBER OF DOCUMENTS: " + documents.size());
        for (String document : documents.keySet()) {
            int count = 0;
            ArrayList<Double> docVector = new ArrayList<>();
            HashMap<String, Integer> docUniqueWords = documents.get(document);
            int docSizeInWords = docUniqueWords.size();
            Iterator<String> it = dictionary.iterator();
            System.out.println(document + " --> DOC SIZE IN WORDS: " + docSizeInWords);
            while (it.hasNext()) {
                String currentWord = it.next();
                if (docUniqueWords.containsKey(currentWord)) { // if uniqueWordsForDocument contains word from dictionary
                    int countInDocument = docUniqueWords.get(currentWord);
                    docVector.add((double)countInDocument/docSizeInWords);
                    count++;
                } else {
                    docVector.add(0.0);
                }
            }
            
            System.out.println(document + " --> ADDED: " + count + " non-zero vector components for doc. Should equal DOCSIZE: " + docSizeInWords);
            System.out.println(document + " --> VECTOR SIZE: " + docVector.size() + ", should equal DICTIONARY SIZE: " + dictionary.size());
            System.out.println("");
            vectors.put(document, docVector);
        }
    }
    
    public void makeVectorPairs() {
        int count = 0;
        Set<Entry<String, ArrayList<Double>>> entries = vectors.entrySet();
        ArrayList<Entry<String, ArrayList<Double>>> allVectorsList = new ArrayList<>(entries);
        for (int i=0; i < allVectorsList.size()-1; i++) {
            for (int j=i+1; j < allVectorsList.size(); j++) {
                String firstDoc = allVectorsList.get(i).getKey();
                ArrayList<Double> firstVect = allVectorsList.get(i).getValue();
                
                String secondDoc = allVectorsList.get(j).getKey();
                ArrayList<Double> secondVect = allVectorsList.get(j).getValue();

                DocVectorPair pair = new DocVectorPair(firstVect, secondVect, firstDoc, secondDoc);
                vectorPairs.add(pair);
                count++;
            }
        }
        System.out.println("VECTOR PAIRS CREATED: " + count);
    }
    
    public String printMostSimilar() {
        ArrayList<DocVectorPair> pairsList = new ArrayList<>(vectorPairs);
        Collections.sort(pairsList);
        Collections.reverse(pairsList);
        
        StringBuilder builder = new StringBuilder();
        builder.append("\n10 MOST SIMILAR DOCUMENTS:\n");
        for (int i=0; i<SIMILAR_NUM; i++) {
            builder.append(pairsList.get(i) + "\n");
        }
        return builder.toString();
    }
    
    public String printLessSimilar() {
        ArrayList<DocVectorPair> pairsList = new ArrayList<>(vectorPairs);
        Collections.sort(pairsList);
        
        StringBuilder builder = new StringBuilder();
        builder.append("\n10 LESS SIMILAR DOCUMENTS:\n");
        for (int i=0; i<SIMILAR_NUM; i++) {
            builder.append(pairsList.get(i) + "\n");
        }
        return builder.toString();
    }
}
