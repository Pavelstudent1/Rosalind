package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class BA6C_2 {
	
	public static void main(String[] args) {
		
		File file = new File(System.getProperty("user.dir") + "/files/ba6c_big.txt");
		List<int[][]> in34 = loadData(file);
		
		int[][] in1 = new int[][] {{1, 2, 3, 4, 5, 6}};
		int[][] in2 = new int[][] {{1, -3, -6, -5}, {2, -4}};
		
		List<List<Integer>> g1 = genomeToCircular(in1);
		List<List<Integer>> g2 = genomeToCircular(in2);
//		List<List<Integer>> g3 = genomeToCircular(in34.get(0));
//		List<List<Integer>> g4 = genomeToCircular(in34.get(1));
		
		AdjGraph ag1 = circularToGraph(g1);
		AdjGraph ag2 = circularToGraph(g2);
//		AdjGraph ag3 = circularToGraph(g3);
//		AdjGraph ag4 = circularToGraph(g4);
		
		ag1.merge(ag2);
//		ag3.merge(ag4);
		
		int cycles = findCycles(ag1);
//		int cycles = findCycles(ag3);
//		List<List<Integer>> cycles = findCycles(ag3);
		
	}


	private static List<int[][]> loadData(File file) {

		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scan.nextLine();
		List<int[][]> list = new ArrayList<>();
		
		for (int count = 0; count < 2; count++) {
			String s = scan.nextLine();
			String[] genes = s.split("\\)\\(");
			for (int i = 0; i < genes.length; i++) {
				genes[i] = genes[i].replaceAll("\\(|\\)", "");
			}
			int[][] out = new int[genes.length][];
			for (int i = 0; i < genes.length; i++) {
				String[] str = genes[i].split(" ");
				out[i] = new int[str.length];
				for (int j = 0; j < out[i].length; j++) {
					out[i][j] = Integer.valueOf(str[j]);
				}
			}
			
			list.add(out);
		}
		
		scan.close();
		return list;
	}

	private static int findCycles(AdjGraph ag) {

		List<List<Integer>> bananaCycles = new ArrayList<>();
		findBananaCicles(ag, bananaCycles);
		
		List<List<Integer>> cycles = new ArrayList<>();
		findOrdinaryCycles(ag, cycles);
		
		return bananaCycles.size() / 2 + cycles.size();
	}

	private static void findOrdinaryCycles(AdjGraph ag, List<List<Integer>> cycles) {
		
		List<Stack<Integer>> rounds = new ArrayList<>();
		for (int key : ag.graph.keySet()) {
			
			Stack<Integer> cycle = new Stack<>();
			cycle.push(key);
			findRecursive(key, key, key, ag.graph, cycle);
			if (cycle.size() > 3) rounds.add(cycle);
		}
		
		for (Stack<Integer> stack : rounds) {
			cycles.add(stack);
		}
	}
	

	private static boolean findRecursive(Integer toFind, int prev, int cur, Map<Integer, List<Integer>> g, Stack<Integer> c) {
		
		for (int i = 0; i < 2; i++) {
			int elem = g.get(cur).get(i);
			if (elem == Integer.MAX_VALUE) continue;
			if (prev == elem){
				g.get(cur).set(i, Integer.MAX_VALUE);
				continue;
			}
			if (elem == toFind){
				System.out.println("Find cycle!!!");
				return true;
			}else{
				c.push(elem);				
			}
			
			findRecursive(toFind, cur, elem, g, c);
			g.get(cur).set(i, Integer.MAX_VALUE);
			return true;
		}
		
		return true;
	}

	private static void findBananaCicles(AdjGraph ag, List<List<Integer>> bCycles) {

		for (Map.Entry<Integer, List<Integer>> e : ag.graph.entrySet()) {
			List<Integer> pair = e.getValue();
			if (pair.get(0) == pair.get(1)){
				List<Integer> banana = new ArrayList<>(
						Arrays.asList(e.getKey(), pair.get(0)));
				bCycles.add(banana);
			}
		}
		System.out.println("Find " + bCycles.size() / 2 + " banana cycles");
		
		for (List<Integer> list : bCycles) {
			ag.graph.remove(list.get(0));
			ag.graph.remove(list.get(1));
		}
	}

	private static void findSpecialsCycles(Map<Integer, List<Integer>> graph) {

		for (Map.Entry<Integer, List<Integer>> e : graph.entrySet()) {
			List<Integer> value = e.getValue();
			boolean isEqual = true;
			for (int i = 0; i < value.size(); i++) {
				if (value.get(i) == value.get(i + 1)){}
			}
		}
		
	}

	private static void findCycle(Integer v, Map<Integer, List<Integer>> g, Map<Integer, List<Boolean>> path, int remainTryings) {
		
		
	}

	private static AdjGraph circularToGraph(List<List<Integer>> g) {
		
		AdjGraph ag = new AdjGraph(g);
		return ag;
		
	}

	private static List<List<Integer>> genomeToCircular(int[][] in) {

		List<List<Integer>> g = genomeToInts(in);
		
		return g;
	}

	private static List<List<Integer>> genomeToInts(int[][] in) {
		
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < in.length; i++) {
			list.add(i, new ArrayList<>());
			for (int j = 0; j < in[i].length; j++) {
				list.get(i).addAll(createPair(in[i][j]));
			}
		}

		return list;
	}

	private static List<Integer> createPair(int i) {
		List<Integer> pair = new ArrayList<>(2);
		int num = Math.abs(i);
		if (i > 0){
			pair.add(2*num - 1);
			pair.add(2*num);
		}else{
			pair.add(2*num);
			pair.add(2*num - 1);
		}
		
		return pair;
	}

}
