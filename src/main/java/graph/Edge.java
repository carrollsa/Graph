package graph;

import java.util.Objects;

/**
 * POJO storing origin and destination vertex references of edges within a graph. Current implementation does not
 * utilize weight, but the field is included for future use.
 */

public class Edge {
    private final int a;
    private final int b;
    private double weight;

    public Edge(int a, int b) {
        this.a = a;
        this.b = b;
        this.weight = 0;
    }

    public Edge(int a, int b, double weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public double weight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return a == edge.a && b == edge.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "Edge{" +
                a +
                ", " + b +
                ", " + weight +
                '}';
    }
}
