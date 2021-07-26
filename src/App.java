import unirio.apa.Graph;
import unirio.apa.util.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    private static final String TEST = "test";
    private static final String INPUT = "input";
    private static final String TEST_PATTERN = "check_v5_s";
    private static final String TEST_EXT = ".dat";
    private static final String MAIN_EXT = ".stp";

    public static void main(String[] args) {
        // Checks if is a test
        boolean testRun = Arrays.asList(args).contains(TEST);

        // List all files in INPUT folder
        try(Stream<Path> walk = Files.walk(Paths.get(INPUT))){
            List<String> files = walk.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());
            for(String filePath : files){
                if(testRun){
                    if(!filePath.contains(TEST_PATTERN)){
                        continue;
                    }
                }
                runDijkstra(filePath);
                System.out.println("--------------------------------------------------------------");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void runDijkstra(String filePath){
        System.out.println(filePath);
        Graph graph;

        if(filePath.contains(TEST_EXT)){
            graph = FileManager.loadTestFile(filePath);
        }else if(filePath.contains(MAIN_EXT)) {
            graph = FileManager.loadMainFile(filePath);
        }else{
            System.out.println("File type unrecognized");
            return;
        }
        if(Objects.nonNull(graph)){
            graph.printAdjacencyList();
        }
    }
}
