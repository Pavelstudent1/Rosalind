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
		int[] input = loadInputData(file);
		
		//int[] input = new int[] {-3, 4, 1, 5, -2};
		
		List<int[]> steps = greedySorting(input);
		printSteps(steps);
		
	}

	private static int[] loadInputData(File file) {
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scanner.nextLine();
		
		String data = scanner.nextLine();
		data = data.replaceAll("\\(|\\)", "");
		String[] values = data.split(" ");
		int[] output = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			output[i] = Integer.valueOf(values[i]);
		}
		
		scanner.close();
		return output;
	}

	private static List<int[]> greedySorting(final int[] input) {
		
		List<int[]> steps = new ArrayList<>();
		
		for (int pos = 0, required = 1; pos < input.length; pos++, required++) {
			
			//find position of required number
			int start = pos, end = pos;
			for (; end < input.length; end++) {
				if (input[end] == required || input[end] == -required) break;
			}
			reverseSubString(input, start, end);
			saveStep(input, steps, pos);
		}
		
		return steps;
	}

	private static void reverseSubString(int[] seq, int start, int end) {
		if (start == end) {
			seq[start] = -seq[start];
			return;
		}
		
		int tmp = 0;
		for (; start <= end; start++, end--) {
			tmp = seq[start];
			seq[start] = -seq[end];
			seq[end] = -tmp;
		}
	}
	
	private static void saveStep(int[] input, List<int[]> steps, int curPos) {
		steps.add(Arrays.copyOf(input, input.length));
		
		//value in correct position, but additional inversion are needed?
		if (input[curPos] < 0){
			input[curPos] = -input[curPos];
			steps.add(Arrays.copyOf(input, input.length));
		}
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
	
}

