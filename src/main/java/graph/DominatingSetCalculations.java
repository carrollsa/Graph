package graph;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Calculation class containing three static methods. Two create dominating sets from graphs, and the third verifies
 * a proposed dominating set of vertices against a graph.
 */

public class DominatingSetCalculations {
    static DominatingSetGenerator generator;
    static DominatingSetVerifier verifier;

    public static Set<Vertex> greedy(Graph graph) {
        generator = new DominatingSetGenerator(graph);
        return  generator.generateGreedy();
    }

    public static Optional<Set<Vertex>> connectedGreedy(Graph graph) {
        generator = new DominatingSetGenerator(graph);
        return  generator.generateConnectedGreedy();
    }

    public static boolean verify(Set<Vertex> dominatingSet, Graph graph) {
        verifier = new DominatingSetVerifier(dominatingSet, graph);
        return verifier.verifySet();
    }

    private static class DominatingSetGenerator {
        private final Graph graph;
        private int largestOutDegree;
        private Vertex mostConnectedVertex;
        private final Set<Vertex> dominatingSet;
        private final Set<Vertex> coveredVertices;
        private boolean vertexHasCoveredNeighbor;

        public DominatingSetGenerator(Graph seed) {
            this.graph = seed.clone();
            largestOutDegree = 0;
            mostConnectedVertex = new Vertex(0);
            dominatingSet = new HashSet<>();
            coveredVertices = new HashSet<>();
            vertexHasCoveredNeighbor = false;
        }

        /**
         * @return returns an optional containing the dominating set if a set is successfully found, or an empty
         * optional if a connected set could not be generated due to the graph being unconnected.
         */

        public Optional<Set<Vertex>> generateConnectedGreedy() {
            if(graphIsDirectedWithIsolatedVertices()) {
                return Optional.empty();
            }
            generateConnectedGreedyDominatingSet();
            return Optional.of(dominatingSet);
        }

        /**
         * @return returns a set of vertices approximating a minimum connected dominating set for the input graph.
         */

        public Set<Vertex> generateGreedy() {
            generateUnconnectedGreedyDominatingSet();
            return dominatingSet;
        }

        private boolean graphIsDirectedWithIsolatedVertices() {
            if(graph instanceof UndirectedGraph) {
                return false;
            }
            Set<Integer> accessibleVertices = new HashSet<>();
            populateAccessibleVertices(accessibleVertices);
            return accessibleVertices.size() != graph.getVertexMap().size();
        }

        private void populateAccessibleVertices(Set<Integer> accessibleVertices) {
            Map<Integer, Set<Edge>> edgeMap = graph.getEdgeMap();
            for(Set<Edge> edges : edgeMap.values()) {
                for(Edge edge : edges) {
                    accessibleVertices.add(edge.getA());
                    accessibleVertices.add(edge.getB());
                }
            }
        }

        private void generateUnconnectedGreedyDominatingSet() {
            addEdgesFromVerticesToThemselves();
            while(!graph.getVertexMap().isEmpty()) {
                findMostConnectedVertex();
                addMostConnectedVertexToDominatingSet();
                removeMostConnectedVertexEgonetFromSeed();
            }
        }

        private void addEdgesFromVerticesToThemselves() {
            Map<Integer, Set<Vertex>> vertexMap = graph.getVertexMap();
            for(int i : vertexMap.keySet()) {
                graph.addEdge(i, i);
            }
        }

        private void findMostConnectedVertex() {
            resetMostConnectedVertex();
            Map<Integer, Vertex> vertexMap = graph.getVertexMap();
            for(Vertex currentVertex : vertexMap.values()) {
                compareToMostConnectedVertex(currentVertex);
            }
        }

        private void resetMostConnectedVertex() {
            largestOutDegree = 0;
            mostConnectedVertex = new Vertex(0);
        }

        private void compareToMostConnectedVertex(Vertex currentVertex) {
            int currentVertexOutDegree = currentVertex.getNeighbors().size();
            updateMostConnectedVertex(currentVertexOutDegree, currentVertex);
        }

        private void updateMostConnectedVertex(int currentVertexOutDegree, Vertex currentVertex) {
            if (currentVertexOutDegree > largestOutDegree) {
                largestOutDegree = currentVertexOutDegree;
                mostConnectedVertex = currentVertex;
            } else if (currentVertexOutDegree == largestOutDegree) {
                mostConnectedVertex = compareValues(currentVertex);
            }
        }

        private Vertex compareValues(Vertex currentVertex) {
            if(currentVertex.getValue() > mostConnectedVertex.getValue()) {
                return currentVertex;
            } else {
                return mostConnectedVertex;
            }
        }

        private void addMostConnectedVertexToDominatingSet() {
            dominatingSet.add(mostConnectedVertex);
        }

        private void removeMostConnectedVertexEgonetFromSeed() {
            Set<Integer> verticesToRemove = findAllNeighborsOfMostConnected();
            removeVertices(verticesToRemove);
        }

        private Set<Integer> findAllNeighborsOfMostConnected() {
            Set<Integer> verticesToRemove = new HashSet<>();
            for(Vertex neighbor : mostConnectedVertex.getNeighbors()) {
                verticesToRemove.add(neighbor.getValue());
            }
            return verticesToRemove;
        }

