package homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.princeton.cs.algs4.Graph;

public class BA6C {
	
	public static void main(String[] args) {
		
		String chr1 = "(+1, +2, +3, +4, +5, +6)";
		String chr2 = "(+1, -3, -6, -5)(+2, -4)";
		
		
		Graph c1 = createGraph(chr1); 
	}

	private static Graph createGraph(String in) {

		List<List<Integer>> chr = decodeStringChromosome(in);
		int vert = decodeVertices(chr);
		
		Graph g = fillGraph(chr, vert);
		
		return g;
	}

	private static int decodeVertices(List<List<Integer>> chr) {
		
		int sum = 0;
		for (List<Integer> list : chr) {
			sum += list.size();
		}
		
		return sum;
	}

	private static Graph fillGraph(List<List<Integer>> chr, int vert) {

		Graph g = new Graph(vert + 1);
		for (List<Integer> list : chr) {
			for (int i = 0; i <= list.size(); i += 2) {
				int v = 0, w = 0;
				if (i >= list.size()){
					v = Math.abs(list.get(i - 1)) - 1;
					w = 0;
				}else{
					v = Math.abs(list.get(i)) - 1;
					w = Math.abs(list.get(i + 1)) - 1;					
				}
				g.addEdge(v, w);
			}
		}
		
		
		
		return null;
	}

	private static List<List<Integer>> decodeStringChromosome(String chr) {
		
		List<List<Integer>> chrs = new ArrayList<>();
		
		boolean filling = false, firstTime = true;
		for (int i = 0, N = 0; i < chr.length(); i++) {
			if (chr.charAt(i) == ' ') continue;
			if (chr.charAt(i) == '('){
				filling = true;
				continue;
			}
			
			
			if (filling){
				if (firstTime){
					chrs.add(N, new ArrayList<>());
					firstTime = false;
				}
				List<Integer> list = chrs.get(N);
				StringBuilder sb = new StringBuilder();
				while(chr.charAt(i) != ',' && chr.charAt(i) != ')'){
					sb.append(chr.charAt(i));
					++i;
				}
				list.add(Integer.valueOf(sb.toString()));
				if (chr.charAt(i) == ')'){
					firstTime = true;
					filling = false;
					N++;
				}
			}
		}
		
		return chrs;
	}
}
