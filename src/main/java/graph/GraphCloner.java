package graph;

/**
 * Interface containing a single method, clone, which returns an exact clone of the input graph as a Graph object.
 * @param <G> The graph intended to be cloned.
 */

public interface GraphCloner<G extends Graph> {
    public G clone();
}
