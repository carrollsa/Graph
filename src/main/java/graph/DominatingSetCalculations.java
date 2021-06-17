package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculation class containing two static methods for generating dominating sets.
 */

public class DominatingSetCalculations {
    static DominatingSetGenerator generator;


    public static Set<Vertex> greedy(Graph graph) {
        generator = new DominatingSetGenerator(graph);
        return  generator.generateGreedy();
    }

    //TODO: TIM QUESTION - We do not need to clone the graph for this method. Is there any elegant solution to this?
    public static Set<Vertex> connectedGreedy(Graph graph) {
        generator = new DominatingSetGenerator(graph);
        return  generator.generateConnectedGreedy();
    }
    //TODO: TIM QUESTION - Is it normal that every call to generate a DS instantiates a new generator? Also
    // coveredVertices is only used in making connected sets, so is it bad practice to new it up even if that method
    // isn't called?

    private static class DominatingSetGenerator {
        private final Graph graph;
        private int largestOutDegree;
        private Vertex mostConnectedVertex;
        private final Set<Vertex> dominatingSet;
        private final Set<Vertex> coveredVertices;
        private boolean vertexHasCoveredNeighbor;

        public DominatingSetGenerator(Graph seed) {
            this.graph = GraphClonerFactory.getCloner(seed).clone();
            largestOutDegree = 0;
            mostConnectedVertex = new Vertex(0);
            dominatingSet = new HashSet<>();
            coveredVertices = new HashSet<>();
            vertexHasCoveredNeighbor = false;
        }

        public Set<Vertex> generateGreedy() {
            generateUnconnectedGreedyDominatingSet();
            return dominatingSet;
        }

        //TODO: TIM QUESTION - Does this seem the right way to handle this? Exception seems wrong because the caller
        // may not know if the graph is connected. Returning an empty set could be confusing, but it seems the safest
        // way to signify that a connected set is not possible.
        /**
         * Generates a set of integers representing connected vertices that make up a dominating set for the
         * specified graph using a greedy algorithm. Currently only works on connected graphs.
         * @return returns the dominating set if successfully found, or an empty set if a connected set could not be
         * generated due to the graph being unconnected.
         */
        //TODO: TIM QUESTION - Why is this about 30% slower than its counterpart? The only difference I can see in
        // how these two classes process an undirected, connected graph is that this class has to initiate a boolean,
        // which is never used, and to check at the end of each full loop over the vertices whether an integer is 0.
        // Additionally, the older class has to actually run a BFS to determine that the graph is, indeed, connected.
        // I'd have thought this class would perform better.
        public Set<Vertex> generateConnectedGreedy() {
            if(graphIsDirectedWithIsolatedVertices()) {
                System.out.println("Connected greedy set cannot be generated for a directed graph with isolated " +
                        "vertices");
                return new HashSet<Vertex>();
            }
            generateConnectedGreedyDominatingSet();
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
            for(Set<Edge> edges : graph.getEdgeMap().values()) {
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
            for(int i : graph.getVertexMap().keySet()) {
                graph.addEdge(i, i);
            }
        }

        private void findMostConnectedVertex() {
            resetMostConnectedVertex();
            for(Vertex currentVertex : graph.getVertexMap().values()) {
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

        //TODO: TIM QUESTION? - Brain too melted right now... are the second and third conditions able to be
        // combined? Same result from conditions being met, but the third only comes into play if the other two were
        // not met.

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


        //TODO: TIM QUESTION - This seems awkward. Is there a better syntax?
        private boolean neighborHasNotBeenSeen(Vertex neighbor) {
            return !(dominatingSet.contains(neighbor.getValue()) || coveredVertices.contains(neighbor));
        }


        private void divertToUncoveredVertices() {
            System.out.println("Diverted");
            resetMostConnectedVertex();
            findMostConnectedUncoveredVertex();
            addMostConnectedVertex();
            loopOverCoveredVertices();
        }

        //TODO: Refactor
        private void findMostConnectedUncoveredVertex() {
            for(Vertex currentVertex : graph.getVertexMap().values()) {
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
}


