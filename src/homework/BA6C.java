package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import edu.princeton.cs.algs4.Graph;

public class BA6C {
	
	public static void main(String[] args) {
		
		File file = new File(System.getProperty("user.dir") + "/files/ba6c_small.txt");
		List<int[][]> in12 = loadData(file);
		
		file = new File(System.getProperty("user.dir") + "/files/ba6c_big.txt");
		List<int[][]> in34 = loadData(file);
		
		int[][] in1 = new int[][] {{1, 2, 3, 4, 5, 6}};
		int[][] in2 = new int[][] {{1, -3, -6, -5}, {2, -4}};
		
		int[][] geneAsInts1 = convertGeneToInts(in1);
		int[][] geneAsInts2 = convertGeneToInts(in2);
		
		int[][] geneAsInts3 = convertGeneToInts(in34.get(0));
		int[][] geneAsInts4 = convertGeneToInts(in34.get(1));
		
		Graph g1 = createUndirectedGraph(geneAsInts1);
		Graph g2 = createUndirectedGraph(geneAsInts2);
		
		Graph g3 = createUndirectedGraph(geneAsInts3);
		Graph g4 = createUndirectedGraph(geneAsInts4);
		
		List<Stack<Integer>> cycles1 = findCycles(g1, g2);
		int numberOfCycles1 = cycles1.size() / 2;
		int twoBreakDistance1 = g1.E() - numberOfCycles1;
		System.out.println("2-break distance = " + twoBreakDistance1);
		
		List<Stack<Integer>> cycles2 = findCycles(g3, g4);
		int numberOfCycles2 = cycles2.size() / 2;
		int twoBreakDistance2 = g3.E() - numberOfCycles2;
		System.out.println("2-break distance = " + twoBreakDistance2);
	}

	private static List<Stack<Integer>> findCycles(Graph g1, Graph g2) {

		boolean marked1[] = new boolean[g1.V()];
		boolean marked2[] = new boolean[g2.V()];
		
		List<Stack<Integer>> ways = new ArrayList<>();
		Stack<Integer> way = new Stack<>();
			
			boolean flip = false;
			for (int i = 1; i != g1.V(); i++) {
				if (marked1[i]){
//					System.out.println("Pass i=" + i); 
					continue;
				}else{
					marked1[i] = true;
				}
				way.push(i);
				int find = g1.adj(i).iterator().next();
				while(i != find){
					if (flip){
						marked1[find] = true;
					}else{
						marked2[find] = true;
					}
					way.push(find);
					find = (flip ? g1.adj(find).iterator().next() : g2.adj(find).iterator().next());
					flip = !flip;
				}

				ways.add(way);
				way = new Stack<>();
				flip = false;
			}
			
		
		
		return ways;
	}

	private static int[][] convertGeneToInts(int[][] in) {
		int[][] seq = new int[in.length][];
		
		for (int i = 0; i < in.length; i++) {
			
			seq[i] = new int[in[i].length * 2];
			for (int j = 0, k = 0; j < in[i].length; j++, k += 2) {
				int direction = in[i][j];
				seq[i][k] = (direction > 0 ? 2 * Math.abs(direction) - 1 : 2 * Math.abs(direction));
				seq[i][k + 1] = (direction > 0 ? 2 * Math.abs(direction) : 2 * Math.abs(direction) - 1);
			}
		}
		
		return seq;
	}

	private static Graph createUndirectedGraph(int[][] genes) {
		
		int vertices = calcVertices(genes);
		Graph graph = new Graph(vertices + 1);
		
		for (int[] gen : genes) {
			int i = 1;
			for (; i < gen.length - 1; i += 2) {
				graph.addEdge(gen[i], gen[i + 1]);
			}
			graph.addEdge(gen[i], gen[0]);
		}
		
		return graph;
	}

	private static int calcVertices(int[][] in) {
		
		int sum = 0;
		for (int[] gen : in) {
			sum += gen.length;
		}
		
		return sum;
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
}
