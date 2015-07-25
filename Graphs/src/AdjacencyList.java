import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AdjacencyList {

	/**
	 * This is a map (Hash Table) of vertices to their adjacent vertices and the weight associated with that edge
	 */
	private Map<Vertex, Map<Vertex, Integer>> adjList;
    private GraphType myType = GraphType.UNDIRECTED;

    /**
     * Create an adjacency list from a file
     */
	public AdjacencyList(String filename, GraphType pType) throws IOException {
		try {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			String line = r.readLine();
			adjList = new HashMap<>();
			Map<Vertex, Integer> map;
			while (line != null) {
				String[] arr = line.split(",");
				Vertex vertex1 = new Vertex(arr[0]);
				Vertex vertex2 = new Vertex(arr[1]);
				map = adjList.get(vertex1);
				if (map != null) {
					map.put(vertex2, Integer.parseInt(arr[2]));
				} else {
					Map<Vertex, Integer> newMap = new HashMap<>();
					newMap.put(vertex2,Integer.parseInt(arr[2]));
					adjList.put(vertex1, newMap);
				}
				map = adjList.get(vertex2);
				if (map != null) {
					map.put(vertex1, Integer.parseInt(arr[2]));
				} else {
					Map<Vertex, Integer> newMap = new HashMap<>();
					newMap.put(vertex1,Integer.parseInt(arr[2]));
					adjList.put(vertex2, newMap);
				}
				line = r.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String toString() {
        Set<Vertex> key = adjList.keySet();
		String toReturn = "";
        for (Vertex k : key) {
            toReturn += k.getName() + ": ";
            Map<Vertex, Integer> submap = adjList.get(k);
            Set<Vertex> subKey = submap.keySet();
            for (Vertex subV : subKey) {
                toReturn += "(" + subV.getName() + ", " + submap.get(subV) + ") ";
            }
            toReturn += "\n";
        }
        return toReturn;
	}
	
    /**
     *  Find a vertex by its name
     *  @param  name   the name of the vertex to find
     *  @return the vertex with that name (or null if none)
     */
	public Vertex findVertexByName(String name) {
        Vertex newVertex = new Vertex(name);
		if (adjList.get(newVertex) != null) {
			return newVertex;
		}
        return null;
    }
    
    /**
     * @return the number of vertexes in the graph
     */
    public int vertexCount() {
        return adjList.size();
    }
    
	public Map<Vertex, Map<Vertex, Integer>> getAdjList() {
		return adjList;
	}
}
