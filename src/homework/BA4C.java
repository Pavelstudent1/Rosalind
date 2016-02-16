package homework;

/**
 * Generate the Theoretical Spectrum of a Cyclic Peptide
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BA4C {
	
	static char[] charTable = 
			new char[] {'G','A','S','P','V','T','C','I','L','N','D','K','Q','E','M','H',
					'F','R','Y','W'};
	static int[] intTable =
			new int[] {57,71,87,97,99,101,103,113,113,114,115,128,128,129,131,137,147,
					156,163,186};
	
	public static void main(String[] args) {
		Map<Character, Integer> massTable = createMassTable();
		
		String peptide = "IAQMLFYCKVATN";
		System.out.println(generateCyclicSpectrum(peptide, massTable));
		
	}


	private static Map<Character, Integer> createMassTable() {
		Map<Character, Integer> out = new HashMap<>();
		for (int i = 0; i < charTable.length; i++) {
			out.put(charTable[i], intTable[i]);
		}
		return out;
	}


	public static String generateCyclicSpectrum(String p, Map<Character, Integer> mt) {
		List<Integer> spectrum = new ArrayList<>();
		spectrum.add(0);
		
		int[] prefixMass = new int[p.length() + 1];
		for (int i = 1; i <= p.length(); i++){
			prefixMass[i] = prefixMass[i - 1] + mt.get(p.charAt(i - 1));
		}
		
		int peptideMass = prefixMass[p.length()];
		
		for (int i = 0; i < p.length(); i++) {
			for (int j = i + 1; j <= p.length(); j++) {
				spectrum.add(prefixMass[j] - prefixMass[i]);
				if (i > 0 && j < p.length()){
					spectrum.add(peptideMass - (prefixMass[j] - prefixMass[i]));
				}
			}
		}
		
		spectrum.sort(new IntegerSort());
		return spectrum.toString();
	}
	
	private static class IntegerSort implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 < o2) return -1;
			if (o1 > o2) return 1;
			return 0;
		}
		
	}
	
}
