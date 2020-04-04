import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

// Class DelivC does the work for deliverable DelivC of the Prog340

public class DelivC {

    File inputFile;
    File outputFile;
    PrintWriter output;
    Graph g;
    ArrayList<Node> usedStartNodes = new ArrayList();
    int shortestDistance;
    int toursGenerated = 0;

    public DelivC(File in, Graph gr) {
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
        StringBuilder messageOutput = new StringBuilder();
        messageOutput.append("\n********************************\nOnly the improved tours:\n");
        //read graph.
        //add fake edges.
        //Only do this when deliv C is running.

        //Print first tour.
        ArrayList<Node> firstTour = g.getNodeList();
        usedStartNodes.add(firstTour.get(0));
        Tour currentTour = getTour(firstTour);
        messageOutput.append(currentTour);

        //Generate random tour based on first tour.
        this.shortestDistance = currentTour.getDistance();
        randomRestart(messageOutput, firstTour);


		ArrayList <Node> newLocalSearch = tourGenerator();
        //stop generating tours.
		while (newLocalSearch != null){
            //Start local search from a different place also known as randomRestart
            randomRestart(messageOutput, newLocalSearch);
            newLocalSearch = tourGenerator();
        }

		// Print output to console and write to file
        System.out.println(messageOutput);
        output.println(messageOutput);
        output.flush();
    }

    public ArrayList<Node> tourGenerator() {
        ArrayList<Node> nodeList = g.nodeList;
        Node newStartNode;
		boolean allNodesUsed = (usedStartNodes.size() == nodeList.size());
		boolean newNodefound = false;
		do {
            newStartNode = nodeList.get(new Random().nextInt(nodeList.size()));
            newNodefound = !usedStartNodes.contains(newStartNode);

        } while ( !newNodefound && !allNodesUsed

        );
		if(!allNodesUsed){
		    usedStartNodes.add(newStartNode);
			ArrayList<Node> randomTour = new ArrayList();
			randomTour.add(newStartNode);
			for (Node node: nodeList) {
				if(!node.getAbbrev().equals(newStartNode.getAbbrev())){
					randomTour.add(node);
				}
			}
			toursGenerated++;
			return randomTour;
		}
		return null;
    }

    private void randomRestart(StringBuilder messageOutput, ArrayList<Node> firstTour) {
        int tryCounter = 10;
        ArrayList<Node> temptour = firstTour;
        //print next better tour.
        while (tryCounter > 0) {
            ArrayList<Node> secondTour = randomizeNodes(temptour);
            temptour = secondTour;
            Tour firstRandomTour = getTour(secondTour);
            if (firstRandomTour.getDistance() < shortestDistance) {
                messageOutput.append(firstRandomTour);
                shortestDistance = firstRandomTour.getDistance();
            } else {
                tryCounter--;
            }
        }
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
		System.out.print(printTour);
        return new Tour(totalDistance, printTour);
    }

    public ArrayList<Node> randomizeNodes(ArrayList<Node> nodeList) {
        // dogs.stream().map(Dog::new).collect(toCollection(ArrayList::new))
        // deep copy of nodeList
        ArrayList<Node> deepCopy = new ArrayList<>();
        ;
        for (Node node : nodeList
        ) {
            deepCopy.add(node);
        }
        ArrayList<Node> newNodeList = new ArrayList<>();
        //Saves the starting city.
        newNodeList.add(deepCopy.remove(0));
        //While nodeList is not empty This will remove a random node from the remaining nodes in nodeList.
        while (!deepCopy.isEmpty()) {
            newNodeList.add(deepCopy.remove(new Random().nextInt(deepCopy.size())));
        }
        return newNodeList;
    }

    private class Tour {
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

