import java.util.*;

public class Graph {

    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;

    public Graph() {
        nodeList = new ArrayList<Node>();
        edgeList = new ArrayList<Edge>();
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    public void addNode(Node n) {
        nodeList.add(n);
    }

    public void addEdge(Edge e) {
        edgeList.add(e);
    }

    /**
     * Return the index of the node with the value of "S"
     *
     * @return -1 if not found, otherwise the index in the nodeList
     */
    public int getStartNodeIndex() {
        int startNodeIndex = -1;
        for (Node node : nodeList) {
            if ("S".equalsIgnoreCase(node.getVal())) {
                startNodeIndex = nodeList.indexOf(node);
            }
        }
        return startNodeIndex;
    }
    public Node getStartingNode(){
        return nodeList.get(getStartNodeIndex());
    }
    public String printNodeList(String message){
        //Sort the node list by discovery time.
        Collections.sort(this.getNodeList(), new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return node1.getTimeDiscovered() - node2.getTimeDiscovered();
            }
        });
        StringBuilder outputMessage = new StringBuilder();
        outputMessage.append(message + "\n");
        String header = String.format("%-10s%-10s%-10s\n","Node","Disc","Finish");
        outputMessage.append(header);
        for (int i = 0; i < nodeList.size(); i++) {
            Node currentNode = nodeList.get(i);
            String currentCityInMap = String.format("%-10s %-10d %-10d\n", currentNode.getName(), currentNode.getTimeDiscovered(), currentNode.getTimeFinished());
            outputMessage.append(currentCityInMap);
        }
        return outputMessage.toString();
    }
    public String printEdgeClassification(){
        StringBuilder edgeOutputMessage = new StringBuilder();
        String header = String.format("%-5s%-5s\n","Edge","Classification:");
        String header2 = String.format("%-5s%-5s\n","Edge","Type");
        edgeOutputMessage.append(header);
        edgeOutputMessage.append(header2);
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            String currentCityInMap = String.format("%s->%s %-5s\n", edge.getTail().getName(), edge.getHead().getName(),edge.getEdgeType());
            edgeOutputMessage.append(currentCityInMap);
        }
        return edgeOutputMessage.toString();
    }
    //Will transpose the graph object.
    public static void transpose(Graph gr){
        ArrayList<Edge> edges = gr.getEdgeList();
        for (Edge edge: edges) {
            Node oldHead = edge.getHead();
            Node oldTail = edge.getTail();
            edge.setHead(oldTail);
            edge.setTail(oldHead);
        }
        ArrayList<Node> nodes = gr.getNodeList();
        for(Node node: nodes){
            ArrayList<Edge> oldOutEdges = (ArrayList<Edge>)node.getOutgoingEdges().clone();
            ArrayList<Edge> oldInEdges = (ArrayList<Edge>)node.getIncomingEdges().clone();
            node.outgoingEdges.clear();
            node.incomingEdges.clear();
            for (Edge oldOut: oldOutEdges){
                node.incomingEdges.add(oldOut);
            }
            for(Edge oldIn: oldInEdges){
                node.outgoingEdges.add(oldIn);
            }
        }
    }
    public static void ClearGraph(Graph gr){
        ArrayList<Node> nodes = gr.getNodeList();
        for(Node node: nodes){
            node.setVisited(false);
            node.setTimeDiscovered(0);
            node.setTimeFinished(0);
        }
    }


}
