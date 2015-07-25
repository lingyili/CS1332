import java.util.*;


/**
 * This class implements Dijkstra's algorithm
 * 
 * @author robertwaters
 *
 */
public final class Dijkstra {

	/**
	 * 
	 * @param source  The vertex to start the algorithm from
	 * @param adjList the graph to perform the search on.
	 * @return  
	 */
	public static List<TableEntry> dijkstra(String source, AdjacencyList adjList) {
		List<TableEntry> list = new LinkedList<>();
        //get list
        Map<Vertex, Map<Vertex, Integer>> myList = adjList.getAdjList();
        Set<Vertex> vertexSet = myList.keySet();
        //set up table
        for (Vertex v : vertexSet) {
            list.add(new TableEntry(v));
        }
        //find the sourceVertex
        Vertex currentVertex = adjList.findVertexByName(source);
        //get its adjacent Node
        Map<Vertex, Integer> nextNodeMap = myList.get(currentVertex);
        //find its table in the list
        //set parameters
        TableEntry currentTable = null;
        PriorityQueue entryPriorityQueue = new PriorityQueue<>(7, new Comparator<TableEntry>() {
            @Override
            public int compare(TableEntry o1, TableEntry o2) {
                return o1.getWeight() - o2.getWeight();
            }


            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
        int knowNum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getVertex().equals(currentVertex)) {
                currentTable = list.get(i);
                list.get(i).setKnown(true);
                list.get(i).setWeight(0);
                knowNum++;
                break;
            }
        }
        while (knowNum <= list.size()) {
            //get ketset of its adjacent node
            Set<Vertex> NodeMapSet = nextNodeMap.keySet();
            //convert to a linked list
            List<Vertex> vertexList = new LinkedList<>();
            for (Vertex v : NodeMapSet) {
                vertexList.add(v);
            }
            TableEntry least = null;
            //go through the entire linked list
            for (int i = 0; i < vertexList.size(); i++) {
                //get its cost
                int cost = nextNodeMap.get(vertexList.get(i));
                //find that vertex in table
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getVertex().equals(vertexList.get(i))) {
                        if (!list.get(j).known()) {
                            //if its weight is smaller than original
                            if (list.get(j).getWeight() > (cost + currentTable.getWeight())) {
                                list.get(j).setWeight(cost + currentTable.getWeight());
                                list.get(j).setParent(currentVertex);
                                entryPriorityQueue.add(list.get(j));
                                break;
                            }
                        }
                    }

                }

            }
            currentTable = (TableEntry) entryPriorityQueue.peek();
            entryPriorityQueue.remove(currentTable);
            currentVertex = currentTable.getVertex();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getVertex().equals(currentVertex)) {
                    currentTable = list.get(i);
                    list.get(i).setKnown(true);
                    knowNum++;
                    break;
                }
            }
           nextNodeMap = myList.get(currentVertex);
        }
		return list;
	}	
}
