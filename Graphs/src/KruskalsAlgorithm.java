import java.util.*;


/**
 * This class encapsulates Kruskal's algorithm for finding a minimum spanning tree 
 * 
 * @author robertwaters
 *
 */
public final class KruskalsAlgorithm {

	public static Collection<Edge> kruskal(EdgeList edgeList) {
		Collection<Edge> list = edgeList.getEdges();
        Collection<Edge> edgeCollection = new ArrayList<>();
        PriorityQueue<Edge> myList = new PriorityQueue<>(list.size(), new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        for (Edge e : list) {
            myList.add(e);
        }
        Set<Vertex> vertexes = edgeList.getVertexes();
        UnionFind unionFind = new UnionFind(vertexes);
        while ((vertexes.size() - 1) != edgeCollection.size()) {
            Edge edge = myList.peek();
            myList.remove(edge);
            Vertex src = edge.getSource();
            Vertex dest = edge.getDestination();
            if (unionFind.find(src) != unionFind.find(dest) || (unionFind.find(src) == -1 || unionFind.find(dest) == -1)) {
                unionFind.union(src, dest);
                edgeCollection.add(edge);
            }
        }
		return edgeCollection;
	}
	
}
