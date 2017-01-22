package com.company;

/**
 * Created by Anshuman on 18-01-2017.
 */
public class Node {
    int nodeId;
    int level;
    String label;
    String color;
    private Coordinate coordinate;

    public Node(){}

    public Node(int nodeId,String label, int level){
        this.nodeId = nodeId;
        this.label = label;
        this.level  = level;
        color   = null;
        setCoordinate(null);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
