package DecisionTree;

import dataRecording.Dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ramonserranolopez on 15/10/16.
 */
public abstract  class SelectorAtributos {

    public String seleccionDeAtributos(Dataset dataset, List<String> attrList) {
        return null;
    }

    public float entropy(Dataset dataset, String attribute) {
        float infoD = 0;
        //ArrayList<String> strategyValuesAux = Constants.STRATEGY.values().toString();
        //List<String> strategyValues = Arrays.asList(STRATEGY.values());
        //ArrayList<String> strategyValues = new ArrayList<String>();
        HashMap<String, Integer> map =  dataset.attributesWithValuesAndCounts.get(attribute);
        List<String> strategyValues = new ArrayList(map.keySet());
        for(String strategyVal : strategyValues) {
            float pi = calculatePi(dataset, attribute, strategyVal);
            if(pi>0) {
                float res = -pi * log2(pi);
                infoD += res;
            } else if(pi==(-1)) {
                return infoD;
            }
        }
        return infoD;
    }

    public float infoA(Dataset dataset, String attribute){
        float info = 0;
        HashMap<String, Integer> map =  dataset.attributesWithValuesAndCounts.get(attribute);
        List<String> values = new ArrayList (map.keySet());
        for(String value : values){
            Dataset dt = dataset.getSubDataSetWithValue(attribute, value);
            float sizeDataset = dataset.dataset.size();
            float subDataset = dt.dataset.size();
            info += (subDataset / sizeDataset) * entropy(dt, "DirectionChosen");
        }
        return info;
    }

    public float calculatePi(Dataset dataset, String attribute, String value) {

        HashMap<String, Integer> mapValuesAttr =  dataset.attributesWithValuesAndCounts.get(attribute);

        float sizeAttr = mapValuesAttr.get(value);
        float sizeDataset = dataset.dataset.size();

        return sizeAttr / sizeDataset;
    }

    float log2(float x)
    {
        return (float) (Math.log(x) / Math.log(2));
    }


}
