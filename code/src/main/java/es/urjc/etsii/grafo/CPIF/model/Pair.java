package es.urjc.etsii.grafo.CPIF.model;

public class Pair implements Comparable<Pair>  {
    private int node,value;
    public Pair(int node, int value){
        this.node = node;
        this.value = value;
    }
    public Pair(Pair node){
        this.node = node.getNode();
        this.value = node.getValue();
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Pair o) {
        return -(getValue()-o.getValue());
    }
}
