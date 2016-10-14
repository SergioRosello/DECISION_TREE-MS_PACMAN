package DecisionTree;
import java.util.TreeMap;

/**
 * Created by ramonserranolopez on 15/10/16.
 */
public class SimpleNode {
    public static int count = 0;

    private String name;

    private String id;

    private TreeMap<String, SimpleNode> children;

    private boolean isRoot, isLeaf;

    public SimpleNode(String name) {
        this.name = name;
        this.id = Integer.toString(count);
        count++;
        this.isRoot = false;
        this.isLeaf = true;
        this.children = new TreeMap<String, SimpleNode>();
    }

    public SimpleNode(String name, boolean root) {
        this(name);
        this.isRoot = root;
    }

    public void addChild(String conn, SimpleNode child) {
        this.children.put(conn, child);
        if (this.isLeaf)
            this.isLeaf = false;
    }

    public TreeMap<String, SimpleNode> getChildren() {
        return this.children;
    }

    public String getName() {
        return name;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public String toString() {
        return "SimpleNode [name=" + name + "]";
    }

    public String getId() {
        return id;
    }
}
