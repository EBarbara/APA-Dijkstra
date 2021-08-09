package unirio.apa.util;

import unirio.apa.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
    public static Graph loadTestFile(String filename){
        Graph graph = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            //Numero de nós na linha 0
            int numNodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);
            //Numero de arestas na linha 1
            int numEdges = Integer.parseInt(reader.readLine().split("\\s+")[1]);

            //Inicializa o grafo
            graph = new Graph(numNodes, numEdges);

            //Lixo na linha 3
            reader.readLine();
            //Arestas nas linhas 4 a numEdges
            for(int i = 0; i < numEdges; i++){
                String[] tokens = reader.readLine().split("\\s+");
                int nodeStart = Integer.parseInt(tokens[0]);
                int nodeEnd = Integer.parseInt(tokens[1]);
                int weight = Integer.parseInt(tokens[2]);
                graph.addAdjacency(nodeStart, nodeEnd, weight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static Graph loadMainFile(String filename){
        Graph graph = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            //Lixo nas linhas 1-8
            for(int i = 1; i < 9; i++){
                reader.readLine();
            }

            //Numero de nós na linha 9
            int numNodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);
            //Numero de arestas na linha 10
            int numEdges = Integer.parseInt(reader.readLine().split("\\s+")[1]);

            //Inicializa o grafo
            graph = new Graph(numNodes, numEdges);

            //Arestas nas linhas 11 a numEdges
            for(int i = 0; i < numEdges; i++){
                String[] tokens = reader.readLine().split("\\s+");
                int nodeA = Integer.parseInt(tokens[1]) - 1;
                int nodeB = Integer.parseInt(tokens[2]) - 1;
                int weight = Integer.parseInt(tokens[3]);
                graph.addAdjacency(nodeA, nodeB, weight);
                graph.addAdjacency(nodeB, nodeA, weight);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return graph;
    }
}
