import unirio.apa.Graph;
import unirio.apa.util.FileManager;

public class App {
    static String input = "input/test_set1/check_v5_s1.dat";

    public static void main(String[] args) {
        Graph test = FileManager.loadTestFile(input);
        System.out.println("Loaded graph");
        test.printAdjacencyList();
    }
}
