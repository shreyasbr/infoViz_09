package com.company;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anshuman on 18-01-2017.
 */
public class BGraph {
    ArrayList<Node> nodeList;
    ArrayList<ArrayList<Edge>> timeSteps;
    Array2DRowRealMatrix theBigMatrix;
    public BGraph()
    {
        nodeList = new ArrayList<>();
        timeSteps = new ArrayList<>();
        theBigMatrix = null;
    }

    public void addNode(Node node)
    {
        nodeList.add(node);
    }

    public Array2DRowRealMatrix toRealMatrix(){
        int TOTAL_ROWS = this.nodeList.size();
        int TOTAL_COLS = this.nodeList.size();
        theBigMatrix
                = new Array2DRowRealMatrix(TOTAL_ROWS, TOTAL_ROWS);

        //Initialize the Matrix
        for(int i = 0; i < TOTAL_ROWS; i++){
            for(int j = 0; j < TOTAL_COLS; j++){
                theBigMatrix.setEntry(i,j,0);
            }
        }

        //a.
        //Now set the edge weights in the matrix
        for (Edge e:this.timeSteps.get(0)
                ) {
            theBigMatrix.setEntry(e.from,e.to,e.weight);
        }

        return theBigMatrix;
    }

    public HashMap<Integer,ArrayList<Feature>> extractFeatures() {
        HashMap<Integer,ArrayList<Feature>> featureMap = new HashMap<>();
        int highest = 0;
        int lowest = 0;
        Edge highE = null, lowE = null;
        int size = 0;
        int curNode = 0;

        //Populate the Hashmap
        for(int i = 0; i < nodeList.size(); i++){
            featureMap.put(i,new ArrayList<>());
        }

        //Iterate through each timestep and create an entry of feature for each node
        for (ArrayList<Edge> timestep:timeSteps
             ) {
            //Iterate through the edge list in each time step
            //and find the highest and lowest emigration for
            //for each node.
            size = timestep.size();
            curNode = timestep.get(0).from;
            highest = timestep.get(0).weight;
            lowest = highest;
            ArrayList<Feature> features = null;
            for(int i = 1; i < size; i++)
            {
                if(timestep.get(i).from == curNode){
                    if(timestep.get(i).weight > highest) {
                        highest = timestep.get(i).weight;
                        highE = new Edge(curNode,timestep.get(i).to,highest);
                    }
                    else if(timestep.get(i).weight < lowest) {
                        lowest = timestep.get(i).weight;
                        lowE = new Edge(curNode,timestep.get(i).to,lowest);
                    }
                }
                else
                {
                    features = featureMap.getOrDefault(curNode,null);
                    if(features!=null)
                        features.add(new Feature(highE,lowE));
                    featureMap.put(curNode,features);

                    curNode = timestep.get(i).from;;
                    highest = timestep.get(i).weight;
                    lowest = highest;
                    highE = null;
                    lowE = null;
                }
            }

            //For the last node
            features = featureMap.getOrDefault(curNode,null);
            if(features!=null)
                features.add(new Feature(highE,lowE));
            featureMap.put(curNode,features);
        }


        return featureMap;
    }
}
