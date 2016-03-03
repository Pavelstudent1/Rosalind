package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Compute the Number of Breakpoints in a Permutation
 *
 */

public class BA6B {
	
	public static void main(String[] args) {
		
		File file = new File(System.getProperty("user.dir") + "/files/ba6b_big.txt");
		int[] data = loadData(file);
		int breaks = getNumberOfBreakPoints(data);
		System.out.println("Number of breakpoints: " + breaks);
	}

	private static int[] loadData(File file) {

		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scanner.nextLine();
		
		String input = scanner.nextLine();
		input = input.replaceAll("\\(|\\)", "");
		String[] values = input.split(" ");
		
		int[] output = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			output[i] = Integer.valueOf(values[i]);
		}
		
		scanner.close();
		return output;
	}

	private static int getNumberOfBreakPoints(int[] data) {
		
		int breaksAccumulator = 0;
		breaksAccumulator += testBoundaryValues(data);
		
		for (int i = 0; i < data.length - 1; i++) {
			breaksAccumulator += isStraight(data[i], data[i + 1]);
		}
				
		return breaksAccumulator;
	}

	private static int isStraight(int current, int next) {
		return current + 1 == next ? 0 : 1;
	}

	private static int testBoundaryValues(int[] data){
		int first = (data[0] - 1 == 0 ? 0 : 1),
			last = (data[data.length - 1] + 1 == data.length + 1 ? 0 : 1);
		return first + last;
	}



}
