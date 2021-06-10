package graph;

public class DirectedGraph extends Graph {

    @Override
    public Edge createEdge(int start, int end) {
        return new Edge(start, end);
    }

    @Override
    protected void addNeighborToVertices(Edge edge) {
        Vertex neighbor = vertices.get(edge.getB());
        vertices.get(edge.getA()).addNeighbor(neighbor);
    }

    public void removeEdge(int start, int end) {
        Edge edge = new Edge(start, end);
        confirmEdgeRemovalIsValid(edge);
        removeEdgeFromGraph(edge);
        numEdges--;
    }

    private void confirmEdgeRemovalIsValid(Edge edge) {
        confirmEdgeIsInMap(edge);
    }

    private void confirmEdgeIsInMap(Edge edge) {
        if (!edgeIsInMap(edge)) {
            throw new IllegalArgumentException(edge + " does not exist.");
        }
    }

    protected void removeNeighborReferenceFromVertices(Edge edge) {
        Vertex neighborToRemove = vertices.get(edge.getB());
        vertices.get(edge.getA()).getNeighbors().remove(neighborToRemove);
    }
}
