package DecisionTree;

import java.util.TreeMap;

/**
 * Created by Sergio Roselló Morell on 10/14/2016.
 */
public class Node {
    String clase;

    private TreeMap<String, Node> hijos;

    private boolean esHoja;

    //Constructor de la clase Node.
    public Node(String clase){
        this.clase = clase;
        this.hijos = new TreeMap<String, Node>();
        this.esHoja = true;
    }

    public void nuevoHijo(String clase, Node nodo){
        hijos.put(clase, nodo);
        if(this.esHoja) this.esHoja = false;
    }

    //Getters...
    public TreeMap<String, Node> getHijos(){
        return this.hijos;
    }

    public String getClase() {
        return this.clase;
    }

    //Checkers...

    public boolean esHoja() {
        return this.esHoja;
    }
}
