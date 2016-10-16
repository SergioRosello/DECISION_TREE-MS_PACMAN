package DecisionTree;

import dataRecording.Dataset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramonserranolopez on 15/10/16.
 */
public class ID3 extends SelectorAtributos {

    @Override
    public String seleccionDeAtributos(Dataset dataset, List<String> attrList) {
        String res = null;
        float infoD = entropy(dataset, "DirectionChosen");

        ArrayList<Float> infoA = new ArrayList<Float>();
        ArrayList<Float> gainA = new ArrayList<Float>();

        //calcular infoA
        for (String attr : attrList) {
            float auxInfoA = infoA(dataset, attr);
            infoA.add(auxInfoA);
        }

        //calcular gainA
        for (int i = 0; i < infoA.size(); i++) {
            float ganancia = infoD - infoA.get(i);
            gainA.add(ganancia);
        }

        //conseguir mas alto
        float max = 0;
        int posMax = 0;

        for (int i = 0; i < gainA.size(); i++) {
            if(gainA.get(i) > max) {
                max = gainA.get(i);
                posMax = i;
            }
        }

        res = attrList.get(posMax);
        return res;
    }
}
