import unirio.apa.Graph;
import unirio.apa.util.FileManager;

public class App {
    static String test1 = "input/test_set1/check_v5_s1.dat";
    static String test2 = "input/test_set2/check_v5_s2.dat";
    

    public static void main(String[] args) {
        System.out.println("Test instance 1");
        Graph gTest1 = FileManager.loadTestFile(test1);
        gTest1.printAdjacencyList();

        System.out.println("--------------------------------------------------------------");

        System.out.println("Test instance 2");
        Graph gTest2 = FileManager.loadTestFile(test2);
        gTest2.printAdjacencyList();
    }
}
