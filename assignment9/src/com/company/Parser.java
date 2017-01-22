package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Anshuman on 18-01-2017.
 */
public class Parser {
    String fileName;
    private static int section;
    BGraph bGraph;

    public Parser(String fileName)
    {
        this.fileName = fileName;
        section = 1;
        bGraph = new BGraph();
    }

    public BGraph Pasre()
    {
        //Step-1: Parse all the nodes
        try (Scanner scanner = new Scanner(new File(fileName))) {

            String line;
            int nodeId = 0;
            String[] splitLine = null;
            Node node = null;
            ArrayList<Edge> edges = new ArrayList<>();

            while (scanner.hasNext()){
                //System.out.println(scanner.nextLine());
                //This means we hare parsing the nodes.
                while(section == 1)
                {
                    line = scanner.nextLine();
                    if (line.trim().isEmpty()){
                        section++;
                    }
                    else {
                        splitLine = line.split("/");
                        node = new Node(nodeId++,splitLine[splitLine.length - 1],splitLine.length-1);
                        bGraph.addNode(node);

                        splitLine = null;
                    }
                }

                //Parse edgeList
                line = scanner.nextLine();
                if(!line.trim().isEmpty())
                {
                    splitLine = line.split(" ");
                    edges.add(
                            new Edge(
                                    Integer.parseInt(splitLine[0]),
                                    Integer.parseInt(splitLine[1]),
                                    Integer.parseInt(splitLine[2])));
                }
                else {
                    bGraph.timeSteps.add(edges);
                    edges = new ArrayList<>();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Parse the soordinates and populate for each node in the bGraph
        try (Scanner scanner = new Scanner(new File("coordinates.txt"))) {

            String line;
            String[] splitLine = null;
            int node = 0;

            while (scanner.hasNext()) {
                //System.out.println(scanner.nextLine());
                line = scanner.nextLine();
                splitLine = line.split(",");
                Coordinate c = new Coordinate(Integer.parseInt(splitLine[0]),Integer.parseInt(splitLine[1]));
                bGraph.nodeList.get(node++).setCoordinate(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return bGraph;

    }
}
