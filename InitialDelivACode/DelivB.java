import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

// Class DelivB does the work for deliverable DelivB of the Prog340

public class DelivB {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	private int counter = 1;
	
	public DelivB( File in, Graph gr) {
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
		StringBuilder delivBOutput = new StringBuilder();


		//Got starting node.
		Node start = gr.getStartingNode();
		// DFS with discovery and finish times
		DFS(start);
		//Sort the node list by discovery time.
		Collections.sort(gr.getNodeList(), new Comparator<Node>() {
			@Override
			public int compare(Node node1, Node node2) {
				return node1.getTimeDiscovered() - node2.getTimeDiscovered();
			}
		});
		delivBOutput.append(gr.printNodeList()+ "\n");
		// Classify the non tree edges
		for(Edge edge: gr.getEdgeList()){
			Node v = edge.getHead();
			Node u = edge.getTail();
			if(edge.getEdgeType() == null){
				if(u.getTimeDiscovered() > v.getTimeDiscovered() && u.getTimeFinished() < v.getTimeFinished()){
					edge.setEdgeType("Back");
				}
				if(u.getTimeDiscovered() < v.getTimeDiscovered() && u.getTimeFinished() > v.getTimeFinished()){
					edge.setEdgeType("Forward");
				}
				if(u.getTimeDiscovered() > v.getTimeDiscovered() && u.getTimeFinished() > v.getTimeFinished()){
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
		delivBOutput.append(gr.printEdgeClassification()+ "\n");
		//Transpose Graph
		Graph.transpose(gr);

		// Quantify strongly connected components and display the components
		delivBOutput.append(gr.printNodeList()+ "\n");
		// Classify the non tree edges
		System.out.println(delivBOutput);
		output.println(delivBOutput);
		//Ensures the file gets wrote.
		output.flush();
	}
	//Grab a node.
	public void DFS(Node start){
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
		for(Edge edge: start.getOutgoingEdges()){
			Node nextNode = edge.getHead();
			if(!nextNode.isVisited()){
				//Classify the Tree Edges.
				edge.setEdgeType("Tree");
				DFS(nextNode);
			}
		}
		//5.Record the time finished
		start.setTimeFinished(counter);
		//6. Increment that counter
		counter++;
	}







}

