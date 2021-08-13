package unirio.apa;

import unirio.apa.MinHeap.NodeHeap;
import unirio.apa.util.DijkstraData;

import java.time.Duration;
import java.time.Instant;

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

        public String toString(){
            String data =  " > " + destNode + " (" + weight + ")";
            if (next != null){
                return data + next;
            }else{
                return data;
            }
        }
    }

    private final int numNodes;
    private final int numEdges;
    private final Node[] nodeLists;

    public int getNumNodes() {
        return numNodes;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public Graph(int numNodes, int numEdges){
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
            Node node = nodeLists[i];
            System.out.println(Integer.toString(i) + node);
        }
    }

    public Integer[] minimalPathsBasic(){
        // Inicializações
        final int STARTER_NODE = 0;

        Integer[] paths = new Integer[numNodes];
        boolean[] explored = new boolean[numNodes];
        for(int i = STARTER_NODE; i < numNodes; i++) {
            paths[i] = Integer.MAX_VALUE;
            explored[i] = false;
        }

        // Iniciamos com o nó inicial
        paths[STARTER_NODE] = 0;
        explored[STARTER_NODE] = true;

        // Loop externo roda num_node vezes
        for (int v = 0; v < numNodes; v++){
            int minDist = Integer.MAX_VALUE;
            int nextNode = -1;
            // Loop interno roda num_node vezes
            for(int i = STARTER_NODE; i < numNodes; i++){
                if(explored[i]){
                    Node node = nodeLists[i];
                    while(node != null){
                        int candidate = node.destNode;
                        if(!explored[candidate]){
                            int dist = paths[i] + node.weight;
                            if(dist < minDist){
                                minDist = dist;
                                nextNode = candidate;
                            }
                        }
                        node = node.next;
                    }
                }
            }
            if(minDist == Integer.MAX_VALUE) break;
            // Se não chegou a nenhum nó novo, os outros são inacessíveis
            paths[nextNode] = minDist;
            explored[nextNode] = true;
        }
        return paths;
    }

    public Integer[] minimalPathsHeap(){

        // Inicializações
        final int STARTER_NODE = 0;
        Integer[] paths = new Integer[numNodes];
        boolean[] explored = new boolean[numNodes];

        MinHeap minHeap = new MinHeap(numNodes);
        minHeap.insert(STARTER_NODE, 0);
        for (int i = 1; i < numNodes; i++){
            minHeap.insert(i, Integer.MAX_VALUE);
        }

        while(!minHeap.isEmpty()){
            NodeHeap minNode = minHeap.remove();
            int nodeNum = minNode.node;
            int nodePath = minNode.pathWeight;
            explored[nodeNum] = true;
            paths[nodeNum] = nodePath;

            Node node = nodeLists[nodeNum];

            while(node != null){
                int targetDest = node.destNode;
                int currentPath = minHeap.getPath(targetDest);
                int targetDist = node.weight;
                int candidatePath = nodePath + targetDist;

                if(!explored[targetDest] && candidatePath < currentPath){
                    minHeap.revisePath(targetDest, candidatePath);
                }

                node = node.next;
            }
        }

        return paths;
    }

    public DijkstraData getMinimalPathsBasic() {
        Instant start = Instant.now();
        Integer[] paths = minimalPathsBasic();
        Instant finish = Instant.now();
        long duration = Duration.between(start, finish).toMillis();
        return new DijkstraData(paths, duration);
    }

    public DijkstraData getMinimalPathsHeap() {
        Instant start = Instant.now();
        Integer[] paths = minimalPathsHeap();
        Instant finish = Instant.now();
        long duration = Duration.between(start, finish).toMillis();
        return new DijkstraData(paths, duration);
    }
}