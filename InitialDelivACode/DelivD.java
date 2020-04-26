import java.io.File;
import java.io.PrintWriter;

// Class DelivD does the work for deliverable DelivD of the Prog340

public class DelivD {

    File inputFile;
    File outputFile;
    PrintWriter output;
    Graph g;
    StringBuilder messageOutput;
    private int[][] l;
    private Object[][] p;

    public DelivD(File in, Graph gr) {
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
        //Initializing the stringbuilder.
        messageOutput = new StringBuilder();
        // initialize l and p
        int n = gr.getNodeList().size();
        //Outer loop is the ROW
        for(Node rowNode: g.getNodeList()){
            System.out.println(g.getNodeList());
            //Inner Loop is the column
            for (Edge outgoingEdge:rowNode.getOutgoingEdges()){
                System.out.println(outgoingEdge.getHead().getAbbrev() + " ");
            }
            System.out.println();
        }


        int[][] w = new int[n][n];
        for (int i = 0; i <g.getNodeList().size() ; i++) {
            for (Edge outgoingEdges: g.getNodeList().get(i).getOutgoingEdges()
                 ) {
                int j = g.nodeList.indexOf(outgoingEdges.getHead());
                w[i][j] = outgoingEdges.dist;
            }
        }


        l = w;
//        l = new int[n][n];
        //This sets the ints to zero and anything else max value.
        for (int i = 0; i < l.length; i++) {
            for (int j = 0; j < l[i].length; j++) {
                if (i != j && l[i][j] == 0) l[i][j] = Integer.MAX_VALUE;
            }

        }
        p = new Object[n][n];
        //Will print the number of iterations per graph.
        for (int i = 0; i < n ; i++) {
            //TODO: compute iteration i
            printLandPrep(n,i);
        }

        System.out.println(messageOutput);
        output.println(messageOutput);
        output.flush();
    }

    //Print the L matrix and the pred matrices
    public void printLandPrep(int n, int i) {
        messageOutput.append("L[" + i + "]");
        //Print header row for both L and P matrices
        for (Node v : g.getNodeList()) {
            messageOutput.append(String.format("%4s", v.getAbbrev()));
        }
        messageOutput.append("            ");
        for (Node v : g.getNodeList()) {
            messageOutput.append(String.format("%8s", v.getAbbrev()));
        }
        messageOutput.append("\n");
        //Print L and P for each node.
        int index = 0;
        for (Node v : g.getNodeList()) {
            // Printing distances
            messageOutput.append(String.format("%4s", v.getAbbrev()));
            for (int h = 0; h < n; h++) {
                if (Math.abs(l[index][h]) >= 99) {
                    messageOutput.append(String.format("%4s", "~"));
                } else {
                    messageOutput.append(String.format("%4s", l[index][h]));
                }
            }// Printing predecessors
            messageOutput.append(String.format("%12s", v.getAbbrev()));
            for (int h = 0; h < n; h++) {
                if ((Math.abs(l[index][h]) >= 99) || ((index == h) && (l[index][h] == 0))) {
                    messageOutput.append(String.format("%8s", "~"));
                } else {
                    messageOutput.append(String.format("%8s", p[index][h]));
                }
            }
            index++;
            messageOutput.append("\n");
        }
        messageOutput.append("\n");


    }



}

