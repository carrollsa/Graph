package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Set to be deleted:
 * Class capable of generating dominating sets using greedy algorithms that calculates vertex out degree by checking
 * every neighbor for inclusion within a set of covered vertices. It is slightly more space efficient, but a good bit
 * slower than the alternate class, which clones the input graph in order to freely remove vertices and edges from
 * consideration once they are covered by the dominating set.
 */

public class SimplerSlowerDominatingSetCalculations {
    static DominatingSetGenerator generator;

    public static Set<Vertex> greedy(Graph graph) {
        generator = new DominatingSetGenerator(graph);
        return  generator.generateGreedy();
    }

    public static Set<Vertex> connectedGreedy(Graph graph) {
        generator = new DominatingSetGenerator(graph);
        return  generator.generateConnectedGreedy();
    }
    //TODO: TIM QUESTION - Is it normal that every call to generate a DS instantiates a new generator? Also
    // coveredVertices is only used in making connected sets, so is it bad practice to new it up even if that method
    // isn't called?

    //TODO: Refactor to return a set of vertices and make most connected vertex a vertex. Improves clarity.
    private static class DominatingSetGenerator {
        private int largestOutDegree;
        private Vertex mostConnectedVertex;
        private Set<Vertex> uncoveredVertices;
        private Set<Vertex> coveredVertices;
        private Set<Vertex> dominatingSet;

        public DominatingSetGenerator(Graph graph) {
            largestOutDegree = 0;
            mostConnectedVertex = new Vertex(0);
            uncoveredVertices = new HashSet<>();
            coveredVertices = new HashSet<>();
            dominatingSet = new HashSet<>();
            addVerticesToUncoveredSet(graph);
        }

        public Set<Vertex> generateGreedy() {
            generateUnconnectedGreedyDominatingSet();
            return dominatingSet;
        }

        private void generateUnconnectedGreedyDominatingSet() {
            while(!uncoveredVertices.isEmpty()) {
                findMostConnectedVertex();
                updateSets();
            }
        }

        private void addVerticesToUncoveredSet(Graph graph) {
            uncoveredVertices.addAll(graph.getVertexMap().values());
        }

        private void findMostConnectedVertex() {
            resetMostConnectedVertex();
            for(Vertex currentVertex : uncoveredVertices) {
                compareToMostConnectedVertex(currentVertex);
            }
        }

        private void resetMostConnectedVertex() {
            largestOutDegree = 0;
            mostConnectedVertex = new Vertex(0);
        }

        private void compareToMostConnectedVertex(Vertex currentVertex) {
            int currentVertexOutDegree = countUncoveredNeighbors(currentVertex);
            if (largestOutDegree > currentVertexOutDegree) {
                return;
            } else if (currentVertexOutDegree > largestOutDegree) {
                largestOutDegree = currentVertexOutDegree;
                mostConnectedVertex = currentVertex;
            } else {
                compareValueToMostConnectedVertex(currentVertex);
            }
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
            return !(dominatingSet.contains(neighbor.getValue()) || coveredVertices.contains(neighbor));
        }

        private void compareValueToMostConnectedVertex(Vertex currentVertex) {
            if(currentVertex.getValue() > mostConnectedVertex.getValue()) {
                mostConnectedVertex = currentVertex;
            }
        }

        private void updateSets() {
            addMostConnectedVertexToDominatingSet();
            addMostConnectedVertexEgonetToCoveredVertices();
            removeMostConnectedVertexEgonetFromUncoveredVertices();
        }

        private void addMostConnectedVertexToDominatingSet() {
            dominatingSet.add(mostConnectedVertex);
        }

        private void addMostConnectedVertexEgonetToCoveredVertices() {
            coveredVertices.add(mostConnectedVertex);
            coveredVertices.addAll(mostConnectedVertex.getNeighbors());
        }

        private void removeMostConnectedVertexEgonetFromUncoveredVertices() {
            uncoveredVertices.remove(mostConnectedVertex);
            for(Vertex neighbor : mostConnectedVertex.getNeighbors()) {
                uncoveredVertices.remove(neighbor);
            }
        }

        //TODO: TIM QUESTION - Does this seem the right way to handle this? Exception seems wrong because the caller
        // may not know if the graph is connected. Returning an empty set could be confusing, but it seems the safest
        // way to signify that a connected set is not possible.

        /**
         * Generates a set of integers representing connected vertices that make up a dominating set for the
         * specified graph using a greedy algorithm.
         * @return returns the dominating set or an empty set if a connected set could not be generated due to the
         * graph being unconnected.
         */
        //TODO: TIM QUESTION - Can you think of a solution outside of adding the graph as a field again? Feels funny
        // just to be able to call isConnected on it.
        public Set<Vertex> generateConnectedGreedy() {
//            if(graph.isConnected()) {
//                generateConnectedGreedyDominatingSet();
//            }
            generateConnectedGreedyDominatingSet();
            return dominatingSet;
        }

        private void generateConnectedGreedyDominatingSet() {
            addMostConnectedVertex();
            while(!uncoveredVertices.isEmpty()) {
                addMostConnectedVertexFromCoveredVertices();
            }
        }

        private void addMostConnectedVertex() {
            findMostConnectedVertex();
            addMostConnectedVertexToDominatingSet();
            addNeighborsToCoveredVerticesSet();
        }

        private void addNeighborsToCoveredVerticesSet() {
            Set<Vertex> neighbors = mostConnectedVertex.getNeighbors();
            coveredVertices.addAll(neighbors);
        }

        private void addMostConnectedVertexFromCoveredVertices() {
            resetMostConnectedVertex();
            int outDegreeCount;
            for(Vertex currentVertex : coveredVertices) {
                outDegreeCount = countUncoveredNeighbors(currentVertex);
                compareToMostConnected(currentVertex);
            }
        }



        //TODO: TIM QUESTION - This seems awkward. Is there a better syntax?

        private void compareToMostConnected(Vertex currentVertex) {

        }
    }


}
