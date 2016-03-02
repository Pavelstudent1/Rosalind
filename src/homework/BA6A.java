package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Implement GreedySorting to Sort a Permutation by Reversals
 */

public class BA6A {
	
	public static void main(String[] args) {
		
		File file = new File(System.getProperty("user.dir") + "/files/ba6a_big.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scanner.nextLine();
		StringBuilder sb;
		List<Integer> readInput = new ArrayList<>();
		boolean readNext = true;
		while(scanner.hasNext() && readNext){
			sb = new StringBuilder(scanner.next());
			if (sb.charAt(0) == '('){
				sb.deleteCharAt(0);
			}
			if (sb.charAt(sb.length() -1) == ')'){
				sb.deleteCharAt(sb.length() - 1);
				readNext = false;
			}
			readInput.add(Integer.valueOf(sb.toString()));
		}
		
		int[] input = convertListToArray(readInput);
		
		//int[] input = new int[] {-3, 4, 1, 5, -2};
		
		List<int[]> steps = greedySorting(input);
		printSteps(steps);
		
	}

	private static int[] convertListToArray(List<Integer> list) {
		int[] output = new int[list.size()];
		
		int index = 0;
		for (int i : list) {
			output[index++] = i;
		}
		
		return output;
	}

	private static void printSteps(List<int[]> steps) {

		for (int[] step : steps) {
			System.out.print("(");
			for (int i : step) {
				System.out.print(i + " ");
			}
			System.out.println(")");
		}
		System.out.println("Moves overall: " + steps.size());
	}

	private static List<int[]> greedySorting(final int[] input) {
		//create copy of input data
		int[] seq = new int[input.length];
		System.arraycopy(input, 0, seq, 0, input.length);
		
		List<int[]> steps = new ArrayList<>();
		
		for (int i = 0, sample = 1; i < seq.length; i++, sample++) {
			
			//find needed value...
			int start = i, end = i;
			for (; end < seq.length; end++) {
				if (seq[end] == sample || seq[end] == -sample) break;
			}
			flipSequence(seq, start, end);
			
			steps.add(Arrays.copyOf(seq, seq.length));
			
			if (seq[i] < 0){
				seq[i] = -seq[i];
				steps.add(Arrays.copyOf(seq, seq.length));
			}
		}
		
		return steps;
	}

	private static void flipSequence(int[] seq, int start, int end) {
		if (start == end) {
			seq[start] = -seq[start];
			return;
		}
		
		int tmp = 0, flip_limit = (end - start) / 2;
		for (int flips = 0; flips <= flip_limit; flips++, start++, end--) {
			tmp = seq[start];
			seq[start] = -seq[end];
			seq[end] = -tmp;
		}
		
		if (start == end) {
			seq[start] = -seq[start];
		}
		
	}
}
