package graph;

import java.util.*;

public abstract class Graph {
    protected final Map<Integer,Vertex> vertices;
    protected final Map<Integer,Set<Edge>> edges;
    protected int numEdges;

    public Graph() {
        vertices = new HashMap<>();
        edges = new HashMap<>();
        numEdges = 0;
    }

    public void addVertex(int value) {
        checkVertexAdditionIsValid(value);
        createAndAddVertex(value);
    }

    public void removeVertex(int vertexValue) {
        confirmVertexRemovalIsValid(vertexValue);
        removeAllConnectedEdges(vertexValue);
        removeVertexFromGraph(vertexValue);
    }

    public void addEdge(int start, int end) {
        Edge edge = createEdge(start, end);
        confirmEdgeAdditionIsValid(edge);
        addEdgeToGraph(edge);
        numEdges++;
    }

    public abstract void removeEdge(int a, int b);

    /**
     * Method that performs a breadth first search to determine whether the graph is connected, every vertex in the
     * graph has a path to every other vertex in the graph.
     * @return returns true if the graph is connected and false if not.
     */
    public boolean isConnected() {
        return breadthFirstSearch();
    }

    public Map<Integer, Vertex> getVertexMap() {
        return vertices;
    }

    public Map<Integer, Set<Edge>> getEdgeMap() {
        return edges;
    }

    public int getNumEdges() {
        return numEdges;
    }

    protected abstract Edge createEdge(int start, int end);

    private void checkVertexAdditionIsValid(int value) {
        checkVertexDoesNotAlreadyExist(value);
        checkValueIsPositive(value);
    }

    private void checkVertexDoesNotAlreadyExist(int value) {
        if(vertexIsInMap(value)) {
            throw new IllegalArgumentException("Vertex " + value + " already exists");
        }
    }

    protected boolean vertexIsInMap(int value) {
        return vertices.containsKey(value);
    }

    private void checkValueIsPositive(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Vertex value must be positive.");
        }
    }

    private void createAndAddVertex(int value) {
        Vertex vertex = new Vertex(value);
        vertices.put(value, vertex);
    }

    private void confirmVertexRemovalIsValid(int vertexValue) {
        if(!vertexIsInMap(vertexValue)) {
            throw new IllegalArgumentException("Vertex " + vertexValue + " does not exist.");
        }
    }

    private void removeAllConnectedEdges(int vertexValue) {
        Set<Edge> edgesToRemove = new HashSet<>();
        addAllConnectedEdgesToRemovalSet(vertexValue, edgesToRemove);
        removeEdgesFromGraph(edgesToRemove);
    }

    private void addAllConnectedEdgesToRemovalSet(int vertexValue, Set<Edge> edgesToRemove) {
        Set<Edge> connectedEdges = edges.get(vertexValue);
        if (connectedEdges == null) {
            return;
        }
        edgesToRemove.addAll(connectedEdges);
    }

    private void removeEdgesFromGraph(Set<Edge> edgesToRemove) {
        for(Edge edge : edgesToRemove) {
            removeEdge(edge.getA(), edge.getB());
        }
    }

    private void removeVertexFromGraph(int vertexValue) {
        vertices.remove(vertexValue);
    }

    protected void confirmEdgeAdditionIsValid(Edge edge) {
        confirmVerticesExist(edge);
        confirmEdgeIsNotDuplicate(edge);
    }

    private void confirmEdgeIsNotDuplicate(Edge edge) {
        if(edgeIsInMap(edge)) {
            throw new IllegalArgumentException(edge + " already exists.");
        }
    }

    protected boolean edgeIsInMap(Edge edge) {
        if (edges.get(edge.getA()) == null || edges.get(edge.getB()) == null) {
            return false;
        }
        return edges.get(edge.getA()).contains(edge) || edges.get(edge.getB()).contains(edge);
    }

    protected void confirmVerticesExist(Edge edge) {
        if(!vertexIsInMap(edge.getA())) {
            throw new IllegalArgumentException(edge.getA() + " does not exist");
        }
        if(!vertexIsInMap(edge.getB())) {
            throw new IllegalArgumentException(edge.getB() + " does not exist");
        }
    }

    protected void addEdgeToGraph(Edge edge) {
        addEdgeToMap(edge);
        addNeighborToVertices(edge);
    }

    protected void addEdgeToMap(Edge edge) {
        addReferenceToMap(edge.getA(), edge);
        addReferenceToMap(edge.getB(), edge);
    }

    private void addReferenceToMap(int reference, Edge edge) {
        Set<Edge> edgeList;
        if(!edges.containsKey(reference)) {
            edgeList = new HashSet<>();
        } else {
            edgeList = edges.get(reference);
        }
        edgeList.add(edge);
        edges.put(reference, edgeList);
    }

    protected abstract void addNeighborToVertices(Edge edge);

    protected void removeEdgeFromGraph(Edge edge) {
        removeEdgeFromMap(edge);
        removeNeighborReferenceFromVertices(edge);
    }

    protected void removeEdgeFromMap(Edge edge) {
        Set<Edge> edgesAtIndexA = edges.get(edge.getA());
        Set<Edge> edgesAtIndexB = edges.get(edge.getB());
        edgesAtIndexA.remove(edge);
        edgesAtIndexB.remove(edge);
    }

    protected abstract void removeNeighborReferenceFromVertices(Edge edge);

    protected boolean breadthFirstSearch() {
        checkForEmptyGraph();
        Queue<Vertex> queue = new LinkedList<>();
        Set<Vertex> seenVertices = new HashSet<>();
        prepareQueueAndSeen(queue, seenVertices);

        return performBFS(queue, seenVertices);
    }

    private void checkForEmptyGraph() {
        if(vertices.size() == 0) {
            throw new RuntimeException("There are no vertices in the graph.");
        }
    }

    private void prepareQueueAndSeen(Queue<Vertex> queue, Set<Vertex> seenVertices) {
        Vertex firstVertex = getFirstVertex();
        queue.offer(firstVertex);
        seenVertices.add(firstVertex);

    }

    private Vertex getFirstVertex() {
        return vertices.values().stream().findFirst().orElse(null);
    }

    private boolean performBFS(Queue<Vertex> queue, Set<Vertex> seenVertices) {
        while(!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            if(allVerticesInGraphHaveBeenSeen(seenVertices)) {
                return true;
            }
            addNeighborsToQueue(vertex, seenVertices, queue);
        }
        return false;
    }
    private boolean allVerticesInGraphHaveBeenSeen(Set<Vertex> visited) {
        return visited.size() == vertices.size();
    }
    //TODO: Write test

    private void addNeighborsToQueue(Vertex vertex, Set<Vertex> seenVertices, Queue<Vertex> queue) {
        for(Vertex neighbor : vertex.getNeighbors()) {
            if(!seenVertices.contains(neighbor)) {
                seenVertices.add(neighbor);
                queue.offer(neighbor);
            }
        }
    }
}
