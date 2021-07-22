package unirio.apa.util;

import unirio.apa.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static Graph loadMainFile(String filename){
        Graph graph = null;
        Integer numNodes = -1;
        Integer numEdges = -1;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            //Lixo nas linhas 1-8
            for(int i = 1; i < 9; i++){
                reader.readLine();
            }

            //Numero de nós na linha 9
            numNodes = Integer.valueOf(reader.readLine().split("\\s+")[1]) + 1;
            //Numero de arestas na linha 10
            numEdges = Integer.valueOf(reader.readLine().split("\\s+")[1]);

            //Inicializa o grafo e carrega um nó fantasma na posição 0
            graph = new Graph(numNodes, numEdges);
            graph.addNode(0);

            //Arestas nas linhas 11 a numEdges
            for(int i = 0; i < numEdges; i++){
                String[] tokens = reader.readLine().split("\\s+");
                Integer nodeA = Integer.valueOf(tokens[1]);
                Integer nodeB = Integer.valueOf(tokens[2]);
                Integer weight = Integer.valueOf(tokens[3]);
                graph.addAdjacency(nodeA, nodeB, weight);
                graph.addAdjacency(nodeB, nodeA, weight);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return graph;
    }
}
