# Graph Data Structure
---


## Overview
A [graph data structure](https://en.wikipedia.org/wiki/Graph_(abstract_data_type)) with approximation algorithms for finding a minimum [dominating set](https://en.wikipedia.org/wiki/Dominating_set) and minimum connected dominating set.

This library consists of two graph data structures, DirectedGraph and UndirectedGraph, descending from a parent abstract Graph class. Both types of graphs can be taken as parameters to the methods of a static calculations class, DominatingSetCalculations, which implements approximation algorithms to find an approximate minimum dominating set and minimum connected dominating set for the input Graph. The output sets are approximations due to the minimum dominating set problem being NP-complete.

This library was designed with the intent of storing social media data from both Facebook and Twitter. This use case of approximating minimum dominating sets is to model the approximate smallest group of users among a set of connected users who would need to share information in order to reach all users in the data set.

---

## Table of Contents
* [Challenges](https://github.com/carrollsa/Graph/blob/master/README.md#challenges)
* [Installation](https://github.com/carrollsa/Graph/blob/master/README.md#installation)
* [Examples](https://github.com/carrollsa/Graph/blob/master/README.md#examples)
	- [Unconnected dominating set](https://github.com/carrollsa/Graph/blob/master/README.md#find-ds-directed)
	- [Connected dominating set](https://github.com/carrollsa/Graph/blob/master/README.md#find-connected)
* [API Reference](https://github.com/carrollsa/Graph/blob/master/README.md#api-reference)

---

<a name="challenges" />

## Challenges



---

<a name="examples" />

## Examples
* [Making a graph](https://github.com/carrollsa/Graph/blob/master/README.md#making-graph)
* [Finding an unconnected dominating set graph](https://github.com/carrollsa/Graph/blob/master/README.md#find-unconnected)
* [Finding a connected dominating set of a graph](https://github.com/carrollsa/Graph/blob/master/README.md#find-connected)

<a name="making-graph" />

### Making a graph

To create a graph instance, invoke [DirectedGraph](https://github.com/carrollsa/Graph/blob/master/README.md#directed-graph) or [UndirectedGraph](https://github.com/carrollsa/Graph/blob/master/README.md#undirected-graph) as a constructor function.

```java
DirectedGraph directedGraph = new DirectedGraph();
UndirectedGraph undirectedGraph = new UndirectedGraph();
```

Add vertices with [addVertex](https://github.com/carrollsa/Graph/blob/master/README.md#add-vertex)

```java
directedGraph.addVertex(0)
directedGraph.addVertex(1)
directedGraph.addVertex(2)

undirectedGraph.addVertex(0)
undirectedGraph.addVertex(1)
undirectedGraph.addVertex(2)
```

Add edges with [addEdge](https://github.com/carrollsa/Graph/blob/master/README.md#add-edge).
```java
directedGraph.addEdge(0, 1);
directedGraph.addEdge(0, 2);

undirectedGraph.addEdge(0, 1);
undirectedGraph.addEdge(2, 0); // Ordering of edges does not matter for undirected graphs
```

Now we have the following graphs: 

<img src="https://github.com/carrollsa/carrollsa_public/blob/main/SimpleDirectedGraph.jpg">
<img src="https://github.com/carrollsa/carrollsa_public/blob/main/SimpleUndirectedGraph.jpg">

<a name="find-unconnected" />

### Finding an unconnected dominating set of a graph

Calculate an approximate minimum dominating set of either type of graph with the `greedy` method of the static class `DominatingSetCalculations`

```java
Set<Vertex> dominatingSetA = DominatingSetCalculations.greedy(directedGraph);
Set<Vertex> dominatingSetB = DominatingSetCalculations.greedy(undirectedGraph);
```

The output for both calculations would be `[0]`. The following illustrates these sets:
 
<img src="https://github.com/carrollsa/carrollsa_public/blob/main/DirectedDominatingSet.jpg">
<img src="https://github.com/carrollsa/carrollsa_public/blob/main/UndirectedDominatingSet.jpg">

<a name="find-connected" />

### Finding a connected dominating set of a graph
Use the `connectedGreedy()` method of the static class `DominatingSetCalculations`.

```java
Set<Vertex> dominatingSet = DominatingSetCalculations.connectedGreedy(graph)
```

For the following graph containing an unconnected vertex, the output would be an empty set, as a connected dominating set cannot be generated:

<img src="https://github.com/carrollsa/carrollsa_public/blob/main/SimpleUnconnectedGraph.jpg" />

For the following graph, the output would be `[1, 2, 6]`, as the set must include vertex `2` in order to be connected:

<img src="https://github.com/carrollsa/carrollsa_public/blob/main/ConnectedDominatingSet.jpg" />

---

<a name="api-reference" />

## API Reference
* ### Graph
	* [Creating a directed graph](https://github.com/carrollsa/Graph/blob/master/README.md#directed-graph)
	* [Creating an undirected graph](https://github.com/carrollsa/Graph/blob/master/README.md#undirected-graph)
	* [Adding and removing vertices](https://github.com/carrollsa/Graph/blob/master/README.md#add-vertex)
	* [Adding and removing edges](https://github.com/carrollsa/Graph/blob/master/README.md#add-edge)
	* [Querying the graph](https://github.com/carrollsa/Graph/blob/master/README.md#graph-querying)
	* [Cloning a graph](https://github.com/carrollsa/Graph/blob/master/README.md#clone)
* ### DominatingSetCalculations
	* [Generating an approximate unconnected dominating set](https://github.com/carrollsa/Graph/blob/master/README.md#greedy)
	* [Generating an approximate connected dominating set](https://github.com/carrollsa/Graph/blob/master/README.md#connected-greedy)
	* [Verifying the validity of a dominating set](https://github.com/carrollsa/Graph/blob/master/README.md#verify)
	
### Graph

#### Creating a directed graph

<a name="directed-graph" href="#directed-graph">#</a> <i>DirectedGraph</i><b></b>()

Constructs a DirectedGraph instance.

#### Creating an undirected graph

<a name="undirected-graph" href="#undirected-graph">#</a> <i>UndirectedGraph</i><b></b>()

Constructs an UndirectedGraph instance.

### Adding and removing vertices

#### Adding a vertex

<a name="add-vertex" href="#add-vertex">#</a> <i>graph</i>.<b>addVertex</b>(<i>vertex</i>)

Adds a vertex of value <i>vertex</i>. Throws an exception if a vertex of value <i>vertex</i> already exists.

#### Removing a vertex

<a name="remove-vertex" href="#add-vertex">#</a> <i>graph</i>.<b>removeVertex</b>(<i>vertex</i>)

Removes the vertex with value <i>vertex</i>. Throws an exception if a vertex of value <i>vertex</i> does not exist.

#### Adding an edge

<a name="add-edge" href="#add-edge">#</a> <i>graph</i>.<b>addEdge</b>(<i>u, v</i>)

Adds an edge between vertices <i>u</i> and <i>v</i>. The direction of the edge will only be stored if the calling Graph is a <i>DirectedGraph</i> instance. Throws an exception if either vertex <i>u</i> or vertex <i>v</i> already exists.

#### Removing an edge

<a name="remove-edge" href="#remove-edge">#</a> <i>graph</i>.<b>removeEdge</b>(<i>u, v</i>)

Removes the edge between vertices <i>u</i> and <i>v</i>. The ordering of <i>u</i> and <i>v</i> are only considered if the calling Graph is a <i>DirectedGraph</i> instance. Throws an exception if the edge does not exist.

<a name="graph-querying" href="#graph-querying"/>

#### Querying the graph

<a name="vertex-map" href="#vertex-map">#</a> <i>graph</i>.<b>getVertexMap</b>()

Returns a <i>Map&lt;Integer, Vertex&gt;</i> object representing all vertices within the graph mapped from their corresponding Integer values.

<a name="edge-map" href="#edge-map">#</a> <i>graph</i>.<b>getEdgeMap</b>()

Returns a <i>Map&lt;Integer, Set&lt;Edge&gt;&gt;</i> object representing all edges within the graph.

In a <i>DirectedGraph</i> instance, each <i>Edge</i> reference is stored in the <i>Set</i> corresponding to the value of its origin Vertex.

In an <i>UndirectedGraph</i> instance, each <i>Edge</i> has a reference stored at value of each of its corresponding vertices.

<a name="edge-map" href="#edge-map">#</a> <i>graph</i>.<b>getNumEdges</b>()

Computes the number of edges within the <i>graph</i>.

#### Cloning a graph

<a name="clone" href="#clone">#</a> <i>graph</i>.<b>clone</b>()

Returns a new Graph instance of the caller's type with the same vertices and edges as the calling <i>graph</i>. This new graph be altered without altering the original graph.


### DominatingSetCalculations

The static DominatingSetCalculations class comes with the following three methods:

<a name="greedy" href="#greedy">#</a> <b>greedy</b>(<i>graph</i>)

Returns a Set&lt;Vertex&gt; object representing an approximate minimum dominating set for the input <i>graph</i> using a greedy algorithm.

<a name="connected-greedy" href="#connected-greedy">#</a> <b>connectedGreedy</b>(<i>graph</i>)

Returns an Optional&lt;Set&lt;Vertex&gt;&gt; object representing an approximate minimum connected dominating set for the input <i>graph</i> using a greedy algorithm. If no set can be found due to the graph being [disconnected](https://mathworld.wolfram.com/DisconnectedGraph.html#:~:text=A%20graph%20is%20said%20to,disconnected%20simple%20unlabeled%20graphs%20on), an empty Optional object is returned.

<a name="verify" href="#verify">#</a> <b>verify</b>(<i>dominatingSet</i>, <i>graph</i>)

Returns true if the input <i>Set&lt;Vertex&gt;</i> object represents a [Dominating Set](https://en.wikipedia.org/wiki/Dominating_set) for input <i>graph</i> and false if not.
