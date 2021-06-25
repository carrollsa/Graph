package graph;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class DominatingSetVerifierTest {
    @Nested
    @DisplayName("DominatingSetVerifier should")
    class DominatingSetVerifierShould{
        UndirectedGraph undirectedGraph = new UndirectedGraph();
        DirectedGraph directedGraph = new DirectedGraph();

        @Nested
        @DisplayName("verify a correct unconnected dominating set")
        class VerifyACorrectUnconnectedDominatingSet {
            @Test
            @DisplayName("for an UndirectedGraph")
            void verifyCorrectUnconnectedUndirected() {
                // given
                for(int i = 1; i <= 5; i++) {
                    undirectedGraph.addVertex(i);
                }
                undirectedGraph.addEdge(1,2);
                undirectedGraph.addEdge(1,3);
                undirectedGraph.addEdge(1,4);
                undirectedGraph.addEdge(3,5);

                // when
                Set<Vertex> dominatingSet = DominatingSetCalculations.greedy(undirectedGraph);

                // then
                assertThat(DominatingSetCalculations.verify(dominatingSet, undirectedGraph)).isTrue();
            }
            @Test
            @DisplayName("for a DirectedGraph")
            void verifyCorrectUnconnectedDirectedGraph() {
                // given
                for(int i = 1; i <= 5; i++) {
                    directedGraph.addVertex(i);
                }
                directedGraph.addEdge(1,2);
                directedGraph.addEdge(1,3);
                directedGraph.addEdge(1,4);
                directedGraph.addEdge(3,1);
                directedGraph.addEdge(3,2);
                directedGraph.addEdge(3,5);

                // when
                Set<Vertex> dominatingSet = DominatingSetCalculations.greedy(directedGraph);

                // then
                assertThat(DominatingSetCalculations.verify(dominatingSet, directedGraph)).isTrue();
            }
        }
        @Nested
        @DisplayName("verify a correct connected dominating set")
        class VerifyACorrectConnectedDominatingSet {
            @Test
            @DisplayName("for an UndirectedGraph")
            void verifyCorrectConnectedUndirected() {
                // given
                for(int i = 1; i <= 5; i++) {
                    undirectedGraph.addVertex(i);
                }
                undirectedGraph.addEdge(1,2);
                undirectedGraph.addEdge(1,3);
                undirectedGraph.addEdge(1,4);
                undirectedGraph.addEdge(3,5);

                // when
                Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(undirectedGraph).get();

                // then
                assertThat(DominatingSetCalculations.verify(dominatingSet, undirectedGraph)).isTrue();
            }
            @Test
            @DisplayName("for a DirectedGraph")
            void verifyCorrectConnectedDirectedGraph() {
                // given
                for(int i = 1; i <= 5; i++) {
                    directedGraph.addVertex(i);
                }
                directedGraph.addEdge(1,2);
                directedGraph.addEdge(1,3);
                directedGraph.addEdge(1,4);
                directedGraph.addEdge(3,1);
                directedGraph.addEdge(3,2);
                directedGraph.addEdge(3,5);

                // when
                Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(directedGraph).get();

                // then
                assertThat(DominatingSetCalculations.verify(dominatingSet, directedGraph)).isTrue();
            }
        }
        @Nested
        @DisplayName("verify an incorrect dominating set")
        class VerifyAnIncorrectDominatingSet {
            @Test
            @DisplayName("for an UndirectedGraph")
            void verifyIncorrectUndirected() {
                // given
                for(int i = 1; i <= 5; i++) {
                    undirectedGraph.addVertex(i);
                }
                undirectedGraph.addEdge(1,2);
                undirectedGraph.addEdge(1,3);
                undirectedGraph.addEdge(1,4);
                undirectedGraph.addEdge(3,5);
                Set<Vertex> dominatingSet = new HashSet<>();
                dominatingSet.add(new Vertex(5));
                dominatingSet.add(new Vertex(2));

                // when
                boolean underTest = DominatingSetCalculations.verify(dominatingSet,undirectedGraph);

                // then
                assertThat(underTest).isFalse();
            }
            @Test
            @DisplayName("for a DirectedGraph")
            void verifyIncorrectDirectedGraph() {
                // given
                for(int i = 1; i <= 5; i++) {
                    directedGraph.addVertex(i);
                }
                directedGraph.addEdge(1,2);
                directedGraph.addEdge(1,3);
                directedGraph.addEdge(1,4);
                directedGraph.addEdge(3,1);
                directedGraph.addEdge(3,2);
                directedGraph.addEdge(3,5);

                // when
                Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(directedGraph).get();
                dominatingSet.remove(new Vertex(3));

                // then
                assertThat(DominatingSetCalculations.verify(dominatingSet, directedGraph)).isFalse();
            }
        }
    }
}