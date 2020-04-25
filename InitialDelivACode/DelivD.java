import java.io.File;
import java.io.PrintWriter;

// Class DelivD does the work for deliverable DelivD of the Prog340

public class DelivD {

    File inputFile;
    File outputFile;
    PrintWriter output;
    Graph g;
    StringBuilder messageOutput;

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
        printLandPrep(22, 2);
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
            messageOutput.append(String.format("%4s", v.getAbbrev()));
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
                    messageOutput.append(String.format("%4d", l[index][h]);
                }
            }// Printing predecessors
            messageOutput.append(String.format("%12s", v.getAbbrev());
            for (int h = 0; h < n; h++) {
                if ((Math.abs(l[index][h]) >= 99) || ((index == h) && (l[index][h] == 0))) {
                    messageOutput.append(String.format("%8s", "~");
                } else {
                    messageOutput.append(String.format("%8s", p[index][h]);
                }
            }
            index++;
            messageOutput.append("\n");
        }
        messageOutput.append("\n");


    }


}

