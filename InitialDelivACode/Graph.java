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
    public String printNodeList(){
        StringBuilder outputMessage = new StringBuilder();
        outputMessage.append("DFS of Graph\n");
        String header = String.format("%-10s%-10s%-10s\n","Node","Dist","Finish");
        outputMessage.append(header);
        for (int i = 0; i < nodeList.size(); i++) {
            Node currentNode = nodeList.get(i);
            String currentCityInMap = String.format("%-10s %-10d %-10d\n", currentNode.getName(), currentNode.getTimeDiscovered(), currentNode.getTimeFinished());
            outputMessage.append(currentCityInMap);
        }
        return outputMessage.toString();
    }

}
