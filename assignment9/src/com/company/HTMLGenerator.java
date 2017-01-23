package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by shrey on 23-01-2017.
 */
public class HTMLGenerator {
    BGraph bGraph;
    String htmlString;

    private final int IMAGE_HEIGHT = 1094;
    private final int IMAGE_WIDTH = 1486;

    private final int DISPLAY_HEIGHT = 720;
    private final int DISPLAY_WIDTH = 1280;

    public HTMLGenerator(BGraph bGraph) {
        this.bGraph = bGraph;
    }

    public String getHtmlString() {
        String baseHTML = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Immigration Emmigration Map</title>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        function hideAll(className) {\n" +
                "            for(var i = 0; i < document.getElementsByClassName(className).length; i++) {\n" +
                "                document.getElementsByClassName(className)[i].style.visibility = \"hidden\";\n" +
                "            }\n" +
                "        }\n" +
                "        function showAll(className) {\n" +
                "            for(var i = 0; i < document.getElementsByClassName(className).length; i++) {\n" +
                "                document.getElementsByClassName(className)[i].style.visibility = \"visible\";\n" +
                "            }\n" +
                "        }\n" +
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<img src=\"map_europe.png\"\n" +
                "     style=\"position:fixed;top: 0px;left: 0px; width:1280px;height:720px;z-index:-1;\">\n";

        HashMap<Integer, ArrayList<Feature>> featureMap =
                bGraph.extractFeatures();

        StringBuilder edgeHTMLs = new StringBuilder("");

        for (Node node : bGraph.nodeList) {
            if (node.getCoordinate().getY() != -1 && node.getCoordinate().getX() != -1) {
                LinkedList<Integer> sourceCoords = getScaled(node.getCoordinate().getY(), node.getCoordinate().getX());
                Integer sourceTop = sourceCoords.get(0);
                Integer sourceLeft = sourceCoords.get(1);

                ArrayList<Feature> featuresForNode = featureMap.get(node.nodeId);
                int timestepIndex = 0;
                for (Feature feature : featuresForNode) {
                    edgeHTMLs.append(getEdgeHTML(feature, sourceTop, sourceLeft, timestepIndex, true));
                    edgeHTMLs.append("\n");
                    edgeHTMLs.append(getEdgeHTML(feature, sourceTop, sourceLeft, timestepIndex, false));
                    edgeHTMLs.append("\n");
                    timestepIndex++;
                }
            }
        }

        String endHTML = "<table>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\" value=\"Hide All\" onclick=\"hideAll('ts0'); hideAll('ts1'); hideAll('ts2'); hideAll('ts3')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show All\" onclick=\"showAll('ts0'); showAll('ts1'); showAll('ts2'); showAll('ts3')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Hide TS0 Low\" onclick=\"hideAll('ts0 low')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show TS0 Low\" onclick=\"showAll('ts0 low')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Hide TS0 High\" onclick=\"hideAll('ts0 high')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show TS0 High\" onclick=\"showAll('ts0 high')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Hide TS1 Low\" onclick=\"hideAll('ts1 low')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show TS1 Low\" onclick=\"showAll('ts1 low')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Hide TS1 High\" onclick=\"hideAll('ts1 high')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show TS1 High\" onclick=\"showAll('ts1 high')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Hide TS2 Low\" onclick=\"hideAll('ts2 low')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show TS2 Low\" onclick=\"showAll('ts2 low')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Hide TS2 High\" onclick=\"hideAll('ts2 high')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show TS2 High\" onclick=\"showAll('ts2 high')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Hide TS3 Low\" onclick=\"hideAll('ts3 low')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show TS3 Low\" onclick=\"showAll('ts3 low')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Hide TS3 High\" onclick=\"hideAll('ts3 high')\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td><input type=\"button\"  value=\"Show TS3 High\" onclick=\"showAll('ts3 high')\"/></td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        return baseHTML + edgeHTMLs.toString() + endHTML;
    }

    private LinkedList<Integer> getScaled(int originalTop, int originalLeft) {
        int scaledTop = (originalTop * DISPLAY_HEIGHT) / IMAGE_HEIGHT;
        int scaledLeft = (originalLeft * DISPLAY_WIDTH) / IMAGE_WIDTH;

        LinkedList<Integer> scaled = new LinkedList<>();
        scaled.add(scaledTop);
        scaled.add(scaledLeft);

        return scaled;
    }

    private String getEdgeHTML(Feature feature, int sourceTop, int sourceLeft, int timestepIndex, boolean low) {
        try {
            int originalTop, originalLeft, height;
            String classStr;
            if (low) {
                originalTop = bGraph.getNodeByNodeID(feature.low.to).getCoordinate().getY();
                originalLeft = bGraph.getNodeByNodeID(feature.low.to).getCoordinate().getX();
                height = 5;
                classStr = "low";
            } else {
                originalTop = bGraph.getNodeByNodeID(feature.high.to).getCoordinate().getY();
                originalLeft = bGraph.getNodeByNodeID(feature.high.to).getCoordinate().getX();
                height = 10;
                classStr = "high";
            }


            LinkedList<Integer> temp = getScaled(originalTop, originalLeft);
            Integer scaledTop = temp.get(0);
            Integer scaledLeft = temp.get(1);

            int distance = (int) Math.round(Math.sqrt(Math.pow((scaledTop - sourceTop), 2) + Math.pow((scaledLeft - sourceLeft), 2)));

            int deg = 270 - (int) Math.round(Math.toDegrees(Math.atan2(sourceLeft - scaledLeft, sourceTop - scaledTop)));

            return "<img class = \"ts" + timestepIndex + " " + classStr + "\" src=\"Arrow_Forward.png\"\n" +
                    "     style=\"position: fixed; top: " + (sourceTop - 23) + "px;left: " + sourceLeft + "px; width:" + distance + "px;height:" + height + "%;transform: rotate(" + deg + "deg);transform-origin: left\">";
        } catch (Exception e) {
            return "";
        }
    }

}
