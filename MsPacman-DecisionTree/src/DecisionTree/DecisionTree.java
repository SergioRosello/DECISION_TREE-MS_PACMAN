package DecisionTree;


import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import dataRecording.Dataset;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.STRATEGY;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

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
        listAttibutes = new ArrayList<String> (dataset.attributesWithValuesAndCounts.keySet());
        listAttibutes.remove(classDecisionTree);
    }

    public void buildTree() {
        root = generateTree(dataset, (ArrayList<String>) listAttibutes);
    }

    //Genera el arbol de decisiones según el dataset y la lista de atributos.
    public Node generateTree(Dataset dataset, ArrayList<String> attrList){

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
            // 4.1. Aplicar el método de selección de atributos sobre los datos y la lista de atributos, para
            // encontrar el mejor atributo actual A: S(D, lista de atributos) -> A.
            SelectorAtributos selector = new ID3();
            String mejorAtributo = selector.seleccionDeAtributos(dataset, attrList);

            // 4.2. Etiquetar a N como A y eliminar A de la lista de atributos.
            node = new Node(mejorAtributo);
            attrList.remove(mejorAtributo);

            // 4.3. Para cada valor aj del atributo A:
            //ArrayList<String> bestAttributeValues = this.attributesValues.get(mejorAtributo);
            HashMap<String, Integer> map =  dataset.attributesWithValuesAndCounts.get(mejorAtributo);
            List<String> bestAttributeValues = new ArrayList (map.keySet());
            for(String value: bestAttributeValues) {
                ArrayList<String> attributeListAux = (ArrayList) attrList.clone();;
                //  a) Separar todas las tuplas en D para las cuales el atributo A toma el valor aj, creando el subconjunto de datos Dj.
                Dataset subsetDj = dataset.getSubDataSetWithValue(mejorAtributo, value);
                //  b) Si Dj está vacío, añadir a N un nodo hoja etiquetado con la clase mayoritaria en D.
                if (subsetDj.dataset.isEmpty()) {
                    Node child = new Node(getMayorityClass(dataset));
                    node.nuevoHijo(value, child);
                }
                //  c) En otro caso, añadir a N el nodo hijo resultante de llamar a Generar_Arbol (Dj, lista de atributos).
                else {
                    node.nuevoHijo(value, generateTree(subsetDj, attributeListAux));
                }
            }
            // b) Si Dj está vacío, añadir a N un nodo hoja etiquetado con la clase mayoritaria en D. para valores no definidos en
            // el dataset de entrenamiento
            Node defaultNode = new Node(getMayorityClass(dataset));
            node.nuevoHijo("default", defaultNode);
            // 4.4 Return N.
            return node;
        }
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
        List<String> valuesForStraytegy = new ArrayList<>(mapStrategy.keySet());
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

    public STRATEGY buscarRecursivo(Node node, DataTuple game){
        STRATEGY strategy = null;
        if(node.esHoja())
            strategy = STRATEGY.valueOf(node.getClase());
        else {
            String valueNode = game.discretize(node.getClase());
            TreeMap tree = node.getHijos();
            Node nextNode = (Node) tree.get(valueNode);
            if(nextNode == null) {
                nextNode = (Node) tree.get("default");
            }
            strategy = buscarRecursivo(nextNode, game);
        }
        return strategy;
    }

    public STRATEGY buscar(Game game){
        DataTuple actualGame = new DataTuple(game, null);
        return buscarRecursivo(root, actualGame);
    }

    @Override
    public MOVE getMove(Game game, long timeDue) {
        switch (buscar(game)) {
            case EATPILLS:
                int pillsIndex[] = game.getPillIndices();
                int minDistP = Integer.MAX_VALUE;
                int pildoraAComer = 0;
                for (int pillIndex : pillsIndex) {
                    int distance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pillIndex);
                    if(distance < minDistP) {
                        minDistP = distance;
                        pildoraAComer = pillIndex;
                    }
                }
                return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), pildoraAComer, Constants.DM.PATH);
            case CHASE:
                int minDistGChase = Integer.MAX_VALUE;
                GHOST ghostAPerseguir = null;

                for (GHOST ghost : GHOST.values()) {
                    int distance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost));
                    if(distance < minDistGChase)  {
                        minDistGChase = distance;
                        ghostAPerseguir = ghost;
                    }
                }

                return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostAPerseguir), Constants.DM.PATH);
            case RUNAWAY:
                int minDistGRunAway = Integer.MAX_VALUE;
                GHOST ghostDelQueHuir = null;

                for (GHOST ghost : GHOST.values()) {
                    int distance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost));
                    if(distance < minDistGRunAway)  {
                        minDistGRunAway = distance;
                        ghostDelQueHuir = ghost;
                    }
                }
                return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostDelQueHuir), Constants.DM.PATH);
            default:
                return MOVE.NEUTRAL;
        }
    }
}
