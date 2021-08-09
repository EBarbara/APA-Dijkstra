package unirio.apa;

public class MinHeap {
    class NodeHeap{
        int node;
        int pathWeight;

        public NodeHeap(int node, int pathWeight) {
            this.node = node;
            this.pathWeight = pathWeight;
        }

        public String toString(){
            return "Node: " + node + "; distance: " + pathWeight;
        }
    }

    private final NodeHeap[] heap;
    private final int maxSize;
    private int size;

    // Initializaing front as static with unity
    private static final int FRONT = 1;

    public MinHeap(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        heap = new NodeHeap[this.maxSize + 1];

        //Trapaceando, me aproveitando que não tem peso negativo
        heap[0] = new NodeHeap(-1, -1);
    }

    private int parent(int pos) {
        return pos/2;
    }

    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    private boolean isLeaf(int pos){
        return pos > (size / 2) && pos <= size;
    }

    private void swap(int fPos, int sPos) {
        NodeHeap tmp;
        tmp = heap[fPos];

        heap[fPos] = heap[sPos];
        heap[sPos] = tmp;
    }

    private void minHeapify(int pos) {
        if(!isLeaf(pos)){
               if(heap[pos].pathWeight > heap[leftChild(pos)].pathWeight
                    || heap[pos].pathWeight > heap[rightChild(pos)].pathWeight){
                // Swap with the left child and heapify the left child
                if (heap[leftChild(pos)].pathWeight < heap[rightChild(pos)].pathWeight) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                } else {
                    // Swap with the right child and heapify the right child
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }

    public void insert(int node, int pathWeight) {
        if (size >= maxSize)
            return;

        NodeHeap nodeHeap = new NodeHeap(node, pathWeight);
        heap[++size] = nodeHeap;
        int current = size;

        while(heap[current].pathWeight < heap[parent(current)].pathWeight){
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(int node) {
        for(int i = 1; i <= size; i++){
            if (node == heap[i].node)
                return true;
        }
        return false;
    }

    public void print(){
        for (int i = 1; i <= size / 2; i++) {
            // Printing the parent and both childrens
            System.out.print(
                    " PARENT : " + heap[i]
                            + " LEFT CHILD : " + heap[leftChild(i)]
                            + " RIGHT CHILD :" + heap[rightChild(i)]);

            // By here new line is required
            System.out.println();
        }
    }

    public NodeHeap remove() {
        NodeHeap popped = heap[FRONT];
        heap[FRONT] = heap[size--];
        if (size > 0)
            minHeapify(FRONT);

        return popped;
    }

    public int getPath(int targetDest) {
        for (int i = 1; i <= size; i++){
            NodeHeap node = heap[i];
            if (node.node == targetDest){
                return node.pathWeight;
            }
        }
        return -1;
    }

    public void revisePath(int targetDest, int candidatePath) {
        for (int i = 1; i <= size; i++){
            NodeHeap node = heap[i];
            if (node.node == targetDest){
                node.pathWeight = candidatePath;

                int current = i;

                while(heap[current].pathWeight < heap[parent(current)].pathWeight){
                    swap(current, parent(current));
                    current = parent(current);
                }
                break;
            }
        }
    }
}
