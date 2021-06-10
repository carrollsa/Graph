package graph;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GraphClonerTest {

    @Nested
    @DisplayName("GraphCloner should")
    class GraphCloner {
        UndirectedGraph undirectedGraph = new UndirectedGraph();
        DirectedGraph directedGraph = new DirectedGraph();
        UndirectedGraphCloner undirectedGraphCloner;
        DirectedGraphCloner directedGraphCloner;

        @Nested
        @DisplayName("create")
        class ShouldCreate {

            @Test
            @DisplayName("an UndirectedGraph")
            void shouldCreateUndirectedGraph() {
                //given
                undirectedGraph.addVertex(1);
                undirectedGraphCloner = new UndirectedGraphCloner(undirectedGraph);

                //when
                UndirectedGraph clone = undirectedGraphCloner.clone();

                //then
                assertThat(clone).isInstanceOf(UndirectedGraph.class);
            }

            @Test
            @DisplayName("a DirectedGraph")
            void shouldCreateDirectedGraph() {
                //given
                directedGraph.addVertex(1);
                directedGraphCloner = new DirectedGraphCloner(directedGraph);

                //when
                DirectedGraph clone = directedGraphCloner.clone();

                //then
                assertThat(clone).isInstanceOf(DirectedGraph.class);
            }
        }

        @Nested
        @DisplayName("clone")
        class ShouldClone {

            @Test
            @DisplayName("all vertices in an UndirectedGraph")
            void shouldCloneAllVerticesUndirectedGraph() {
                //given
                for (int i = 0; i < 10; i++) {
                    undirectedGraph.addVertex(i);
                }
                undirectedGraphCloner = new UndirectedGraphCloner(undirectedGraph);
                UndirectedGraph clone = undirectedGraphCloner.clone();

                //when
                for(int i = 0; i < 10; i++) {
                    Vertex originalVertex = undirectedGraph.getVertexMap().get(i);
                    Vertex cloneVertex = clone.getVertexMap().get(i);

                    // then
                    assertThat(cloneVertex).isEqualTo(originalVertex);
                }
            }

            //TODO: TIM QUESTION - is this bulkier than we'd want? Is there a better way?
            @Test
            @DisplayName("all edges in an UndirectedGraph")
            void shouldCloneAllEdgesInUndirectedGraph() {
                // given
                for(int i = 0; i < 10; i++) {
                    undirectedGraph.addVertex(i);
                }
                for(int j = 0; j < 10; j++) {
                    for(int k = j; k < 10; k+=2) {
                        undirectedGraph.addEdge(j, k);
                    }
                }
                undirectedGraphCloner = new UndirectedGraphCloner(undirectedGraph);
                UndirectedGraph clone = undirectedGraphCloner.clone();
                for(Map.Entry<Integer, Set<Edge>> entry : undirectedGraph.getEdgeMap().entrySet()) {
                    int index = entry.getKey();
                    Set<Edge> originalSet = undirectedGraph.getEdgeMap().get(index);
                    Iterator<Edge> originalSetIterator = originalSet.iterator();

                    Set<Edge> cloneSet = clone.getEdgeMap().get(index);
                    Iterator<Edge> cloneSetIterator = cloneSet.iterator();
                    while(originalSetIterator.hasNext() && cloneSetIterator.hasNext()) {

                        // when
                        Edge originalEdge = originalSetIterator.next();
                        Edge cloneEdge = cloneSetIterator.next();

                        // then
                        assertThat(originalEdge).isEqualTo(cloneEdge);
                    }
                }
            }

            @Test
            @DisplayName("all vertices in a DirectedGraph")
            void shouldCloneAllVerticesInDirectedGraph() {
                //given
                for (int i = 0; i < 10; i++) {
                    directedGraph.addVertex(i);
                }
                directedGraphCloner = new DirectedGraphCloner(directedGraph);
                DirectedGraph clone = directedGraphCloner.clone();

                //when
                for(int i = 0; i < 10; i++) {
                    Vertex originalVertex = directedGraph.getVertexMap().get(i);
                    Vertex cloneVertex = clone.getVertexMap().get(i);

                    // then
                    assertThat(cloneVertex).isEqualTo(originalVertex);
                }
            }

            @Test
            @DisplayName("all edges in DirectedGraph")
            void shouldCloneAllEdgesInDirectedGraph() {
                // given
                for(int i = 0; i < 10; i++) {
                    directedGraph.addVertex(i);
                }
                for(int j = 0; j < 10; j++) {
                    for(int k = j; k < 10; k+=2) {
                        directedGraph.addEdge(j, k);
                    }
                }
                directedGraphCloner = new DirectedGraphCloner(directedGraph);
                DirectedGraph clone = directedGraphCloner.clone();
                for(Map.Entry<Integer, Set<Edge>> entry : directedGraph.getEdgeMap().entrySet()) {
                    int index = entry.getKey();
                    Set<Edge> originalSet = directedGraph.getEdgeMap().get(index);
                    Iterator<Edge> originalSetIterator = originalSet.iterator();

                    Set<Edge> cloneSet = clone.getEdgeMap().get(index);
                    Iterator<Edge> cloneSetIterator = cloneSet.iterator();
                    while(originalSetIterator.hasNext() && cloneSetIterator.hasNext()) {

                        // when
                        Edge originalEdge = originalSetIterator.next();
                        Edge cloneEdge = cloneSetIterator.next();

                        // then
                        assertThat(originalEdge).isEqualTo(cloneEdge);
                    }
                }
            }
        }
    }
}