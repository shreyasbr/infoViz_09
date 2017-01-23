package com.company;

/**
 * Created by Anshuman on 18-01-2017.
 */
public class Edge {
    int from;
    int to;
    int weight;

    public Edge(){}

    public Edge(int from, int to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return from + "->" + to + ":" + weight;
    }
}
