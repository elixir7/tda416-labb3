
import java.util.*;

public class DirectedGraph<E extends Edge> {

	private ArrayList<Edge> edges;
	private int size;

	public DirectedGraph(int noOfNodes) {
		edges = new ArrayList<Edge>();
		this.size = noOfNodes;
	}

	/**
	 * Adds an edge to the graph
	 * @param e
	 * @throws NullPointerException if <tt>e</tt> isn't supplied.
	 */
	public void addEdge(E e) {
		if (e == null)
			throw new NullPointerException("Can't add a null as edge");
		edges.add(e);
	}

	public Iterator<E> shortestPath(int from, int to) {
		return null;
	}

	/**
	 * Finds the MST (minimum spanning tree) of the graph using a version of Kruskals algorithm.
	 * @return An <tt>iterator</tt> which goes over the MST.
	 */
	public Iterator<E> minimumSpanningTree() {
		// Kruskal implementation av "Overhead bilder" Try 2





		//retunera en iterator för MST
		return null;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Edge edge : edges)
			str.append("From: " + edge.from + "        To: " + edge.to + "        Weight: " + edge.getWeight() + "\n");
		return str.toString();
	}
}
  
