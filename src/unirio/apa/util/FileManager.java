package unirio.apa.util;

import unirio.apa.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class FileManager {
    private static final String NB_NODES = "NB_NODES";
    private static final String NB_ARCS = "NB_ARCS";

    public static Graph loadTestFile(String filename){
        Graph graph = null;
        Integer numNodes = -1;
        Integer numEdges = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            //Numero de nós na linha 0
            numNodes = Integer.valueOf(reader.readLine().split("\\s+")[1]);
            //Numero de arestas na linha 1
            numEdges = Integer.valueOf(reader.readLine().split("\\s+")[1]);

            //Inicializa o grafo
            graph = new Graph(numNodes, numEdges);

            //Lixo na linha 3
            reader.readLine();
            //Arestas nas linhas 4 a numEdges
            for(int i = 0; i < numEdges; i++){
                String[] tokens = reader.readLine().split("\\s+");
                Integer nodeStart = Integer.valueOf(tokens[0]);
                Integer nodeEnd = Integer.valueOf(tokens[1]);
                Integer weight = Integer.valueOf(tokens[2]);
                graph.addAdjacency(nodeStart, nodeEnd, weight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
