/**
 * Implement GreedyMotifSearch
 */
package homework;

public class BA2D {
	
	public static void main(String[] args) {
		
		int k = 3, t = 5;
		String[] s = new String[] {
				"GGCGTTCAGGCA", "AAGAATCAGTCA", 
				"CAAGGAGTTCGC", "CACGTCAATCAC", "CAATAATATTCG"};
		
		
		System.out.println(greedyMotifSearch(s, k, t));
		
	}

	private static String greedyMotifSearch(String[] s, int k, int t) {
		
		double[][] profile = new double[4][k];
		String[] bestMotifs = new String[t];
		for (int i = 0; i < bestMotifs.length; i++) {
			bestMotifs[i] = s[i].substring(0, k);
		}
		
			
		String[] motifs = new String[t];
		motifs[0] = s[0].substring(0, 0 + k);
		
		for (int i = 1; i + k <= s[0].length(); i++) {
			
			profile = createProfileMatrix(motifs, k, t);
			String motif = findMostProbableMotif(s[i], k, profile);
			System.out.println(i + ": String = " + s[i] + " -> MostProbableMotif: " + motif);
			motifs[i] = motif;
			
		}
		
		

		return null;
	}

	private static String findMostProbableMotif(String str, int k, double[][] profile) {

		String mostProbable;
		double max = 0.0;
		
		int shift = 0;
		for (int i = 0; i + k <= str.length(); i++) {
			
			double p = calcProbability(i, str, k, profile);
			if (p > max){
				max = p;
				shift = i;
			}
		}
		
		return str.substring(shift, shift + k);
	}

	private static double calcProbability(int shift, String str, int k, double[][] profile) {
		
		double p = 0;	
		for (int i = shift, col = 0; i < k + shift; i++, col++) {
			char c = str.charAt(i);
			
			switch (c) {
				case 'A': p += profile[0][col]; break;
				case 'C': p += profile[1][col]; break;
				case 'G': p += profile[2][col]; break;
				case 'T': p += profile[3][col]; break;
			}
		}
		
		return p;
	}
	
	//вероятно оптимизация: т.к построение профайла суть добавление данных из добавляемых
	//в motifs строк, то можно не пересчитывать всё заново, а передавать массив обратно
	//в функцию с номером строки, данные из которой нужно добавить
	private static double[][] createProfileMatrix(String[] motifs, int k, int t) {
		double[][] profile = new double[4][k];
		
		//A:0, C:1, G:2, T:3
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < motifs.length; j++) {
				if (motifs[j] == null) break;
				char c = motifs[j].charAt(i);
				switch(c){
				case 'A': profile[0][i] += 0.1; break;
				case 'C': profile[1][i] += 0.1; break;
				case 'G': profile[2][i] += 0.1; break;
				case 'T': profile[3][i] += 0.1; break;
				}
				
			}
		}
		
		
		
		return profile;
	}

	
}
