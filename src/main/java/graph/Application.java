package graph;

import util.GraphLoader;

import java.util.Map;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph();
//        graph.addVertex(8003822);
//        graph.addEdge(8003822, 8003822);
//        Edge edge = new Edge(8003822, 8003822);
//        Map<Integer,Set<Edge>> edgeMap = graph.getEdgeMap();
//        Set<Edge> edges = edgeMap.get(8003822);
//        edges.add(edge);
//        System.out.println(edgeMap.get(8003822));
//        if(!edges.contains(new Edge(8003822, 8003822))) {
//            System.out.println("Not here");
//            graph.addEdge(8003822, 8003822);
//        }

        GraphLoader.loadGraph(graph, "data/raw_graph_data/twitter_combined.txt");
        System.out.println(graph.getVertexMap().size());
        System.out.println(graph.getNumEdges());
        System.out.println("starting domSetCalc");
        Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(graph).get();
        System.out.println("finished domSetCalc");
        System.out.println(dominatingSet.size());
        System.out.println(DominatingSetCalculations.verify(dominatingSet, graph));
    }
}
