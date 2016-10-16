package DecisionTree;


import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import dataRecording.Dataset;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
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

        classDecisionTree = "DirectionChosen";
        dataset = new Dataset(DataSaverLoader.LoadPacManData());
        listAttibutes = new ArrayList<String> (dataset.attributesWithValuesAndCounts.keySet());
        listAttibutes.remove(classDecisionTree);
        System.out.printf("asdasd");
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
            String valorClase = dataset.dataset.get(0).DirectionChosen.toString();
            node = new Node(valorClase);
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
            // 4.4 Return N.
            return node;
        }
    }

    private boolean sameClass (Dataset D) {
        MOVE str = D.dataset.get(0).DirectionChosen;
        for (DataTuple tuple : D.dataset) {
            if (tuple.DirectionChosen != str) {
                return false;
            }
        }
        System.out.println("YO");
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

    public MOVE buscarRecursivo(Node node, DataTuple game){
        MOVE move = null;
        if(node.esHoja())
            move = MOVE.valueOf(node.getClase());
        else {
            String valueNode = game.discretize(node.getClase());
            TreeMap tree = node.getHijos();
            Node nextNode = (Node) tree.get(valueNode);
            move = buscarRecursivo(nextNode, game);
        }
        return move;
    }

    public MOVE buscar(Game game){
        DataTuple actualGame = new DataTuple(game, null);
        return buscarRecursivo(root, actualGame);
    }

    @Override
    public MOVE getMove(Game game, long timeDue) {
        return buscar(game);
    }
}
