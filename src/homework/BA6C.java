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
		List<int[][]> input1 = loadData(file);
		
		file = new File(System.getProperty("user.dir") + "/files/ba6c_big.txt");
		List<int[][]> input2 = loadData(file);
		
		int[][] geneAsInts1 = convertGeneToInts(input1.get(0));
		int[][] geneAsInts2 = convertGeneToInts(input1.get(1));
		
		Graph graphP = createUndirectedGraph(geneAsInts1);
		Graph graphQ = createUndirectedGraph(geneAsInts2);
		
		int numberOfCycles = findNumberOfCycles(graphP, graphQ);
		int twoBreakDistance = graphP.E() - numberOfCycles;
		System.out.println("2-break distance between P and Q is " + twoBreakDistance);
	}
	
	//Convert "+1" into pair "tail->head" and then to (1,2), where t = 2*x - 1, h = 2*x
	//If "-1" then pair will be (2,1)
	private static int[][] convertGeneToInts(int[][] in) {
		int[][] intSeq = new int[in.length][];
		
		for (int gene = 0; gene < in.length; gene++) {
			
			intSeq[gene] = new int[in[gene].length * 2];
			for (int element = 0, pos = 0; element < in[gene].length; element++, pos += 2) {
				int direction = in[gene][element];
				intSeq[gene][pos] = (direction > 0 ? 2 * Math.abs(direction) - 1 : 2 * Math.abs(direction));
				intSeq[gene][pos + 1] = (direction > 0 ? 2 * Math.abs(direction) : 2 * Math.abs(direction) - 1);
			}
		}
		
		return intSeq;
	}

	private static Graph createUndirectedGraph(int[][] genes) {
		
		int vertices = requiredNumberOfVertices(genes);
		Graph graph = new Graph(vertices + 1); //fill from vertices 1
		
		for (int[] gen : genes) {
			int i = 1;
			for (; i < gen.length - 1; i += 2) {
				graph.addEdge(gen[i], gen[i + 1]);
			}
			graph.addEdge(gen[i], gen[0]); //special case: bounding elements
		}
		
		return graph;
	}
	
	private static int findNumberOfCycles(Graph g1, Graph g2) {

		boolean marked1[] = new boolean[g1.V()];
		boolean marked2[] = new boolean[g2.V()];
		
		List<Stack<Integer>> listOfCycles = new ArrayList<>();
		Stack<Integer> cycle = new Stack<>();
			
			boolean changeGraph = false;
			for (int toFind = 1; toFind != g1.V(); toFind++) {
				if (marked1[toFind]){
					continue;
				}else{
					marked1[toFind] = true;
				}
				cycle.push(toFind);
				int nextVertex = g1.adj(toFind).iterator().next();
				while(toFind != nextVertex){
					marked1[nextVertex] = marked2[nextVertex] = true; //prevent doubling of cycles
					cycle.push(nextVertex);
					changeGraph = !changeGraph;
					nextVertex = (changeGraph ? 
							g2.adj(nextVertex).iterator().next() :
							g1.adj(nextVertex).iterator().next()); 
				}

				listOfCycles.add(cycle);
				cycle = new Stack<>();
				changeGraph = false;
			}
			
		return listOfCycles.size();
	}




	private static int requiredNumberOfVertices(int[][] in) {
		
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
			int[][] intGene = new int[genes.length][];
			for (int i = 0; i < genes.length; i++) {
				String[] str = genes[i].split(" ");
				intGene[i] = new int[str.length];
				for (int j = 0; j < intGene[i].length; j++) {
					intGene[i][j] = Integer.valueOf(str[j]);
				}
			}
			
			list.add(intGene);
		}
		
		scan.close();
		return list;
	}
}
