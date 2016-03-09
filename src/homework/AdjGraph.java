package homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjGraph {
	
	Map<Integer, List<Integer>> graph;
	
	public AdjGraph() {
		graph = new HashMap<>();
	}
	
	public AdjGraph(List<List<Integer>> in){
		this();
		for (List<Integer> list : in) {
			int i = 1;
			for (; i < list.size() - 1; i += 2) {
				System.out.println(list.get(i) + " " + list.get(i + 1));
				addEdge(list.get(i), list.get(i + 1));
			}
			addEdge(list.get(i), list.get(0));
		}
	}
	
	public void merge(AdjGraph other){
		
		for (Map.Entry<Integer, List<Integer>> e : other.graph.entrySet()) {
			List<Integer> list = graph.get(e.getKey());
			if (list == null){
				graph.put(e.getKey(), e.getValue());
			}else{
				list.addAll(e.getValue());
			}
		}
		
	}
	
	public void addEdge(int v, int w){
		
		List<Integer> elem = graph.get(v);
		if (elem == null){
			List<Integer> l = new ArrayList<>();
			l.add(w);
			graph.put(v, l);
		}else{
			elem.add(w);
		}
		
		List<Integer> elem2 = graph.get(w);
		if (elem2 == null){
			List<Integer> l = new ArrayList<>();
			l.add(v);
			graph.put(w, l);
		}else{
			elem2.add(v);
		}
	}
	
}
