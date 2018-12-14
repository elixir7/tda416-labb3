import java.lang.reflect.Array;
import java.util.*;

public class DirectedGraph<E extends Edge> {

	private ArrayList<E> edges;
	private int size;

	public DirectedGraph(int noOfNodes) {
		edges = new ArrayList<E>();
		this.size = noOfNodes;
	}

	/**
	 * Adds an edge to the graph.<br/>
	 * Throws <tt>exception</tt> if null is supplied.
	 * @param e The edge to add
	 * @throws NullPointerException if <tt>e</tt> is null supplied.
	 */
	public void addEdge(E e) {
		if (e == null)
			throw new NullPointerException("Can't add null as an edge");
		edges.add(e);
	}

	/**
	 * This is used for simplifying the shortestPath() method. <br/>
	 * Creates an array with LinkedLists. Each index correspond to the same node, e.g node 5 has index 5. <br />
	 * The linked list contains all edges that start from the index number.
	 * This makes it a lot easier to get outgoing edges from a specific node.
	 * E.g index 3 contains edged from the node 3. ({3,5}, {3,7}, {3,1}, {3,15})
	 * @return Array containing LinkedLists of Edges.
	 */
	private LinkedList<E>[] getEdgesByOrigin(){
		LinkedList<E>[] edgeList = new LinkedList[this.size];

		for (int i = 0; i < edgeList.length; i++){
			edgeList[i] = new LinkedList<E>();
		}

		for(E e : edges){
			edgeList[e.from].add(e);
		}
		return edgeList;
	}

	/**
	 * Finds the shortest path from <tt>A</tt> to <tt>B</tt>. <br/>
	 * Uses an implementation of Dijkstras path finding algorithm. <br/>
	 * Greedy algorithm
	 * @param origin The start node - <tt>Origin</tt>
	 * @param destination The goal node - <tt>Destination</tt>
	 * @return Iterator for the shortest path. Returns empty iterator if <tt>A</tt> = <tt>B</tt>
	 */
	public Iterator<E> shortestPath(int origin, int destination) {
		LinkedList<E> path = new LinkedList<E>();

		//Return empty path if the input is bad
		if(origin == destination){
			System.err.println("The one calling shortestPath() should make sure the parameters are not the same.");
			return path.iterator();
		}

		LinkedList<E>[] nodes = getEdgesByOrigin();
		DijkstraNode[] dNodes = (DijkstraNode[]) Array.newInstance(DijkstraNode.class, this.size);
		PriorityQueue<DijkstraNode> que = new PriorityQueue<DijkstraNode>();

		//Add all outgoing predecessor nodes from the origin to the que.
		//This is done instead of adding the origin node.
		for(E edge : nodes[origin]){
			DijkstraNode newDNode = new DijkstraNode(edge, edge.getWeight());
			dNodes[newDNode.getNode()] = newDNode;
			que.add(newDNode);
		}

		while(!que.isEmpty()){
			DijkstraNode currentNode = que.poll();

			if(currentNode.getNode() == destination){
				//Backtrack from the destination to the origin to figure out the path.
				//This is done instead of saving the path for every single node, which uses more space than needed.
				DijkstraNode node = dNodes[destination];
				while(node.getPrevNode() != origin){
					path.push(node.node);
					node = dNodes[node.getPrevNode()];
				}
				//After the while loop, node = origin but we haven't added it yet.
				path.push(node.node);
				return path.iterator();
			}

			//Get all edges from the current node
			for(E edge : nodes[currentNode.getNode()]){
				DijkstraNode newDNode = new DijkstraNode(edge, currentNode.getCost() + edge.getWeight());

				//The null check could be removed by making all dNodes have a cost of infinity.
				//We would need to loop through all dNodes then, which takes unwanted time.
				if (dNodes[newDNode.getNode()] == null || newDNode.compareTo(dNodes[newDNode.getNode()]) < 0){
					dNodes[newDNode.getNode()] = newDNode;
					que.add(newDNode);
				}
			}
		}
		//If we reach this, we have not found the destination.
		return null;
	}

	/**
	 * Finds the MST (minimum spanning tree) of the graph using a version of Kruskals algorithm. <br />
	 * Greedy algorithm
	 * @return An <tt>iterator</tt> which goes over the MST.
	 */
	public Iterator<E> minimumSpanningTree() {
		// Kruskal implementation av "Overhead bilder" Try 2.
		MergeFindSet cc = new MergeFindSet(size);
		Set mst = new HashSet();
		E u; //A node

		//Instead of creating CompareKruskalEdge we implement Comparable in the Abstract Class Edge.
		PriorityQueue<E> que = new PriorityQueue<E>(edges);

		while(cc.getnumbOfSets() > 1 &&  que.size() > 0){
			u = que.poll();

			int ucomp = cc.find(u.from);
			int vcomp = cc.find(u.to);

			if(cc.merge(ucomp, vcomp)){
				mst.add(u);
			}
		}
		return mst.iterator();
	}

	/**
	 * Prints all edges, one on each line on the form "From: int"    "To: int" <br/>
	 * Useful for debugging
	 * @return String with all edges.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Edge edge : edges)
			str.append("From: " + edge.from + "        To: " + edge.to + "        Weight: " + edge.getWeight() + "\n");
		return str.toString();
	}


	/**
	 * Class that is used for Dijkstra's Shortest Path Algorithm.
	 */
	private class DijkstraNode implements Comparable<DijkstraNode>{

		private E node;
		private double costToNode;

		private DijkstraNode(E node, double cost){
			this.node = node;
			costToNode = cost;
		}

		private int getNode() {
			return node.to;
		}

		private int getPrevNode() {
			return node.from;
		}

		private int getCost() {
			return (int) costToNode;
		}

		/**
		 * Compares the cost to this.node and
		 * @param node
		 * @return
		 */
		@Override
		public int compareTo(DijkstraNode node) {
			double diff = costToNode - node.getCost();
			return (int) Math.signum(diff);
		}
	}
}
  
