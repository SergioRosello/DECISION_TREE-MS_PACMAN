package DecisionTree;
import dataRecording.DataTuple;
import dataRecording.Dataset;
import pacman.game.Constants;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ramonserranolopez on 6/10/16.
 */
public class DecisionTree {

    public String classDecisionTree;

    public DecisionTree() {
        classDecisionTree = "strategy";
    }


    //Genera el arbol de decisiones según el dataset y la lista de atributos.
    public Node generateTree(Dataset trainingData, List<String> attrList){

        //Creamos un Nodo.
        Node node = new Node();

        //2. Si las tuplas en dataset tienen todas la misma clase C, return N como un nodo hoja etiquetado con la clase C.
        if(sameClass(trainingData)){
            Node nodoADevolver = new Node(trainingData.dataset.get(0).strategy.toString());
            return nodoADevolver;
        }
        //Si la lista de atributos está vacia, devuelve N etiquetado como un nodo de la clase mayoritaria D. (el valor de la clase que más se repita)
        else if(attrList.isEmpty()){
            HashMap<String, Integer> mapStrategy = trainingData.attributesWithValuesAndCounts.get(classDecisionTree);
            List<String> valuesForStraytegy = (List<String>) mapStrategy.keySet();
            String claseMayoritaria = "NONE";
            int max = 0;
            for(String value : valuesForStraytegy) {
                if(max < mapStrategy.get(value)) {
                    max = mapStrategy.get(value);
                    claseMayoritaria = value;
                }
            }
            Node nodoADevolver = new Node(claseMayoritaria);
            return nodoADevolver;
        }
        return null;
    }

    private boolean sameClass (Dataset D) {
        Constants.STRATEGY str = D.dataset.get(0).strategy;
        for (DataTuple tuple : D.dataset) {
            if (tuple.strategy != str) {
                return false;
            }
        }
        return true;
    }

    //Función que calcula el mayor beneficio.
    public String seleccionAtributos(){
        return null;
    }
}
