package DecisionTree;


import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import dataRecording.Dataset;
import pacman.controllers.Controller;
import pacman.game.Constants.STRATEGY;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ramonserranolopez on 6/10/16.
 */
public class DecisionTree extends Controller<MOVE> {

    private Node root;
    private String classDecisionTree;
    private Dataset dataset;
    private List<String> listAttibutes;

    public DecisionTree() {

        classDecisionTree = "strategy";
        dataset = new Dataset(DataSaverLoader.LoadPacManData());
        listAttibutes = (List<String>) dataset.attributesWithValuesAndCounts.keySet();
        listAttibutes.remove(classDecisionTree);
    }

    @Override
    public MOVE getMove(Game game, long timeDue) {
        return null;
    }

    public void buildTree() {
        root = generateTree(dataset, listAttibutes);
    }


    //Genera el arbol de decisiones según el dataset y la lista de atributos.
    public Node generateTree(Dataset dataset, List<String> attrList){

        // 1. Se crea el nodo N.
        Node node = null;

        // 2. Si las tuplas en D tienen todas la misma clase C, return N como un nodo hoja etiquetado con la clase C.
        if(sameClass(dataset)){
            node = new Node(dataset.dataset.get(0).strategy.toString());
            return node;
        }
        // 3. En otro caso, si la lista de atributos está vacía, return N como un nodo hoja etiquetado con la clase mayoritaria en D.
        else if(attrList.isEmpty()){
            String claseMayoritaria = getMayorityClass(dataset);
            node = new Node(claseMayoritaria);
            return node;
        }
        // 4. En otro caso:
        else {
            // 1. Aplicar el método de selección de atributos sobre los datos y la lista de atributos, para
            // encontrar el mejor atributo actual A: S(D, lista de atributos) -> A.
            SelectorAtributos selector = new ID3();
            String mejorAtributo = selector.seleccionDeAtributos(dataset, attrList);

        }
        return null;
    }

    private boolean sameClass (Dataset D) {
        STRATEGY str = D.dataset.get(0).strategy;
        for (DataTuple tuple : D.dataset) {
            if (tuple.strategy != str) {
                return false;
            }
        }
        return true;
    }

    private String getMayorityClass(Dataset D) {
        HashMap<String, Integer> mapStrategy = D.attributesWithValuesAndCounts.get(classDecisionTree);
        List<String> valuesForStraytegy = (List<String>) mapStrategy.keySet();
        String claseMayoritaria = "NONE";
        int max = 0;
        for(String value : valuesForStraytegy) {
            if(max < mapStrategy.get(value)) {
                max = mapStrategy.get(value);
                claseMayoritaria = value;
            }
        }

        return claseMayoritaria;
    }
}
