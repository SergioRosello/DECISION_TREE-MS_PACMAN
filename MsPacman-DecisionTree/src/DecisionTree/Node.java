package DecisionTree;

import java.util.TreeMap;

/**
 * Created by Sergio Roselló Morell on 10/14/2016.
 */
public class Node {
    //Que va a tener el nodo de Java?
    //Para poder implementar nuestro subDataSet.

    //Se podría decir que es el nombre de el nodo.
    String clase;

    //Creamos los hijos del nodo.
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
