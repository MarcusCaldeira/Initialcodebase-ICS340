import java.io.*;
import java.util.ArrayList;

// Class DelivA does the work for deliverable DelivA of the Prog340

public class DelivA {

    File inputFile;
    File outputFile;
    PrintWriter output;
    Graph g;

    public DelivA(File in, Graph gr) {
        inputFile = in;
        g = gr;

        // Get output file name.
        String inputFileName = inputFile.toString();
        String baseFileName = inputFileName.substring(0, inputFileName.length() - 4); // Strip off ".txt"
        String outputFileName = baseFileName.concat("_out.txt");
        outputFile = new File(outputFileName);
        if (outputFile.exists()) {    // For retests
            outputFile.delete();
        }

        try {
            output = new PrintWriter(outputFile);
        } catch (Exception x) {
            System.err.format("Exception: %s%n", x);
            System.exit(0);
        }

        // Find start node
        ArrayList<Node> nodeList = gr.getNodeList();
        int startNodeIndex = -1;
        for (Node node : nodeList) {
            if ("S".equalsIgnoreCase(node.getVal())) {
                startNodeIndex = nodeList.indexOf(node);
            }
        }
        // Make map from start node back to itself
        ArrayList<Node> map = new ArrayList<Node>();
        for (int i = startNodeIndex; i < nodeList.size(); i++) {
            map.add(nodeList.get(i));
        }
        if (startNodeIndex > 0) {
            for (int i = 0; i < startNodeIndex; i++) {
                map.add(nodeList.get(i));
            }
        }
        map.add(nodeList.get(startNodeIndex));

        // Try to traverse the graph using the node order from the map
        boolean traversalPossible = true;
        for (int i = 0; i < map.size() - 1; i++) {
            boolean canMoveToNextNode = false;
            Node currentNode = map.get(i);
            Node nextNode = map.get(i + 1);

            ArrayList<Edge> outgoingEdges = currentNode.getOutgoingEdges();

            for (Edge edge : outgoingEdges) {
                if (nextNode.getAbbrev().equalsIgnoreCase(edge.getHead().getAbbrev())) {
                    canMoveToNextNode = true;
                    break; // leave the outgoing edge for loop
                }
            }

            if (canMoveToNextNode == false) {
                traversalPossible = false;
                break; // stop checking if the graph can be traversed
            }
        }

        StringBuilder outputMessage = new StringBuilder();
        outputMessage.append("TSP tour:\n");
        // If the traversal succeeds print out the map
        if (traversalPossible) {
            for (int i = 0; i < map.size() - 1; i++) {
                String currentCityInMap = String.format("%s--> ", map.get(i).getAbbrev());
                outputMessage.append(currentCityInMap);
            }
            //This is the last city in the map
            String currentCityInMap = String.format("%s", map.get(map.size() - 1).getAbbrev());
            outputMessage.append(currentCityInMap);
        } else
            outputMessage.append("Is not possible.");
        // if the traversal fails print "Is not possible."
        //Line that writes it to console.
        System.out.println(outputMessage);
        //This writes to the file.
        output.println(outputMessage);
        //Ensures that the file gets wrote.
        output.flush();
    }

}








