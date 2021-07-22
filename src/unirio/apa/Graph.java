package unirio.apa;

public class Graph {
    class Node {
        Integer destNode;
        Integer weight;
        Node next;

        public Node(int destNode, int weight) {
            this.destNode = destNode;
            this.weight = weight;
            next = null;
        }
    }

    private Integer numNodes;
    private Integer numEdges;
    private Node[] nodeLists;

    public Graph(Integer numNodes, Integer numEdges){
        this.numNodes = numNodes;
        this.numEdges = numEdges;
        nodeLists = new Node[numNodes];
    }

    public void addAdjacency(int node, int destNode, int weight) {
        Node newNode = new Node(destNode, weight);

        if (nodeLists[node] == null){
            nodeLists[node] = newNode;
        }else{
            Node lastNode = nodeLists[node];
            while (lastNode.next != null){
                lastNode = lastNode.next;
            }
            lastNode.next = newNode;
        }
    }

    public void printAdjacencyList(){
        for(int i = 0; i < numNodes; i++){
            StringBuilder data = new StringBuilder(Integer.toString(i));
            Node node = nodeLists[i];
            while (node.next != null){
                data.append(" > " + node.destNode + " (" + node.weight + ")" );
                node = node.next;
            }
            System.out.println(data);
        }
    }

    public Integer[] minimalPathBasic(){
        Integer[] path = new Integer[numNodes];
        return path;
    }
}