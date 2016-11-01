package dataRecording;
import sun.applet.Main;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ramonserranolopez on 29/9/16.
 */
public class Dataset {
    public ArrayList<DataTuple> dataset;
    public HashMap<String, HashMap<String, Integer>> attributesWithValuesAndCounts;

    public Dataset(ArrayList<DataTuple> data) {
        dataset = data;
        attributesWithValuesAndCounts = new HashMap<>();
        fillAttributeHashMap();
    }


    // Crear un subdataset con aquellas tuplas que contegan el mismo valor para un atributo
    //Eg. Attribute: isPinkyEdible  Value: false.
    //Crea un dataset en el que isPinkyEdible es false.
    public Dataset getSubDataSetWithValue(String attribute, String value) {

        ArrayList<DataTuple> newDataset = new ArrayList<DataTuple>();

        for (DataTuple tuple : dataset) {
            if (tuple.discretize(attribute).equals(value)) {
                newDataset.add(tuple);
            }
        }
        return new Dataset(newDataset);
    }


    public void fillAttributeHashMap() {
        // Attributte, value, countTimes;
        for (DataTuple t: this.dataset) {
            for (String attr: t.getHash().keySet()){
                String value = t.getHash().get(attr);
                if (attributesWithValuesAndCounts.containsKey(attr)) {
                    if (attributesWithValuesAndCounts.get(attr).containsKey(value)) {
                        Integer count = attributesWithValuesAndCounts.get(attr).get(value);
                        count++;
                        attributesWithValuesAndCounts.get(attr).put(value, count);
                    } else {
                        attributesWithValuesAndCounts.get(attr).put(value, 1);
                    }
                } else {
                    attributesWithValuesAndCounts.put(attr, new HashMap<String, Integer>());
                    attributesWithValuesAndCounts.get(attr).put(value, 1);
                }
            }
        }
    }
}
