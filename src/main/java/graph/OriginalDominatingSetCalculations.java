package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Set to be deleted. Kept around for speed test comparison. Newest version under the name DominatingSetCalculations
 * is fully functional.
 */

public class OriginalDominatingSetCalculations {
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
        private Set<Vertex> dominatingSet;
        private Set<Vertex> coveredVertices;

        public DominatingSetGenerator(Graph seed) {
            this.graph = GraphClonerFactory.getCloner(seed).clone();
            largestOutDegree = 0;
            mostConnectedVertex = new Vertex(0);
            dominatingSet = new HashSet<>();
            coveredVertices = new HashSet<>();
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
        public Set<Vertex> generateConnectedGreedy() {
            if(graph.isConnected()) {
                generateConnectedGreedyDominatingSet();
            }
            return dominatingSet;
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
                mostConnectedVertex = compareToMostConnectedVertex(currentVertex);
            }
        }

        private void resetMostConnectedVertex() {
            largestOutDegree = 0;
            mostConnectedVertex = new Vertex(0);
        }

        private Vertex compareToMostConnectedVertex(Vertex currentVertex) {
            int currentVertexOutDegree = currentVertex.getNeighbors().size();
            if (currentVertexOutDegree > largestOutDegree) {
                largestOutDegree = currentVertexOutDegree;
                return currentVertex;
            } else if (currentVertexOutDegree == largestOutDegree) {
                return compareValues(mostConnectedVertex, currentVertex);
            } else {
                return mostConnectedVertex;
            }
        }

        private Vertex compareValues(Vertex mostConnectedVertex, Vertex currentVertex) {
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
            while(!allVerticesCovered()) {
                addMostConnectedVertexFromCoveredVertices();
            }
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

        private boolean allVerticesCovered() {
            return coveredVertices.size() == graph.getVertexMap().size();
        }

        private void addMostConnectedVertexFromCoveredVertices() {
            resetMostConnectedVertex();
            for(Vertex currentVertex : coveredVertices) {
                compareToMostConnected(currentVertex);
            }
            addMostConnectedVertex();
        }
        //TODO: TIM QUESTION? - Brain too melted right now... are the second and third conditions able to be
        // combined? Same result from conditions being met, but the third only comes into play if the other two were
        // not met.
        private void compareToMostConnected(Vertex currentVertex) {
            int outDegreeCount = countUncoveredNeighbors(currentVertex);
            if (largestOutDegree > outDegreeCount) {
                return;
            } else if (outDegreeCount > largestOutDegree) {
                largestOutDegree = outDegreeCount;
                mostConnectedVertex = currentVertex;
            } else if (currentVertex.getValue() > mostConnectedVertex.getValue()){
                mostConnectedVertex = currentVertex;
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

        //TODO: TIM QUESTION - This seems awkward. Is there a better syntax?

        private boolean neighborHasNotBeenSeen(Vertex neighbor) {
            return !(dominatingSet.contains(neighbor.getValue()) || coveredVertices.contains(neighbor));
        }
    }


}

