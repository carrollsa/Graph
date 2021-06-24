package graph;

import java.util.Map;
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
        UndirectedGraph dominatingSetGraph = new UndirectedGraph();
        for(Vertex vertex : dominatingSet) {
            for(Vertex neighbor : vertex.getNeighbors()) {
                if(!dominatingSetGraph.getVertexMap().containsValue(neighbor)) {
                    dominatingSetGraph.addVertex(neighbor.getValue());
                }
            }
            if(!dominatingSetGraph.getVertexMap().containsValue(vertex)) {
                dominatingSetGraph.addVertex(vertex.getValue());
            }
        }
        Map<Integer, Vertex> vertexMap = originalGraph.getVertexMap();
        for(Vertex vertex : vertexMap.values()) {
            if(!dominatingSetGraph.getVertexMap().containsValue(vertex)) {
                return false;
            }
        }
        return true;
    }

}
