import org.w3c.dom.css.Counter;

import java.io.*;
import java.util.*;

// Class DelivB does the work for deliverable DelivB of the Prog340

public class DelivB {

    File inputFile;
    File outputFile;
    PrintWriter output;
    Graph g;
    private int counter = 1;
    Stack<Node> dfsStack = new Stack<>();
    ArrayList<ArrayList<String>> sccs = new ArrayList<>();


    public DelivB(File in, Graph gr) {
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
        StringBuilder delivBOutput = new StringBuilder();


        //Got starting node.
        Node start = gr.getStartingNode();
        // DFS with discovery and finish times
        DFS(start);

        delivBOutput.append(gr.printNodeList("DFS of Graph") + "\n");
        // Classify the non tree edges
        for (Edge edge : gr.getEdgeList()) {
            Node v = edge.getHead();
            Node u = edge.getTail();
            if (edge.getEdgeType() == null) {
                if (u.getTimeDiscovered() > v.getTimeDiscovered() && u.getTimeFinished() < v.getTimeFinished()) {
                    edge.setEdgeType("Back");
                }
                if (u.getTimeDiscovered() < v.getTimeDiscovered() && u.getTimeFinished() > v.getTimeFinished()) {
                    edge.setEdgeType("Forward");
                }
                if (u.getTimeDiscovered() > v.getTimeDiscovered() && u.getTimeFinished() > v.getTimeFinished()) {
                    edge.setEdgeType("Cross");
                }
            }
        }


        Collections.sort(gr.getEdgeList(), new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.getTail().getName().compareTo(edge2.getTail().getName());
            }
        });
        delivBOutput.append(gr.printEdgeClassification() + "\n");
        //Clear the graph object.
        Graph.ClearGraph(gr);
        //Overwrite the counter
        counter = 1;
        //Transpose Graph
        Graph.transpose(gr);
        //Run DFS on COMP graph and track scc
        int sccIndex = 0;
        while (!dfsStack.isEmpty()) {
            Node source = dfsStack.pop();
            if (!source.isVisited()) {
                sccs.add(sccIndex, new ArrayList<String>());
                DFSComplement(source, sccIndex);
                sccIndex++;

            }
        }

        delivBOutput.append(gr.printNodeList("DFS of Complementary Graph:") + "\n");


        // Quantify strongly connected components and display the components
        delivBOutput.append("Strongly Connected Components: " + sccs.size() + "\n");
        for (ArrayList<String> scc : sccs) {
            Collections.sort(scc, (string1, string2) -> string1.compareTo(string2));
            for (String nodeName : scc
            ) {
                delivBOutput.append(nodeName);
                if(nodeName.length() > 1){
                    delivBOutput.append(" ");
                }
            }
            delivBOutput.append("\n");

        }

        // Classify the non tree edges
        System.out.println(delivBOutput);
        output.println(delivBOutput);
        //Ensures the file gets wrote.
        output.flush();
    }

    //Grab a node.
    public void DFS(Node start) {
        // 1. Visit node
        start.setVisited(true);
        //2. Setting the time discovered via an counter instance variable.
        start.setTimeDiscovered(counter);
        //3.Incrementing the counter.
        counter++;
        // Sort the nodes based on their distance.
        Collections.sort(start.getOutgoingEdges(), new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.getDist() - edge2.getDist();
            }
        });
        //4. If node is attached to Node(start) and is unvisited then call DFS on it.
        for (Edge edge : start.getOutgoingEdges()) {
            Node nextNode = edge.getHead();
            if (!nextNode.isVisited()) {
                //Classify the Tree Edges.
                edge.setEdgeType("Tree");
                DFS(nextNode);
            }
        }
        //Adding start node to the stack.
        dfsStack.push(start);
        //5.Record the time finished
        start.setTimeFinished(counter);
        //6. Increment that counter
        counter++;
    }

    //The complement DFS
    //Grab a node.
    public void DFSComplement(Node start, int sccIndex) {
        // 1. Visit node
        start.setVisited(true);
        //Append node to scc.
        sccs.get(sccIndex).add(start.getName());
        //2. Setting the time discovered via an counter instance variable.
        start.setTimeDiscovered(counter);
        //3.Incrementing the counter.
        counter++;
        // Sort the nodes based on their distance.
        Collections.sort(start.getOutgoingEdges(), new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.getDist() - edge2.getDist();
            }
        });
        //4. If node is attached to Node(start) and is unvisited then call DFS on it.
        for (Edge edge : start.getOutgoingEdges()) {
            Node nextNode = edge.getHead();
            if (!nextNode.isVisited()) {
                //Classify the Tree Edges.
                edge.setEdgeType("Tree");
                DFSComplement(nextNode, sccIndex);
            }
        }
        //5.Record the time finished
        start.setTimeFinished(counter);
        //6. Increment that counter
        counter++;
    }

    public static String sortString(String string) {
        // convert input string to char array
        char tempArray[] = string.toCharArray();

        // sort tempArray
        Arrays.sort(tempArray);

        // return new sorted string
        return new String(tempArray);
    }


}

