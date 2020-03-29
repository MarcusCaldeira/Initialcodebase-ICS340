import org.w3c.dom.NodeList;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

// Class DelivC does the work for deliverable DelivC of the Prog340

public class DelivC {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivC( File in, Graph gr ) {
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
		StringBuilder messageOutput = new StringBuilder();
		//read graph.
		//add fake edges.
		//Only do this when deliv C is running.

		//Print first tour.
		StringBuilder currentTourAbrev = new StringBuilder();
		ArrayList <Node>  firstTour = g.getNodeList();
		int totalDistance = 0;
		for (int i = 0; i < firstTour.size() - 1; i++) {
			Node tail = firstTour.get(i);
			Node head = firstTour.get(i + 1);
			Edge currentEdge = g.getEdge(head, tail);
			totalDistance += currentEdge.getDist();
			currentTourAbrev.append(tail.getAbbrev() + " ");
		}
		Node lastTail = firstTour.get(firstTour.size() - 1);
		Node lastHead = firstTour.get(0);
		currentTourAbrev.append(lastTail.getAbbrev() + " ");
		currentTourAbrev.append(lastHead.getAbbrev());
		Edge edgeHome = g.getEdge(lastHead, lastTail);
		totalDistance += edgeHome.getDist();
		String currentTour = String.format("Dist = %d: %s", totalDistance, currentTourAbrev);
		messageOutput.append(currentTour);

		/*
		* These will run as loop.
		*/
		//Generate random tours.
		//print next better tour.
		//stop generating tours.





		System.out.println(messageOutput);
		output.println(messageOutput);
		output.flush();
	}
}

