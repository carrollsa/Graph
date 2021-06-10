package graph;

import java.util.Set;

public class UndirectedGraphCloner implements GraphCloner<UndirectedGraph> {
    private UndirectedGraph seed;
    private UndirectedGraph clone;

    public UndirectedGraphCloner(UndirectedGraph seed) {
        this.seed = seed;
        this.clone = new UndirectedGraph();
    }

    @Override
    public UndirectedGraph clone() {
        clone = new UndirectedGraph();
        addVerticesToClone();
        addEdgesToClone();
        return clone;
    }

    private void addVerticesToClone() {
        for(int i : seed.getVertexMap().keySet()) {
            clone.addVertex(i);
        }
    }

    private void addEdgesToClone() {
        for(Set<Edge> edgeSet : seed.getEdgeMap().values()) {
            addEdgesFromSetToClone(edgeSet);
        }
    }

    private void addEdgesFromSetToClone(Set<Edge> edgeSet) {
        for(Edge edge : edgeSet) {
            if(clone.getEdgeMap().get(edge.getA()) == null || !clone.getEdgeMap().get(edge.getA()).contains(edge)) {
                clone.addEdge(edge.getA(), edge.getB());
            }
        }
    }
}
