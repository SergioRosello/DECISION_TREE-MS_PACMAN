package DecisionTree;

import dataRecording.Dataset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramonserranolopez on 15/10/16.
 */
public class ID3 extends SelectorAtributos {

    @Override
    public String seleccionDeAtributos(Dataset dataset, List<String> attributeList) {
        String res = null;
        float infoD = entropia(dataset, "strategy");

        ArrayList<Float> gainA = new ArrayList<Float>();

        //Para cada atributo de attributeList
        for (String attr : attributeList) {
            float infoADAux = infoAD(dataset, attr);    //Calcula el infoa(D) para poder calcular la ganancia.
            //calcula la ganancia de la información
            float ganancia = infoD - infoADAux;
            gainA.add(ganancia);
        }

        float max = 0;
        int posMax = 0;

        //Averiugua que atributo tiene la mayor ganancia de información de la lista.
        for (int i = 0; i < gainA.size(); i++) {
            if(gainA.get(i) > max) {
                max = gainA.get(i);
                posMax = i;
            }
        }

        res = attributeList.get(posMax);
        return res;
    }
}
