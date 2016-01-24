package homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BA1E {
	
	public static void main(String[] args) {
		
		String s = "CGGACTCGACAGATGTGAAGAAATGTGAAGACTGAGTGAAGAGAAG"
				 + "AGGAAACACGACACGACATTGCGACATAATGTACGAATGTAATGTG"
				 + "CCTATGGC";
		int k = 5,
			L = 75,
			t = 4;
		
		System.out.println(sortWay(s, k, L, t));
	}

	private static String sortWay(String s, int k, int L, int t) {
		long start = System.nanoTime();
		
//		System.out.println("Genome: " + s);
		Set<String> output = new HashSet<>();
		for (int i = 0, end = s.length() - L; i <= end; i++) {
//			printL(i, L, s);
			
			List<Integer> shifts = init(L, k, i);
//			printAllKmerIn(shifts, k, s);

			ShiftsComparator cmp = new ShiftsComparator(s, k);
			shifts.sort(cmp);
//			printAllKmerIn(shifts, k, s);
			
			int howMany = 0;
			for (int j = 0, index = 0; j < shifts.size() - 1; j++) {
		
				if (cmp.compare(shifts.get(j), shifts.get(j + 1)) == 0){
					++howMany;
				}else{
					if (howMany + 1 >= t){
						index = shifts.get(j);
						output.add(s.substring(index, index + k));
					}
					howMany = 0;
					index = 0;
				}
			}
		}
		
		long stop = System.nanoTime();
		System.out.println("Time: " + (double)(stop - start)/1000000 + " ms.");
		return output.toString();
	}
	
	private static void printAllKmerIn(List<Integer> shifts, int k, String s) {

		for (Integer i : shifts) {
			for (int j = i; j < k + i; j++) {
				System.out.print(s.charAt(j));
			}
			System.out.println();
		}
	}

	private static List<Integer> init(int L, int k, int shift) {
		List<Integer> out = new ArrayList<>(L - k);
		for (int j = shift; j <= L - k + shift; j++) {
			out.add(j);
		}
		
		return out;
	}

	private static void printL(int shift, int klength, String s) {
		System.out.print("Region: ");
		for (int i = shift; i < klength + shift; i++) {
			System.out.print(s.charAt(i));
		}
		System.out.println();
	}
	
	private static void printKmer(int shift, String s, int k) {
			for (int j = shift; j < k + shift; j++) {
				System.out.print(s.charAt(j));
			}
	}
	
	static class ShiftsComparator implements Comparator<Integer> {
		private String s;
		private int klen;
		
		public ShiftsComparator(String s, int klen) {
			this.s = s;
			this.klen = klen;
		}

		@Override
		public int compare(Integer o1, Integer o2) {

			for(int i = 0; i < klen; i++){
				if (s.charAt(o1 + i) < s.charAt(o2 + i)) return -1;
				if (s.charAt(o1 + i) > s.charAt(o2 + i)) return 1;					
			}
			
//			printKmer(o1, s, klen);
//			System.out.print(" and ");
//			printKmer(o2, s, klen);
//			System.out.println();
			return 0;
		}
	}
	
	
	
}
