package DecisionTree;

import dataRecording.DataTuple;
import dataRecording.Dataset;

import java.util.List;

/**
 * Created by ramonserranolopez on 15/10/16.
 */
public abstract  class SelectorAtributos {

    public String seleccionDeAtributos(Dataset dataset, List<String> attrList) {
        return null;
    }

    public float entropy(DataTuple dataset, String attribute) {
        return 0;
    }

    public float infoA(DataTuple dataset, String attribute){
        return 0;
    }

    public float calculatePi(DataTuple dataset, String attribute, String value) {
        return 0;
    }

    float log2(float x)
    {
        return (float) (Math.log(x) / Math.log(2));
    }
}
