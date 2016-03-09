package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Graph;

public class BA6C_2 {
	
	public static void main(String[] args) {
		
		File file = new File(System.getProperty("user.dir") + "/files/ba6c_big.txt");
		int[][] g3 = loadData(file);
		
		int[][] in1 = new int[][] {{1, 2, 3, 4, 5, 6}};
		int[][] in2 = new int[][] {{1, -3, -6, -5}, {2, -4}};
		
		List<List<Integer>> g1 = genomeToCircular(in1);
		List<List<Integer>> g2 = genomeToCircular(in2);
		
		AdjGraph ag1 = circularToGraph(g1);
		AdjGraph ag2 = circularToGraph(g2);
		
		ag1.merge(ag2);
		System.out.println(ag1.graph.entrySet());
		
		List<List<Integer>> cycles = findCycles(ag1);
		
	}

	private static int[][] loadData(File file) {

		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scan.nextLine();
		String s = scan.nextLine();
		String[] genes = s.split("\\)\\(");
		for (int i = 0; i < genes.length; i++) {
			genes[i] = genes[i].replaceAll("\\(|\\)", "");
		}
		int[][] out = new int[genes.length][];
		for (int i = 0; i < genes.length; i++) {
			out[i] = new int[genes[i].length()];
			String[] str = genes[i].split(" ");
			for (int j = 0; j < out.length; j++) {
				out[i][j] = Integer.valueOf(str[j]);
			}
		}
		return out;
	}

	private static List<List<Integer>> findCycles(AdjGraph ag) {

		Map<Integer, List<Boolean>> pathChecker = new HashMap<>();
		int remainTryings = 0;
		for (Map.Entry<Integer, List<Integer>> e : ag.graph.entrySet()) {
			List<Boolean> bList = new ArrayList<>(e.getValue().size());
			for (int i = 0; i < e.getValue().size(); i++) {
				bList.add(i, false);
				remainTryings++;
			}
			pathChecker.put(e.getKey(), bList);
		}
		System.out.println(pathChecker.entrySet());
		
		List<List<Integer>> paths = new ArrayList<>();
		int i = 0;
		
		findSpecialsCycles(ag.graph);
		while(!ag.graph.isEmpty()){
			
		}
		
		
		
		
		return null;
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
