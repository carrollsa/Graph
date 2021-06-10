package graph;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GraphClonerFactoryTest {

    @Nested
    @DisplayName("GraphClonerFactory")
    class GraphClonerFactory {
        @Nested
        @DisplayName("should create")
        class ShouldCreateCloners {
            @Test
            @DisplayName("a DirectedGraphCloner")
            void shouldCreateDirectedGraphCloner() {
                // when
                //TODO: Why do I need the package graph2 preceding the GraphClonerFactory call?
//                DirectedGraph directedGraph = new DirectedGraph();
//                DirectedGraph directedGraph1 = graph2.GraphClonerFactory.getCloner(directedGraph).clone();
//                UndirectedGraphCloner undirectedGraphCloner = graph2.GraphClonerFactory.getCloner(directedGraph);
            }
            @Test
            @DisplayName("should create an undirectedGraphFactory")
            void shouldCreateUndirectedGraphFactory() {

            }
        }

        @Test
        @DisplayName("should throw IllegalArgumentException")
        void shouldThrowException() {

        }
    }
}