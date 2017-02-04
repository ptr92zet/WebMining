package ptr.studies.java.webmining.utils;

public class WordCountPair implements Comparable<WordCountPair>{
    private final String key;
    private int value;
    
    public WordCountPair(String key, int value) {
        this.key = key;
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }
    
    public int getValue() {
        return value;
    }

    public int setValue(int val) {
        int oldValue = this.value;
        this.value = val;
        return oldValue;
    }

    @Override
    public String toString() {
        return key + " -> " + value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WordCountPair other = (WordCountPair) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

    @Override
    public int compareTo(WordCountPair o) {
        int compareValue = 0;
        if (this.value < o.getValue()) {
            compareValue = -1;
        }
        if (this.value == o.getValue()) {
            compareValue = 0;
        }
        if (this.value > o.getValue()){
            compareValue = 1;
        }
        return compareValue;
    }
}