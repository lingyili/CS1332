import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the union find algorithm
 * 
 * @author robertwaters
 *
 */
public class UnionFind {
	Map<Vertex, Integer> map;
    int[] arr;
    int num;
	/**
	 * This should implement the make_set function of union find
	 * 
	 * @param vertexes  the list to create the disjoint sets from
	 */
	public UnionFind(Set<Vertex> vertexes) {
		map	= new HashMap<>();
		arr = new int[vertexes.size()];
		int i = 0;
		for (Vertex v : vertexes) {
			arr[i] = -1;
			map.put(v, i);
            i++;
		}
        num = vertexes.size();
	}

	/**
	 * Assume that u is a vertex. Determine if they are currently
	 * in the same component of this UnionFind structure
	 * 
	 * @param u the vertex we want to find the set for
	 * @return the name of the set that u is in
	 */
	public int find(Vertex u) {
		int index = map.get(u);
        int parentIndex = arr[index];
        while (parentIndex != -1 && arr[parentIndex] != -1) {
            parentIndex = arr[parentIndex];
        }
		return parentIndex;
	}



    /**
	 * Assume that u and v are vertices that were in the edgeList. Assume that u and v are in
	 * different components (you have already checked find). Union the component containing u and the component containing v
	 * together.
	 * 
	 * @param u
	 *            a vertex
	 * @param v
	 *            a vertex
	 */
	public void union(Vertex u, Vertex v) {
        int indexU = map.get(u);
        int indexV = map.get(v);
        if (find(u) == -1 && find(v) == -1) {
            arr[indexU] = indexV;
        } else if (find(u) == -1 && find(v) != -1) {
            int parentV = find(v);
            arr[indexU] = parentV;
        } else if (find(v) == -1 && find(u) != -1) {
            int parentU = find(u);
            arr[indexV] = parentU;
        } else {
            int parentU = find(u);
            int parentV = find(v);
            arr[parentU] = parentV;
        }
	}
}
