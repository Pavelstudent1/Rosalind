package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BA6J {
	
	public static void main(String[] args) {
		
		File file = new File(System.getProperty("user.dir") + "/files/ba6j_small.txt");
		int[][] input = loadData(file); //first - edges, second - 2-break
		
		int[] edges = input[0];
		int[] twoBreak = input[1];
		GenomeGraph graph = createGenomeGraph(edges);
		
		twoBreakOnGenomeGraph(graph, twoBreak);
		Iterator<int[]> it = graph.clockwiseDetour();
		while(it.hasNext()){
			int[] p = it.next();
			System.out.print("(" + p[0] + "," + p[1] + ")");
		}
		System.out.println("\n" + graph);
	}

	private static void twoBreakOnGenomeGraph(GenomeGraph graph, int[] twoBreak) {
		
		int i1 = twoBreak[0], i2 = twoBreak[1],
			j1 = twoBreak[2], j2 = twoBreak[3];
		graph.delEdge(i1, i2);
		graph.delEdge(j1, j2);
		
		graph.addEdge(i1, j1);
		graph.addEdge(i2, j2);
		
	}

	private static GenomeGraph createGenomeGraph(int[] edges) {
		GenomeGraph graph = new GenomeGraph(edges.length + 1);
		
		int i = 0;
		for (; i < edges.length; i += 2) {
			graph.addEdge(edges[i], edges[i + 1]);
		}
		
		return graph;
	}

	private static int[][] loadData(File file) {
		
		int[][] edgesAnd2Break = new int[2][];
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scanner.nextLine();
		String line = scanner.nextLine().replaceAll("\\(|\\)|,", "");
		String[] edges = line.split(" ");
		int[] intEdges = new int[edges.length];
		for (int i = 0; i < intEdges.length; i++) {
			intEdges[i] = Integer.valueOf(edges[i]);
		}
		line = scanner.nextLine().replaceAll(",", "");
		edges = line.split(" ");
		int[] int2Breaks = new int[edges.length];
		for (int i = 0; i < int2Breaks.length; i++) {
			int2Breaks[i] = Integer.valueOf(edges[i]);
		}
		
		edgesAnd2Break[0] = intEdges;
		edgesAnd2Break[1] = int2Breaks;
		return edgesAnd2Break;
	}
}
