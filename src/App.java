import unirio.apa.Graph;
import unirio.apa.util.DijkstraData;
import unirio.apa.util.FileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private static final String RESULTS_FILE = "output\\results.txt";
    private static final String TIMES_FILE = "output\\times.csv";

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        // Verifica se é um teste
        boolean testRun = Arrays.asList(args).contains(TEST);

        // Lista todos os arquivos na pasta INPUT
        try(
                BufferedWriter resultWriter = new BufferedWriter(new FileWriter(RESULTS_FILE));
                BufferedWriter timeWriter = new BufferedWriter(new FileWriter(TIMES_FILE));
                Stream<Path> walk = Files.walk(Paths.get(INPUT))
        ){
            List<String> files = walk.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());
            resultWriter.write("Resultados gerados em " + dtf.format(now) + "\n");
            timeWriter.write("File,Nodes,Edges,Basic,Heap\n");
            for(String filePath : files){
                if(testRun){
                    if(!filePath.contains(TEST_PATTERN)){
                        continue;
                    }
                }
                runDijkstra(filePath, resultWriter, timeWriter);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void runDijkstra(String filePath, BufferedWriter resultWriter, BufferedWriter timeWriter) throws IOException {
        resultWriter.write(filePath + "\n");
        System.out.println("Working on " + filePath);

        Graph graph;
        long basicTime = -1;
        long heapTime = -1;
        int nodes = -1;
        int edges = -1;

        if(filePath.contains(TEST_EXT)){
            graph = FileManager.loadTestFile(filePath);
        }else if(filePath.contains(MAIN_EXT)) {
            graph = FileManager.loadMainFile(filePath);
        }else{
            System.out.println("Tipo de arquivo não reconhecido");
            return;
        }
        if(Objects.nonNull(graph)){
            nodes = graph.getNumNodes();
            edges = graph.getNumEdges();

            DijkstraData dijkstraBasic = graph.getMinimalPathsBasic();
            basicTime = dijkstraBasic.getTime();
            Integer[] basicPaths = dijkstraBasic.getPaths();
            resultWriter.write("BASIC--------------------------------\n");
            for(int i = 0; i < nodes; i++){
                resultWriter.write("d[" + i + "] = " + basicPaths[i] + "\n");
            }

            DijkstraData dijkstraHeap = graph.getMinimalPathsHeap();
            heapTime = dijkstraHeap.getTime();
            Integer[] heapPaths = dijkstraHeap.getPaths();
            resultWriter.write("HEAP---------------------------------\n");
            for(int i = 0; i < nodes; i++){
                resultWriter.write("d[" + i + "] = " + heapPaths[i] + "\n");
            }
        }
        String filename = filePath.substring(filePath.lastIndexOf("\\")+1);
        timeWriter.write(filename + "," + nodes + "," + edges + "," + basicTime + "," + heapTime + "\n");
    }
}
