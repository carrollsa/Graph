package graph;

public class GraphClonerFactory {
    //TODO: TIM QUESTION - This doesn't feel quite right. Because this returns type GraphCloner, the return type of
    // the method called by that cloner is also just a Graph and is not of the correct type. This works for my
    // current DominatingSet calculator, and it may be the only time I use this factory, but would it be better for
    // it to return a graph that can be typed correctly without casting?

    /**
     * Factory which returns the appropriate GraphCloner object for the input graph.
     * @param graph Input graph of type DirectedGraph or UndirectedGraph.
     * @return Returns the appropriate GraphCloner object capable of cloning the input graph.
     */
    public static GraphCloner getCloner(Graph graph) {
        confirmGraphIsNotNull(graph);
        if (graph instanceof UndirectedGraph) {
            return new UndirectedGraphCloner((UndirectedGraph) graph);
        }
        else if (graph instanceof DirectedGraph) {
            return new DirectedGraphCloner((DirectedGraph) graph);
        } else if (graph == null) {
            throw new IllegalArgumentException("graph must not be null");
        } else {
            return null;
        }
    }

    //TODO: TIM QUESTION - Is this pointless?
    private static void confirmGraphIsNotNull(Graph graph) {
        if(graph == null) {
            throw new IllegalArgumentException("GraphCloner seed cannot be null.");
        }
    }
}
