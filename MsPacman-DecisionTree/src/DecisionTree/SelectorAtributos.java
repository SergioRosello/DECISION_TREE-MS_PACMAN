package DecisionTree;

import dataRecording.Dataset;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by ramonserranolopez on 15/10/16.
 */
public abstract  class SelectorAtributos {

    public String seleccionDeAtributos(Dataset dataset, List<String> attributeList) {
        return null;
    }

    //Info(D)
    public float entropia(Dataset dataset, String attribute) {
        float infoD = 0;
        //Selecciona el hashmap que contiene el atributo
        // eg: selecciona los posibles valores para blinkyDist (very_low, medium...)
        //y el numero de veces que aparece dicho valor.
        HashMap<String, Integer> map =  dataset.attributesWithValuesAndCounts.get(attribute);
        //Saca el numero de claves (string. eg: very_low) que tiene map
        List<String> strategyValues = new ArrayList(map.keySet());

        for(String strategyVal : strategyValues) {
            //Calcula la probabilidad de cada valor.
            //Eg. probabilidad de que isInkyEdible es 4/10
            //Quiere decir que 4 de esas 10 veces es edible.
            float pi = calculoProbabilidad(dataset, attribute, strategyVal);
            if(pi>0) {
                //Calcula el resultado de cada valor
                float res = -pi * log2(pi);
                //Suma ese resultado a el global para calcular el Info(D) (tambiel llamado entropia)
                infoD += res;
            } else if(pi==(-1)) {
                return infoD;
            }
        }
        return infoD;
    }

    public float infoAD(Dataset dataset, String attribute){
        float info = 0;
        //Selecciona el hashmap que contiene el atributo
        // eg: selecciona los posibles valores para blinkyDist (very_low, medium...)
        //y el numero de veces que aparece dicho valor.
        HashMap<String, Integer> map =  dataset.attributesWithValuesAndCounts.get(attribute);
        //Saca el numero de claves (string. eg: very_low) que tiene map
        List<String> values = new ArrayList (map.keySet());
        for(String value : values){
            //Genera un dataset en el que el atributo es un solo valor.
            //Para poder calcular el tamaño de Dj ( |Dj| )
            Dataset dt = dataset.getSubDataSetWithValue(attribute, value);
            //Calcula los tamaños de ambos datasets.
            float sizeDataset = dataset.dataset.size();
            float subDataset = dt.dataset.size();
            //Va generando el sumatorio. Para cada value, le suma su resultado a info.
            info += (subDataset / sizeDataset) * entropia(dt, "strategy");
        }
        return info;
    }

    public float calculoProbabilidad(Dataset dataset, String attribute, String value) {

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
