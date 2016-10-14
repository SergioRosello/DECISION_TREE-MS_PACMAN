package DecisionTree;
import dataRecording.DataTuple;
import dataRecording.Dataset;

import java.util.List;

/**
 * Created by ramonserranolopez on 6/10/16.
 */
public class DecisionTree {

    //Genera el arbol de decisiones según el dataset y la lista de atributos.
    public void generateTree(Dataset trainingData, List<String> attrList){

        //Creamos un Nodo.
        Node node = new Node();

        //2. Si las tuplas en dataset tienen todas la misma clase C, return N como un nodo hoja etiquetado con la clase C.


        /*
        *
        String className;
        if (sameClass(dataset)) {
            className = dataset.get(0).DirectionChosen.toString();
            n = new SimpleNode(className);
            return n;
        }
        * */
    }

    //Función que calcula el mayor beneficio.
    public String seleccionAtributos(){
        return null;
    }
}
