package graph;

//TODO: TIM QUESTION - Should I rename the test methods to match the class methods they are testing? Clarity?
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class GraphTest {

    @Nested
    @DisplayName("DirectedGraph")
    class DirectedGraphTest {
        DirectedGraph directedGraph = new DirectedGraph();

        @Nested
        @DisplayName("Vertices")
        class AddAndRemoveVertices {
            @Test
            @DisplayName("should be added")
            void shouldAddAValidVertex() {
                // given
                directedGraph.addVertex(1);

                // when
                boolean expected = directedGraph.getVertexMap().containsKey(1);

                // then
                assertThat(expected).isTrue();
            }

            @Test
            @DisplayName("should be removed")
            void shouldRemoveAValidVertex() {
                // given
                directedGraph.addVertex(1);

                // when
                directedGraph.removeVertex(1);

                // then
                assertThat(directedGraph.getVertexMap().entrySet().contains(1)).isFalse();
            }

            @Test
            @DisplayName("should correspond to value key in map")
            void vertexShouldCorrespondToValueKey() {
                // given
                directedGraph.addVertex(1);

                // when
                Vertex directedVertex = new Vertex(1);

                // then
                assertThat(directedVertex).isEqualTo(directedGraph.getVertexMap().get(1));
            }

            @Nested
            @DisplayName("should throw")
            class shouldThrowErrors {
                @Test
                @DisplayName("IllegalArgumentException when duplicate is added")
                void shouldThrowDuplicateVertex() {
                    // given
                    directedGraph.addVertex(1);

                    // when
                    ThrowableAssert.ThrowingCallable callable = () ->
                            directedGraph.addVertex(1);

                    // then
                    assertThatThrownBy(callable).hasMessageContaining("Vertex 1 already exists");
                }

                @Test
                @DisplayName("IllegalArgumentException when added with negative value")
                void shouldThrowIfVertexValueIsNegative() {
                    // when
                    ThrowableAssert.ThrowingCallable callable = () ->
                            directedGraph.addVertex(-1);

                    // then
                    assertThatThrownBy(callable).hasMessageContaining("Vertex value must be positive.");
                }

                @Test
                @DisplayName("IllegalArgumentException when removed without existing")
                void shouldThrowIfRemovedWithoutExisting() {
                    // given
                    directedGraph.addVertex(1);

                    // when
                    ThrowableAssert.ThrowingCallable callable = () ->
                            directedGraph.removeVertex(2);

                    // then
                    assertThatThrownBy(callable).hasMessageContaining("Vertex " + 2 + " does not exist.");
                }
            }
            @Nested
            @DisplayName("upon removal should remove")
            class RemovingVerticesShould {
                @Nested
                @DisplayName("all connected edges")
                class RemoveConnectedEdges {
                    @Test
                    @DisplayName("from map")
                    void verticesRemoveEdgesFromMap() {
                        // given
                        DirectedGraph directedGraph = new DirectedGraph();
                        for(int i = 1; i <= 3; i++) {
                            directedGraph.addVertex(i);
                        }
                        directedGraph.addEdge(1,2);
                        directedGraph.addEdge(3,1);
                        directedGraph.addEdge(2,1);
                        directedGraph.removeVertex(1);

                        // when
                        Set<Edge> neighboringEdges = directedGraph.getEdgeMap().get(1);

                        // then
                        assertThat(neighboringEdges).isEqualTo(new HashSet<Edge>());
                    }

                    @Test
                    @DisplayName("from neighbors")
                    void verticesRemoveEdgesFromNeighbors() {
                        // given
                        DirectedGraph directedGraph = new DirectedGraph();
                        for(int i = 1; i <= 3; i++) {
                            directedGraph.addVertex(i);
                        }
                        directedGraph.addEdge(1,2);
                        directedGraph.removeVertex(1);

                        // when
                        Set<Edge> neighboringEdges = directedGraph.getEdgeMap().get(2);

                        // then
                        assertThat(neighboringEdges).isEqualTo(new HashSet<Edge>());
                    }
                }
                @Test
                @DisplayName("neighbor references from origin vertices of incoming edges")
                void shouldRemoveVertexFromNeighborSetOfNeighbors() {
                    DirectedGraph directedGraph = new DirectedGraph();
                    for (int i = 1; i <= 3; i++) {
                        directedGraph.addVertex(i);
                    }
                    directedGraph.addEdge(1, 2);
                    directedGraph.addEdge(2, 1);
                    directedGraph.removeVertex(1);

                    // when
                    Set<Vertex> neighborsOfSecondVertex = directedGraph.getVertexMap().get(2).getNeighbors();

                    // then
                    assertThat(neighborsOfSecondVertex).isEqualTo(new HashSet<Vertex>());
                }
            }
        }
        @Nested
        @DisplayName("Edges")
        class AddAndRemoveEdges {
            @Nested
            @DisplayName("should be added")
            class AddEdge {
                @Nested
                @DisplayName("to Edge Map")
                class ToEdgeMap {
                    @Test
                    @DisplayName("at A Value")
                    void ShouldAddEdgeToMapAtAValue() {
                        // given
                        directedGraph.addVertex(1);
                        directedGraph.addVertex(2);
                        Edge edge = new Edge(1, 2);
                        directedGraph.addEdge(1, 2);

                        // when
                        boolean expected = directedGraph.edges.get(1).contains(edge);

                        //then
                        assertThat(expected).isTrue();
                    }

                    @Test
                    @DisplayName("at B value")
                    void shouldAddEdgeToMapAtBValue() {
                        // given
                        directedGraph.addVertex(1);
                        directedGraph.addVertex(2);
                        Edge edge = new Edge(1, 2);
                        directedGraph.addEdge(1, 2);

                        // when
                        boolean expected = directedGraph.edges.get(2).contains(edge);

                        // then
                        assertThat(expected).isTrue();
                    }
                }
            }

            @Nested
            @DisplayName("should be removed")
            class RemoveEdge {
                @Test
                @DisplayName("from map at A value")
                void shouldRemoveEdgeFromMapAtAValue() {
                    // given
                    directedGraph.addVertex(1);
                    directedGraph.addVertex(2);
                    directedGraph.addEdge(1, 2);
                    directedGraph.removeEdge(1, 2);

                    // when
                    int edgeSetSize = directedGraph.getEdgeMap().get(1).size();

                    // then
                    assertThat(edgeSetSize).isEqualTo(0);
                }

                @Test
                @DisplayName("from map at B value")
                void shouldRemoveEdgeFromMapAtBValue() {
                    // given
                    directedGraph.addVertex(1);
                    directedGraph.addVertex(2);
                    directedGraph.addEdge(1, 2);
                    directedGraph.removeEdge(1, 2);

                    // when
                    int edgeSetSize = directedGraph.getEdgeMap().get(2).size();

                    // then
                    assertThat(edgeSetSize).isEqualTo(0);
                }
            }

            @Nested
            @DisplayName("should add neighbor references")
            class EdgeShouldAddNeighborReference{
                @Test
                @DisplayName("at origin")
                void EdgeShouldAddNeighborReferenceAtOrigin() {
                    // given
                    directedGraph.addVertex(1);
                    directedGraph.addVertex(2);
                    directedGraph.addEdge(1, 2);

                    // when
                    boolean vertexContainsNeighbor =
                            directedGraph.getVertexMap()
                                    .get(1)
                                    .getNeighbors()
                                    .contains(new Vertex(2));

                    // then
                    assertThat(vertexContainsNeighbor).isTrue();
                }
            }

            @Nested
            @DisplayName("should be counted")
            class EdgesShouldBeCounted {
                //TODO: TIM QUESTION - Is this convention for when or should it be formatted like the next test?
                @Test
                @DisplayName("when added")
                void shouldCountEdgeWhenAdded () {
                    // given
                    directedGraph.addVertex(1);
                    directedGraph.addVertex(2);

                    // when
                    directedGraph.addEdge(1, 2);

                    // then
                    assertThat(directedGraph.getNumEdges()).isEqualTo(1);
                }
                @Test
                @DisplayName("when removed")
                void shouldCountEdgeWhenRemoved () {
                    directedGraph.addVertex(1);
                    directedGraph.addVertex(2);
                    directedGraph.addEdge(1, 2);
                    directedGraph.removeEdge(1, 2);

                    // when
                    int numEdges = directedGraph.getNumEdges();

                    // then
                    assertThat(numEdges).isEqualTo(0);
                }
            }
        }
        @Nested
        @DisplayName("isConnected() method")
        class IsConnected {
            @Test
            @DisplayName("should return true if graph is connected")
            void shouldBeTrueIfGraphIsConnected() {
                // given
                for(int i = 1; i <= 4; i++) {
                    directedGraph.addVertex(i);
                }
                for(int i = 2; i <= 4; i++) {
                    directedGraph.addEdge(1, i);
                }

                // when
                boolean underTest = directedGraph.isConnected();

                // then
                assertThat(underTest).isTrue();
            }
            @Test
            @DisplayName("should return false if graph is not connected")
            void shouldBeFalseIfGraphIsNotConnected() {
                // given
                for(int i = 1; i <= 4; i++) {
                    directedGraph.addVertex(i);
                }
                directedGraph.addEdge(1,2);
                directedGraph.addEdge(1,3);
                directedGraph.addEdge(4,1);

                // when
                boolean underTest = directedGraph.isConnected();

                // then
                assertThat(underTest).isFalse();
            }
            @Test
            @DisplayName("should throw if graph is empty")
            void shouldThrowIfGraphIsEmpty() {
                // when
                ThrowableAssert.ThrowingCallable callable = () ->
                        directedGraph.isConnected();

                // then
                assertThatThrownBy(callable).hasMessageContaining("There are no vertices in the graph.");
            }
        }
    }

    @Nested
    @DisplayName("UndirectedGraph")
    class UndirectedGraphTest {
        UndirectedGraph undirectedGraph = new UndirectedGraph();

        @Nested
        @DisplayName("Vertices")
        class AddAndRemoveVertices {
            @Test
            @DisplayName("should be added")
            void ShouldAddAValidVertex() {
                // given
                undirectedGraph.addVertex(1);

                // when
                boolean expected = undirectedGraph.getVertexMap().containsKey(1);

                // then
                assertThat(expected).isTrue();
            }

            @Test
            @DisplayName("should be removed")
            void shouldRemoveAValidVertex() {
                // given
                undirectedGraph.addVertex(1);

                // when
                undirectedGraph.removeVertex(1);

                // then
                assertThat(undirectedGraph.getVertexMap().entrySet().contains(1)).isFalse();
            }

            @Test
            @DisplayName("should correspond to value key in Map")
            void vertexShouldCorrespondToValueKey() {
                // given
                undirectedGraph.addVertex(1);

                // when
                Vertex vertex = new Vertex(1);

                // then
                assertThat(vertex).isEqualTo(undirectedGraph.getVertexMap().get(1));
            }
            @Nested
            @DisplayName("should throw")
            class shouldThrowErrors {

                @Test
                @DisplayName("IllegalArgumentException when duplicate is added")
                void shouldThrowDuplicateVertex() {
                    // given
                    undirectedGraph.addVertex(1);

                    // when
                    ThrowableAssert.ThrowingCallable callable = () ->
                            undirectedGraph.addVertex(1);

                    // then
                    assertThatThrownBy(callable).hasMessageContaining("Vertex 1 already exists");
                }

                @Test
                @DisplayName("IllegalArgumentException when added with negative value")
                void shouldThrowIfVertexValueIsNegative() {
                    // when
                    ThrowableAssert.ThrowingCallable callable = () ->
                            undirectedGraph.addVertex(-1);

                    // then
                    assertThatThrownBy(callable).hasMessageContaining("Vertex value must be positive.");
                }

                @Test
                @DisplayName("IllegalArgumentException when removed without existing")
                void shouldThrowIfRemovedWithoutExisting() {
                    // given
                    undirectedGraph.addVertex(1);

                    // when
                    ThrowableAssert.ThrowingCallable callable = () ->
                            undirectedGraph.removeVertex(2);

                    // then
                    assertThatThrownBy(callable).hasMessageContaining("Vertex " + 2 + " does not exist.");
                }
            }
        }
        @Nested
        @DisplayName("Edges")
        class AddAndRemoveEdges {
            @Nested
            @DisplayName("should be added")
            class AddEdge {
                @Nested
                @DisplayName("to Edge Map")
                class ToEdgeMap{
                    @Test
                    @DisplayName("at A Value")
                    void ShouldAddEdgeToMapAtAValue() {
                        // given
                        undirectedGraph.addVertex(1);
                        undirectedGraph.addVertex(2);
                        Edge edge = new Edge(1, 2);
                        undirectedGraph.addEdge(1, 2);

                        // when
                        boolean expected = undirectedGraph.edges.get(1).contains(edge);

                        //then
                        assertThat(expected).isTrue();
                    }

                    @Test
                    @DisplayName("at B value")
                    void shouldAddEdgeToMapAtBValue() {
                        // given
                        undirectedGraph.addVertex(1);
                        undirectedGraph.addVertex(2);
                        Edge edge = new Edge(1, 2);
                        undirectedGraph.addEdge(1, 2);

                        // when
                        boolean expected = undirectedGraph.edges.get(2).contains(edge);

                        // then
                        assertThat(expected).isTrue();
                    }
                }

                @Test
                @DisplayName("with values in ascending order")
                void shouldSortValuesBeforeAddingEdge() {
                    // given
                    undirectedGraph.addVertex(1);
                    undirectedGraph.addVertex(2);
                    Edge edge = new Edge(1, 2);

                    // when
                    undirectedGraph.addEdge(2, 1);

                    //then
                    assertThat(undirectedGraph.edges.get(1).contains(edge)).isTrue();
                }
            }

            @Nested
            @DisplayName("should add neighbors to vertices")
            class AddNeighbors {
                @Test
                @DisplayName("at A value")
                void shouldAddNeighborAtAValue() {
                    // given
                    undirectedGraph.addVertex(1);
                    undirectedGraph.addVertex(2);
                    Vertex destinationVertex = undirectedGraph.getVertexMap().get(2);

                    // when
                    undirectedGraph.addEdge(1,2);

                    // then
                    assertThat(undirectedGraph.getVertexMap().get(1).getNeighbors().contains(destinationVertex));
                }

                @Test
                @DisplayName("at B value")
                void shouldAddNeighborAtBValue() {
                    // given
                    undirectedGraph.addVertex(1);
                    undirectedGraph.addVertex(2);
                    Vertex originVertex = undirectedGraph.getVertexMap().get(1);

                    // when
                    undirectedGraph.addEdge(1,2);

                    // then
                    assertThat(undirectedGraph.getVertexMap().get(2).getNeighbors().contains(originVertex));
                }
            }

            @Nested
            @DisplayName("should be removed")
            class RemoveEdge {
                @Test
                @DisplayName("from map")
                void shouldRemoveEdgeFromMapAtAValue() {
                    // given
                    undirectedGraph.addVertex(1);
                    undirectedGraph.addVertex(2);
                    undirectedGraph.addEdge(1, 2);
                    undirectedGraph.removeEdge(1, 2);

                    // when
                    int edgeSetSize = undirectedGraph.getEdgeMap().get(1).size();

                    // then
                    assertThat(edgeSetSize).isEqualTo(0);
                }
            }

            @Nested
            @DisplayName("when removed")
            class EdgesWhenRemoved {
                @Nested
                @DisplayName("should remove neighbor reference")
                class ShouldRemoveNeighborReference {
                    @Test
                    @DisplayName("from vertex at A value")
                    void shouldRemoveReferenceFromA() {
                        // given
                        undirectedGraph.addVertex(1);
                        undirectedGraph.addVertex(2);
                        undirectedGraph.addEdge(1,2);

                        // when
                        undirectedGraph.removeEdge(1,2);

                        // then
                        assertThat(undirectedGraph.getVertexMap().get(1).getNeighbors().contains(2)).isFalse();
                    }

                    @Test
                    @DisplayName("from vertex at B value")
                    void shouldRemoveReferenceFromB() {
                        // given
                        undirectedGraph.addVertex(1);
                        undirectedGraph.addVertex(2);
                        undirectedGraph.addEdge(1,2);

                        // when
                        undirectedGraph.removeEdge(1,2);

                        // then
                        assertThat(undirectedGraph.getVertexMap().get(2).getNeighbors().contains(1)).isFalse();
                    }
                }
            }

            @Nested
            @DisplayName("should be counted")
            class EdgesShouldBeCounted {
                @Test
                @DisplayName("when added")
                void shouldCountEdgeWhenAdded () {
                    // given
                    undirectedGraph.addVertex(1);
                    undirectedGraph.addVertex(2);

                    // when
                    undirectedGraph.addEdge(1, 2);

                    // then
                    assertThat(undirectedGraph.getNumEdges()).isEqualTo(1);
                }
                @Test
                @DisplayName("when removed")
                void shouldCountEdgeWhenRemoved () {
                    undirectedGraph.addVertex(1);
                    undirectedGraph.addVertex(2);
                    undirectedGraph.addEdge(1, 2);

                    // when
                    undirectedGraph.removeEdge(1, 2);

                    // then
                    assertThat(undirectedGraph.getNumEdges()).isEqualTo(0);
                }
            }
        }
        @Nested
        @DisplayName("isConnected() method")
        class IsConnected {
            @Test
            @DisplayName("should return true if graph is connected")
            void shouldBeTrueIfGraphIsConnected() {
                // given
                for(int i = 1; i <= 4; i++) {
                    undirectedGraph.addVertex(i);
                }
                undirectedGraph.addEdge(1,2);
                undirectedGraph.addEdge(1,3);
                undirectedGraph.addEdge(4,1);

                // when
                boolean underTest = undirectedGraph.isConnected();

                // then
                assertThat(underTest).isTrue();
            }
            @Test
            @DisplayName("should return false if graph is not connected")
            void shouldBeFalseIfGraphIsNotConnected() {
                // given
                for(int i = 1; i <= 4; i++) {
                    undirectedGraph.addVertex(i);
                }
                undirectedGraph.addEdge(1,2);
                undirectedGraph.addEdge(1,3);

                // when
                boolean underTest = undirectedGraph.isConnected();

                // then
                assertThat(underTest).isFalse();
            }
            @Test
            @DisplayName("should throw if graph is empty")
            void shouldThrowIfGraphIsEmpty() {
                // when
                ThrowableAssert.ThrowingCallable callable = () ->
                        undirectedGraph.isConnected();

                // then
                assertThatThrownBy(callable).hasMessageContaining("There are no vertices in the graph.");
            }
        }
    }
}