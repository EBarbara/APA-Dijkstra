package unirio.apa;

public class Graph {
    class Node {
        Integer destNode;
        Integer weight;
        Node next;

        public Node(Integer destNode, Integer weight) {
            this.destNode = destNode;
            this.weight = weight;
            next = null;
        }

        public Node(){
            this(null, null);
        }

        public String toString(){
            String data =  " > " + destNode + " (" + weight + ")";
            if (next != null){
                return data + next.toString();
            }else{
                return data;
            }
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

    public void addNode(int node) {
        Node newNode = new Node();
        if (nodeLists[node] == null) {
            nodeLists[node] = newNode;
        }
    }

    public void printAdjacencyList(){
        for(int i = 0; i < numNodes; i++){
            Node node = nodeLists[i];
            System.out.println(Integer.toString(i) + node);
        }
    }

    public Integer[] minimalPathBasic(){
        Integer[] path = new Integer[numNodes];
        return path;
    }
}