package graph;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.GraphLoader;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DominatingSetGeneratorTest {
    @Nested
    @DisplayName("DominatingSetGenerator should")
    class ShouldCreate{
        @Nested
        @DisplayName("create a greedy dominating set")
        class CreateGreedyDominatingSet {
            @Test
            @DisplayName("for an UndirectedGraph")
            void generateForUndirectedGraph() {
                // given
                UndirectedGraph undirectedGraph = new UndirectedGraph();
                for(int i = 1; i <= 6; i++) {
                    undirectedGraph.addVertex(i);
                }
                undirectedGraph.addEdge(1,2);
                undirectedGraph.addEdge(1,3);
                undirectedGraph.addEdge(1,4);
                undirectedGraph.addEdge(2,5);
                undirectedGraph.addEdge(4,3);
                undirectedGraph.addEdge(3,5);
                undirectedGraph.addEdge(5,6);

                Set<Vertex> correctSet = new HashSet<>();
                correctSet.add(new Vertex(4));
                correctSet.add(new Vertex(5));

                // when
                Set<Vertex> dominatingSet = DominatingSetCalculations.greedy(undirectedGraph);

                // then
                assertThat(dominatingSet).isEqualTo(correctSet);
            }

            @Test
            @DisplayName("for a DirectedGraph")
            void generateForDirectedGraph() {
                // given
                DirectedGraph directedGraph = new DirectedGraph();
                for(int i = 1; i <= 6; i++) {
                    directedGraph.addVertex(i);
                }
                directedGraph.addEdge(1,2);
                directedGraph.addEdge(1,3);
                directedGraph.addEdge(1,4);
                directedGraph.addEdge(2,5);
                directedGraph.addEdge(4,3);
                directedGraph.addEdge(3,5);
                directedGraph.addEdge(5,6);
                directedGraph.addEdge(5,2);

                Set<Vertex> correctSet = new HashSet<>();
                correctSet.add(new Vertex(1));
                correctSet.add(new Vertex(5));

                // when
                Set<Vertex> dominatingSet = DominatingSetCalculations.greedy(directedGraph);


                // then
                assertThat(dominatingSet).isEqualTo(correctSet);
            }
        }
        @Nested
        @DisplayName("create a connected greedy dominating set")
        class CreateConnectedGreedyDominatingSet {
            @Test
            @DisplayName("for an UndirectedGraph")
            void createConnectedGreedyUndirected() {
                // given
                UndirectedGraph undirectedGraph = new UndirectedGraph();
                GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
                Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(undirectedGraph);

                // when
                boolean underTest = DominatingSetVerifier.verify(dominatingSet, undirectedGraph);

                // then
                assertThat(underTest).isTrue();
            }

            @Test
            @DisplayName("for an DirectedGraph")
            void createConnectedGreedyDirected() {
                // given
                DirectedGraph directedGraph = new DirectedGraph();
                GraphLoader.loadGraph(directedGraph, "data/raw_graph_data/facebook_combined.txt");
                Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(directedGraph);

                // when
                boolean underTest = DominatingSetVerifier.verify(dominatingSet, directedGraph);

                // then
                assertThat(underTest).isTrue();
            }
        }

        /*
        Should return an empty set if there are isolated vertices on a directed graph when calculating a connected
        dominating set
         */
        @Test
        @DisplayName("return an empty set")
        void shouldReturnAnEmptySet() {
            // given
            DirectedGraph directedGraph = new DirectedGraph();
            GraphLoader.loadGraph(directedGraph, "data/raw_graph_data/facebook_combined.txt");
            directedGraph.addVertex(5000);
            Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(directedGraph);

            // when
            int underTest = dominatingSet.size();

            // then
            assertThat(underTest).isEqualTo(0);
        }
    }

    @Disabled
    @Nested
    @DisplayName("Speed tests")
    class SpeedTests{
        @Test
        @DisplayName("1Newest implementation that can work on unconnected graphs1")
        void unconnectedDirectedSpeedTest1() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(undirectedGraph);
        }
        @Test
        @DisplayName("2Newest implementation that can work on unconnected graphs2")
        void unconnectedDirectedSpeedTest2() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(undirectedGraph);
        }
        @Test
        @DisplayName("1Original Implementation that checks for graph connectedness1")
        void connectedDSTest1() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = OriginalDominatingSetCalculations.connectedGreedy(undirectedGraph);
        }


        @Test
        @DisplayName("2Original Implementation that checks for graph connectedness2")
        void connectedDSTest2() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = OriginalDominatingSetCalculations.connectedGreedy(undirectedGraph);
        }
        @Test
        @DisplayName("3Newest implementation that can work on unconnected graphs3")
        void unconnectedDirectedSpeedTest3() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(undirectedGraph);
        }
        @Test
        @DisplayName("3Original Implementation that checks for graph connectedness3")
        void connectedDSTest3() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = OriginalDominatingSetCalculations.connectedGreedy(undirectedGraph);
        }
        @Test
        @DisplayName("Connected Directed DS")
        void connectedDirectedDS() {
            DirectedGraph directedGraph = new DirectedGraph();
            GraphLoader.loadGraph(directedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(directedGraph);
        }
        @Test
        @DisplayName("Simple design speed test")
        void speedTest() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = SimplerSlowerDominatingSetCalculations.greedy(undirectedGraph);

            DirectedGraph directedGraph = new DirectedGraph();
            GraphLoader.loadGraph(directedGraph, "data/raw_graph_data/facebook_combined.txt");
            dominatingSet = OriginalDominatingSetCalculations.greedy(directedGraph);
        }

        @Test
        @DisplayName("Original design speed test")
        void speedTestOld() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = OriginalDominatingSetCalculations.greedy(undirectedGraph);

            DirectedGraph directedGraph = new DirectedGraph();
            GraphLoader.loadGraph(directedGraph, "data/raw_graph_data/facebook_combined.txt");
            dominatingSet = OriginalDominatingSetCalculations.greedy(directedGraph);
        }

        @Test
        @DisplayName("Current implementation speed test")
        void speedTestNew() {
            UndirectedGraph undirectedGraph = new UndirectedGraph();
            GraphLoader.loadGraph(undirectedGraph, "data/raw_graph_data/facebook_combined.txt");
            Set<Vertex> dominatingSet = DominatingSetCalculations.greedy(undirectedGraph);

            DirectedGraph directedGraph = new DirectedGraph();
            GraphLoader.loadGraph(directedGraph, "data/raw_graph_data/facebook_combined.txt");
            dominatingSet = DominatingSetCalculations.greedy(directedGraph);
        }
    }
}