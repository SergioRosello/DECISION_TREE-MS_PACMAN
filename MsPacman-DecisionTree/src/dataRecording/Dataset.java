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
    private HashMap<String, HashMap<String, Integer>> attributesWithValuesAndCounts;

    public Dataset(ArrayList<DataTuple> data) {
        dataset = data;
        attributesWithValuesAndCounts = new HashMap<>();
        fillAttributeHashMap();
    }


    // Crear un subdataset con aquellas tuplas que contegan el mismo valor para un atributo
    public Dataset getSubDataSetWithValue(String attribute, String value) {

        ArrayList<DataTuple> newDataset = new ArrayList<DataTuple>();
        //subDataset.sadasd.remove(aatr);

        for (DataTuple tuple : dataset) {
            if (tuple.discretize(attribute).equals(value)) {
                newDataset.add(tuple);
            }
        }
        fillAttributeHashMap();
        Dataset subDataset = new Dataset(newDataset);

        return subDataset;
    }


    public void fillAttributeHashMap() {

        if (attributesWithValuesAndCounts.containsKey("Attribute")) {
            if (attributesWithValuesAndCounts.get("Attribute").containsKey("value")) {
                Integer value = attributesWithValuesAndCounts.get("Attribute").get("value");
                value++;
            } else {
                attributesWithValuesAndCounts.get("Attribute").put("value", 1);
            }
        } else {
            attributesWithValuesAndCounts.put("Attribute", new HashMap<String, Integer>());
            attributesWithValuesAndCounts.get("Attribute").put("value", 1);
        }
    }



    // Contar el numero de veces que aparece la tupla con el mismo valor

    //Dataset dataset = new Dataset(DataSaverLoader.LoadPacManData());
    // subDataset.getSubDataSetWithValue("asdas", "dadas");

    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        System.out.println("Hello, World");

        Dataset example = new Dataset(DataSaverLoader.LoadPacManData());
    }
}
