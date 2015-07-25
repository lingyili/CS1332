/**
 * Represents a single entry in our table of vertexes for solving Dijkstra's
 * 
 * @author robertwaters
 *
 */
public class TableEntry implements Comparable<TableEntry> {

	/**  the overall weight of the path we know so far */
	private int weight;
	
	/** the vertex for this entry */
	private Vertex myVertex;

	/** the immediately preceeding vertex in the shortest path */
	private Vertex parent;
	
	/** whether myVertex's shortest path has been found */
	private boolean known;

	/**
	 * Set up an initial table entry
	 * 
	 * @param vertex
	 *            a vertex  the initial vertex
	 */
	public TableEntry(Vertex vertex) {
		myVertex = vertex;
        known = false;
        parent = null;
        weight = 9999999;
	}

	@Override
	public int compareTo(TableEntry other) {
		return weight - other.getWeight();
	}
	
	@Override
	public String toString() {
		return getVertex().getName() + " Known: " + known() + " weight: " + getWeight() + " Parent: " + parent;
	}

	// Provided Methods
	public int getWeight() {
		return weight;
	}
	
	public Vertex getVertex() {
		return myVertex;
	}
	
	public boolean known() {
		return known;
	}
	
	public void setKnown(boolean val) {
		known = val;
	}
	
	public void setParent(Vertex p) {
		parent = p;
	}
    
    public void setWeight(int newWeight) {
        weight = newWeight;
    }

	
}
