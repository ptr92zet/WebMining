package ptr.studies.java.webmining.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DocVectorPair implements Comparable<DocVectorPair>{

    private ArrayList<Double> firstVector;
    private ArrayList<Double> secondVector;
    private String firstDoc;
    private String secondDoc;
    private int size;
    
    private double firstVectMagnitude;
    private double secondVectMagnitude;
    private double scalarProduct;
    private double cosinus;
    
    public DocVectorPair(ArrayList<Double> firstVect, ArrayList<Double> secondVect, String firstDoc, String secondDoc) {
        this.firstVector = firstVect;
        this.secondVector = secondVect;
        this.firstDoc = firstDoc.substring(firstDoc.lastIndexOf("/")+1);
        this.secondDoc = secondDoc.substring(secondDoc.lastIndexOf("/")+1);
        this.size = firstVector.size();
        countCosinus();
    }
    
    public Double getCosinus() {
        return this.cosinus;
    }
    
    public String getFirstDoc() {
        return this.firstDoc;
    }
    
    public String getSecondDoc() {
        return this.secondDoc;
    }
    
    private void countCosinus() {
        countFirstMagnitude();
        countSecondMagnitude();
        countScalarProduct();
        this.cosinus = scalarProduct/(firstVectMagnitude*secondVectMagnitude);
    }
    
    private void countFirstMagnitude() {
        double sum = 0.0;
        for (int i=0; i<size; i++) {
            sum += (firstVector.get(i) * firstVector.get(i));
        }
        this.firstVectMagnitude = Math.sqrt(sum);
    }
    
    private void countSecondMagnitude() {
        double sum = 0.0;
        for (int i=0; i<size; i++) {
            sum += (secondVector.get(i) * secondVector.get(i));
        }
        this.secondVectMagnitude = Math.sqrt(sum);
    }
    
    private void countScalarProduct() {
        double sum = 0.0;
        for (int i=0; i<size; i++) {
            sum += (firstVector.get(i) * secondVector.get(i));
        }
        this.scalarProduct = sum;
    }

    @Override
    public String toString() {
        int spacesAfterFirstDoc = 30 - firstDoc.length();
        int spacesAfterSecondDoc = 30 - secondDoc.length();
        String spacesFirst = "";
        String spacesSecond = "";
        for (int i=0; i<spacesAfterFirstDoc; i++) {
            spacesFirst += " ";
        }
        for (int i=0; i<spacesAfterSecondDoc; i++) {
            spacesSecond += " ";
        }
        return "[" + firstDoc + spacesFirst + " | " + secondDoc + spacesSecond + "] --> " + this.cosinus;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(cosinus);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        DocVectorPair other = (DocVectorPair) obj;
        if (Double.doubleToLongBits(cosinus) != Double.doubleToLongBits(other.cosinus))
            return false;
        return true;
    }

    @Override
    public int compareTo(DocVectorPair other) {
        int compareResult = BigDecimal.valueOf(this.cosinus).compareTo(BigDecimal.valueOf(other.cosinus));
        return compareResult;
    }
    
}
