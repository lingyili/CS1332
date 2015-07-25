import java.io.IOException;
import java.util.Collection;
import java.util.List;


/**
 * A class to allow you to write drivers if you wish to test out your code
 * 
 * @author robertwaters
 *
 */
public class RunProblems {

	/**
	 * TEST YOUR CODE here (if you do not implement JUnit tests). This method is for your benefit
	 * and will not be graded.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
//		AdjacencyList adjacencyList1 = new AdjacencyList("bookgraph.txt", GraphType.UNDIRECTED);
//		List<TableEntry> tableEntries = runDijkstra(adjacencyList1, "v1");
//		for (int i = 0; i < tableEntries.size(); i++) {
//			System.out.println(tableEntries.get(i).toString());
//		}
//        System.out.println(adjacencyList1.toString());
//		System.out.println("-----------------");
//
//		AdjacencyList adjacencyList2 = new AdjacencyList("debuggraph.txt", GraphType.UNDIRECTED);
//		List<TableEntry> tableEntries2 = runDijkstra(adjacencyList2, "a");
//		for (int i = 0; i < tableEntries2.size(); i++) {
//			System.out.println(tableEntries2.get(i).toString());
//		}
//		System.out.println("-----------------");
//
//        EdgeList edgeList2 = new EdgeList("debuggraph.txt");
//        Collection<Edge> edgeCollection = runKruskal(edgeList2);
//        System.out.println(edgeCollection.toString());
//		System.out.println("-----------------");

        EdgeList edgeList1 = new EdgeList("debuggraph.txt");
        Collection<Edge> edgeCollection1 = runKruskal(edgeList1);
        for (Edge e : edgeCollection1) {
            System.out.println(e.toString());
        }
		System.out.println("---------------");
        System.out.println(edgeList1);

		EdgeList edgeList2 = new EdgeList("bookgraph.txt");
		Collection<Edge> edgeCollection2 = runKruskal(edgeList2);
		for (Edge e : edgeCollection2) {
			System.out.println(e.toString());
		}
		System.out.println("---------------");
		System.out.println(edgeList2);


	}

	/**
	 * Create an adjacency list (using the AdjacencyList constructor) from the given txtFile and run
	 * Dijkstra's algorithm with the given AdjacencyList and the given source vertex
	 * 
	 * @param source  the name of the start vertex
	 * @param list the name of the file that contains the graph information
	 * @return a map keyed by vertex that contains the shortest path to the vertex from the source
	 */
	public static List<TableEntry> runDijkstra(AdjacencyList list, String source) throws IOException {
		List<TableEntry> entries = Dijkstra.dijkstra(source, list);
		return entries;
	}

	/**
	 * run Kruskal's
	 * algorithm with that EdgeList, return the list of edges that are in the MST
	 * 
	 * @param edges graph in EdgeList format
	 * @return the edges in the MST
	 */
	public static Collection<Edge> runKruskal(EdgeList edges) throws IOException {
		Collection<Edge> edge = KruskalsAlgorithm.kruskal(edges);
		return edge;
	}
}
