package graph;

import java.util.Set;

public class Application {
    public static void main(String[] args) {
        DirectedGraph directedGraph = new DirectedGraph();
        for(int i = 1; i <= 5; i++) {
            directedGraph.addVertex(i);
        }
        directedGraph.addEdge(1,2);
        directedGraph.addEdge(2,3);
        directedGraph.addEdge(3,4);
        directedGraph.addEdge(3,5);

        Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(directedGraph);
        System.out.println(DominatingSetVerifier.verify(dominatingSet, directedGraph));
        System.out.println(dominatingSet);

    }
}
