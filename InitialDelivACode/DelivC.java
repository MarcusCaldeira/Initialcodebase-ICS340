import org.w3c.dom.NodeList;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

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
		ArrayList<Node> firstTour = g.getNodeList();
		Tour currentTour = getTour(firstTour);
		messageOutput.append(currentTour);

		/*
		* These will run as loop.
		*/
		//Generate random tours.
		int shortestDistance = currentTour.getDistance();
		int tryCounter = 100;
		ArrayList<Node> temptour = (ArrayList<Node>) firstTour;
		//print next better tour.
		while (tryCounter > 0){
			ArrayList <Node> secondTour = switchIndex(temptour);
			temptour = secondTour;
			Tour firstRandomTour = getTour(secondTour);
			if(firstRandomTour.getDistance() < shortestDistance){
				messageOutput.append(firstRandomTour);
				shortestDistance = firstRandomTour.getDistance();
			}
			else{
				tryCounter--;
			}
		}
		//Start local search from a different place.




		//stop generating tours.





		System.out.println(messageOutput);
		output.println(messageOutput);
		output.flush();
	}

	private Tour getTour(ArrayList<Node> firstTour) {
		StringBuilder currentTourAbrev = new StringBuilder();
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
		String printTour = String.format("Dist = %d: %s\n", totalDistance, currentTourAbrev);
		//Uncomment the line bellow to print every tour.
//		System.out.println(printTour);
		return new Tour(totalDistance, printTour);
	}
	public ArrayList <Node> switchIndex(ArrayList <Node> nodeList){
		// dogs.stream().map(Dog::new).collect(toCollection(ArrayList::new))
		// deep copy of nodeList
		ArrayList<Node> deepCopy  = new ArrayList<>();;
		for (Node node: nodeList
			 ) {
			deepCopy.add(node);
		}
		ArrayList <Node> newNodeList = new ArrayList<>();
		//Saves the starting city.
		newNodeList.add(deepCopy.remove(0));
		//While nodeList is not empty This will remove a random node from the remaining nodes in nodeList.
		while (!deepCopy.isEmpty()){
			newNodeList.add(deepCopy.remove(new Random().nextInt(deepCopy.size())));
		}
		return newNodeList;
	}

	private class Tour{
		private final int distance;
		private final String tour;

		public Tour(int distance, String tour) {
			this.distance = distance;
			this.tour = tour;
		}

		public int getDistance() {
			return distance;
		}

		public String getTour() {
			return tour;
		}

		@Override
		public String toString() {
			return this.tour;
		}
	}
}

