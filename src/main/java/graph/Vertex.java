package graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vertex {
    private int value;
    private Set<Vertex> neighbors;

    public Vertex(int value) {
        this.value = value;
        neighbors = new HashSet<>();
    }

    public void addNeighbor(Vertex neighbor) {
        neighbors.add(neighbor);
    }

    public int getValue() {
        return value;
    }

    public Set<Vertex> getNeighbors() {
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return value == vertex.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Vertex{" + value + "}";
    }
}
