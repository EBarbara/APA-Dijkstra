package unirio.apa;

import unirio.apa.MinHeap.NodeHeap;

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

    private static boolean hasFalse(boolean[] array){
        for(boolean b : array) if(!b) return true;
        return false;
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

        // Enquanto houver nós não explorados
        while(hasFalse(explored)){
            int minDist = Integer.MAX_VALUE;
            int nextNode = -1;
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

        MinHeap minHeap = new MinHeap(numNodes);
        minHeap.insert(STARTER_NODE, 0);
        for (int i = 1; i < numNodes; i++){
            minHeap.insert(i, Integer.MAX_VALUE);
        }

        while(!minHeap.isEmpty()){
            NodeHeap minNode = minHeap.remove();
            int nodePath = minNode.pathWeight;
            paths[minNode.node] = nodePath;

            Node node = nodeLists[minNode.node];

            while(node != null){
                int targetDest = node.destNode;
                int currentPath = minHeap.getPath(targetDest);
                int targetDist = node.weight;
                int candidatePath = nodePath + targetDist;

                if(minHeap.contains(targetDest)
                        && candidatePath < currentPath){
                    minHeap.revisePath(targetDest, candidatePath);
                }

                node = node.next;
            }
        }

        return paths;
    }

    public void printMinimalPathsBasic() {
        Integer[] paths = minimalPathsBasic();
        for(int i = 0; i < numNodes; i++){
            System.out.println("d[" + i + "] = " + paths[i]);
        }
    }

    public void printMinimalPathsHeap() {
        Integer[] paths = minimalPathsHeap();
        for(int i = 0; i < numNodes; i++){
            System.out.println("d[" + i + "] = " + paths[i]);
        }
    }
}