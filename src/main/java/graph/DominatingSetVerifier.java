package graph;

import java.util.Set;

public class DominatingSetVerifier {
    /**
     * Returns a boolean as to whether the set given as a parameter would constitute a dominating set of the graph
     * given as a parameter.
     * @param dominatingSet The set of vertices proposed to be a dominating set of the input graph.
     * @param originalGraph The graph being tested for domination.
     * @return Returns true if the set of vertices is a dominating set and false if not.
     */
    public static boolean verify(Set<Vertex> dominatingSet, Graph originalGraph) {
        Graph dominatingSetGraph = new UndirectedGraph();
        for(Vertex vertex : dominatingSet) {
            for(Vertex neighbor : vertex.getNeighbors()) {
                if(!dominatingSetGraph.getVertexMap().values().contains(neighbor)) {
                    dominatingSetGraph.addVertex(neighbor.getValue());
                }
            }
            if(!dominatingSetGraph.getVertexMap().values().contains(vertex)) {
                dominatingSetGraph.addVertex(vertex.getValue());
            }
        }

        for(Vertex vertex : originalGraph.getVertexMap().values()) {
            if(!dominatingSetGraph.getVertexMap().values().contains(vertex)) {
                return false;
            }
        }
        return true;
    }

}
