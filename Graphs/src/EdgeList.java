import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class represents a graph that uses an edge list for it's representation
 * 
 * @author robertwaters
 *
 */
public class EdgeList {
	
	/**
	 * The list of edges in the graph
	 */
	private Collection<Edge> edges;
    
    /**
     * The set of vertexes in the graph
     */
    private Set<Vertex> vertexes;
	
	/**
	 * Construct an EdgeList from a given file
	 * 
	 * @param filename the name of the file
	 * @throws IOException Any of the exceptions that might occur while we process the file
	 */
	public EdgeList(String filename) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(filename));
		edges = new LinkedList<>();
		vertexes = new HashSet<>();
		String line = r.readLine();
		while (line != null) {
			String[] arr = line.split(",");
			if (!vertexes.contains(arr[0])) {
				vertexes.add(new Vertex(arr[0]));
			}
			if (!vertexes.contains(arr[1])) {
				vertexes.add(new Vertex(arr[1]));
			}
			edges.add(new Edge(new Vertex(arr[0]),new Vertex(arr[1]),Integer.parseInt(arr[2])));
			line = r.readLine();
		}
	}
	
	@Override
	public String toString() {
		String toReturn = "";
		for (Edge e : edges) {
			toReturn += "(" + e.getSource().getName() + ", " + e.getDestination().getName() + ", " + e.getWeight() + ")\n";
		}
		return toReturn;
	}
	
	/**
	 * 
	 * @return all the edges in the graph
	 */
	public Collection<Edge> getEdges() {
		return edges;
	}
    
    public Set<Vertex> getVertexes() {
        return vertexes;
    }
}
