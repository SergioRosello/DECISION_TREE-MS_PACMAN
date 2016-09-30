package dataRecording;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ramonserranolopez on 29/9/16.
 */
public class Dataset {
    public ArrayList<DataTuple> dataset;

    public Dataset() {
        dataset = new ArrayList<DataTuple>(Arrays.asList(DataSaverLoader.LoadPacManData()));
    }

    // Crear un subdataset del dataset con aquellas tuplas que contegan el mismo valor para un atributo
    public ArrayList<DataTuple> getSubDataSetWithValue(String attribute, String value) {

        ArrayList<DataTuple> subDataset = new ArrayList<DataTuple>();

        for (DataTuple tuple : dataset) {
            if (tuple.discretize(attribute).equals(value)) {
                subDataset.add(tuple);
            }
        }

        return subDataset;
    }

    // Contar el numero de veces que aparece la tupla con el mismo valor
    public int appearsXTimes(ArrayList<DataTuple> data){
        int numberOfTimes = 0;
        for(DataTuple tuple : data){
            numberOfTimes++;
        }
        return numberOfTimes;
    }
}