        private void removeVertices(Set<Integer> verticesToRemove) {
            for(int i : verticesToRemove) {
                graph.removeVertex(i);
            }
        }


        private void generateConnectedGreedyDominatingSet() {
            findMostConnectedVertex();
            addMostConnectedVertex();
            loopOverCoveredVertices();
        }

        private void addMostConnectedVertex() {
            addMostConnectedVertexToDominatingSet();
            addMostConnectedVertexEgonetToCoveredVerticesSet();
        }

        private void addMostConnectedVertexEgonetToCoveredVerticesSet() {
            Set<Vertex> neighbors = mostConnectedVertex.getNeighbors();
            coveredVertices.addAll(neighbors);
            coveredVertices.add(mostConnectedVertex);
        }

        private void loopOverCoveredVertices() {
            while(!allVerticesCovered()) {
                addMostConnectedVertexFromCoveredVertices();
            }
        }

        private boolean allVerticesCovered() {
            return coveredVertices.size() == graph.getVertexMap().size();
        }

        private void addMostConnectedVertexFromCoveredVertices() {
            resetMostConnectedVertex();
            for(Vertex currentVertex : coveredVertices) {
                compareToMostConnected(currentVertex);
            }
            if(largestOutDegree == 0) {
                divertToUncoveredVertices();
            }
            addMostConnectedVertex();
        }

        private void compareToMostConnected(Vertex currentVertex) {
            int currentVertexOutDegree = countUncoveredNeighbors(currentVertex);
            updateMostConnectedVertex(currentVertexOutDegree, currentVertex);
        }
        private int countUncoveredNeighbors(Vertex vertex) {
            int outDegreeCount = 0;
            for (Vertex neighbor : vertex.getNeighbors()) {
                if(neighborHasNotBeenSeen(neighbor)) {
                    outDegreeCount++;
                }
            }
            return outDegreeCount;
        }

        private boolean neighborHasNotBeenSeen(Vertex neighbor) {
            return !(dominatingSet.contains(neighbor) || coveredVertices.contains(neighbor));
        }

        private void divertToUncoveredVertices() {
            resetMostConnectedVertex();
            findMostConnectedUncoveredVertex();
            addMostConnectedVertex();
            loopOverCoveredVertices();
        }

        private void findMostConnectedUncoveredVertex() {
            Map<Integer, Vertex> vertexMap = graph.getVertexMap();
            for(Vertex currentVertex : vertexMap.values()) {
                processUncoveredVertex(currentVertex);
            }
        }

        private void processUncoveredVertex(Vertex currentVertex) {
            if(vertexIsAlreadyCovered(currentVertex)) {
                return;
            }
            int currentVertexOutDegree = evaluateNeighbors(currentVertex);
            if(!vertexHasCoveredNeighbor) {
                return;
            }
            updateMostConnectedVertex(currentVertexOutDegree, currentVertex);
            resetHasCoveredNeighborField();
        }

        private boolean vertexIsAlreadyCovered(Vertex currentVertex) {
            return coveredVertices.contains(currentVertex);
        }

        private int evaluateNeighbors(Vertex vertex) {
            int outDegreeCount = 0;
            for (Vertex neighbor : vertex.getNeighbors()) {
                if(neighborHasNotBeenSeen(neighbor)) {
                    outDegreeCount++;
                } else if (vertexHasCoveredNeighbor == false){
                    vertexHasCoveredNeighbor = true;
                }
            }
            return outDegreeCount;
        }

        private void resetHasCoveredNeighborField() {
            vertexHasCoveredNeighbor = false;
        }
    }

    private static class DominatingSetVerifier {
        Set<Vertex> dominatingSet;
        Graph originalGraph;
        UndirectedGraph dominatingSetGraph;

        public DominatingSetVerifier(Set<Vertex> dominatingSet, Graph originalGraph) {
            this.dominatingSet = dominatingSet;
            this.originalGraph = originalGraph;
            dominatingSetGraph = new UndirectedGraph();
        }


        public boolean verifySet() {
            buildDominatingSetGraph();
            return compareGraphs();
        }

        private void buildDominatingSetGraph() {
            for(Vertex vertex : dominatingSet) {
                Map<Integer,Vertex> originalGraphVertexMap = originalGraph.getVertexMap();
                Vertex originalGraphVertex = originalGraphVertexMap.get(vertex.getValue());
                addNeighborsToDominatingSetGraph(originalGraphVertex.getNeighbors());
                addSelfToDominatingSetGraph(vertex);
            }
        }

        private void addNeighborsToDominatingSetGraph(Set<Vertex> neighbors) {
            for(Vertex neighbor : neighbors) {
                if(!dominatingSetGraph.getVertexMap().containsValue(neighbor)) {
                    dominatingSetGraph.addVertex(neighbor.getValue());
                }
            }
        }

        private void addSelfToDominatingSetGraph(Vertex vertex) {
            if(!dominatingSetGraph.getVertexMap().containsValue(vertex)) {
                dominatingSetGraph.addVertex(vertex.getValue());
            }
        }

        private boolean compareGraphs() {
            Map<Integer, Vertex> originalVertexMap = originalGraph.getVertexMap();
            for(Vertex vertex : originalVertexMap.values()) {
                if(!dominatingSetGraph.getVertexMap().containsValue(vertex)) {
                    return false;
                }
            }
            return true;
        }
    }

}


