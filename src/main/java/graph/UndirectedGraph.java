package graph;

public class UndirectedGraph extends Graph<UndirectedGraph> {

    @Override
    public void removeEdge(int a, int b) {
        Edge edge = new Edge(Math.min(a, b), Math.max(a,b));
        confirmEdgeExists(edge);
        removeEdgeFromGraph(edge);
        numEdges--;
    }

    @Override
    public UndirectedGraph clone() {
        UndirectedGraph clone = new UndirectedGraph();
        addVerticesToClone(clone);
        addEdgesToClone(clone);
        return clone;
    }

    @Override
    protected Edge createEdge(int start, int end) {
        return new Edge(Math.min(start, end), Math.max(start, end));
    }

    @Override
    protected void addNeighborToVertices(Edge edge) {
        vertices.get(edge.getA()).addNeighbor(vertices.get(edge.getB()));
        vertices.get(edge.getB()).addNeighbor(vertices.get(edge.getA()));
    }


    private void confirmEdgeExists(Edge edge) {
        if (!edgeIsInMap(edge)) {
            throw new IllegalArgumentException(edge + " does not exist.");
        }
    }

    protected void removeNeighborReferenceFromVertices(Edge edge) {
        Vertex aVertex = vertices.get(edge.getA());
        Vertex bVertex = vertices.get(edge.getB());
        aVertex.getNeighbors().remove(bVertex);
        bVertex.getNeighbors().remove(aVertex);
    }
}
