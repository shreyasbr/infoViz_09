package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        Parser parser = new Parser("migration_europe.bgraph");

        //1. Parse the migration_europe.bgraph file and load into memory
        BGraph bGraph = parser.Pasre();

        //2. Extract features. We will extract highest and lowest Emigration
        // for a given node for ach time step.
        HashMap<Integer,ArrayList<Feature>> featureMap=
                bGraph.extractFeatures();

    }
}
