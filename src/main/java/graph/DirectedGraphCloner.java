package graph;

import java.util.Set;

public class DirectedGraphCloner implements GraphCloner<DirectedGraph> {
    private DirectedGraph seed;
    private DirectedGraph clone;

    public DirectedGraphCloner(DirectedGraph seed) {
        this.seed = seed;
        this.clone = new DirectedGraph();
    }

    @Override
    public DirectedGraph clone() {
        clone = new DirectedGraph();
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
