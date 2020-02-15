import java.io.*;
import java.util.ArrayList;

// Class DelivB does the work for deliverable DelivB of the Prog340

public class DelivB {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivB( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For retests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}

		// DFS with discovery and finish times
		int startNodeIndex = gr.getStartNodeIndex();
		Node start = gr.getNodeList().get(startNodeIndex);
		DFSRecursive(gr, start);

		// Classify the edges

		// Quantify strongly connected components and display the components

		System.out.println( "DelivB:  To be implemented");
		output.println( "DelivB:  To be implemented");
	}

	public void DFSRecursive(Graph graph, Node start){
		start.setVisited();
		System.out.print(start.toString() + " ");
		for (Edge edge : start.getOutgoingEdges()
			 ) {
			if(!edge.getHead().isVisited()){
				DFSRecursive(graph, edge.getHead());
			}
		}
	}






}

