package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Parser parser = new Parser("migration_europe.bgraph");

        //1. Parse the migration_europe.bgraph file and load into memory
        BGraph bGraph = parser.Pasre();

        for (Node node : bGraph.nodeList) {
            System.out.println(node.nodeId + "->" + node.getCoordinate());
        }

        //2. Extract features. We will extract highest and lowest Emigration
        // for a given node for ach time step.
        HashMap<Integer, ArrayList<Feature>> featureMap =
                bGraph.extractFeatures();

        for (int index : featureMap.keySet()) {
            System.out.println("index = " + index);
            for (Feature feature : featureMap.get(index)) {
                System.out.println("feature.high = " + feature.high);
                System.out.println("feature.low = " + feature.low);
            }
        }

        HTMLGenerator htmlGenerator = new HTMLGenerator(bGraph);
        String htmlString = htmlGenerator.getHtmlString();

        // write out htmlString as HTML file
        try(PrintWriter out = new PrintWriter("openthis.html")) {
            out.print(htmlString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
