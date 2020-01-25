import java.io.*;
import java.util.ArrayList;

// Class DelivA does the work for deliverable DelivA of the Prog340

public class DelivA {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;

	public DelivA( File in, Graph gr ) {
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

		// Find start node
		ArrayList<Node> nodeList = gr.getNodeList();
		int startNodeIndex = -1;
		for (Node node: nodeList) {
			if ("S".equalsIgnoreCase(node.getVal())){
				startNodeIndex = nodeList.indexOf(node);
			}
		}
		// Make map from start node back to itself
		ArrayList<String> map = new ArrayList<String>();
		for (int i = startNodeIndex; i < nodeList.size() ; i++) {
			map.add(nodeList.get(i).abbrev);
		}
		if(startNodeIndex > 0){
			for (int i = 0; i < startNodeIndex; i++) {
				map.add(nodeList.get(i).abbrev);
			}
		}
		map.add(nodeList.get(startNodeIndex).getAbbrev());
		// Try to traverse the graph using the node order from the map
		boolean possible = true;
		while (possible){
			// check if the current node can get to the next node
		}
		// If the traversal succeeds print out the map
		// if the traversal fails print "Is not possible."

		System.out.println( "DelivA:  To be implemented");
		output.println( "DelivA:  To be implemented");
		output.flush();
		int indexOfS = getIndex();
//		tsp();

	}

	//Gives us the index.
	public int getIndex(){
		int size = this.g.getNodeList().size();
		for(int i = 0; i < size; i++){
			//Checking to see if we got start node
			if(this.g.getNodeList().get(i).getVal().equals("S")){
				return i;
			}
		}
		return -1;
	}

//	public void tsp(){
//		int distance = 0;
//		int start = getIndex();
//		int size = this.g.getNodeList().size();
//		for(int i = start; i < size; i++){
//			if(g.nodeList.get(i).incomingEdges.equals("~") || g.nodeList.get(i).outgoingEdges.equals("~")){
//				return;
//			}
//			else if (!(g.nodeList.get(i).incomingEdges.equals("~") || g.nodeList.get(i).outgoingEdges.equals("~"))){
//				distance += g.edgeList.get(i).dist;
//				System.out.println(distance);
//			}
//		}
//
//		}

	}








