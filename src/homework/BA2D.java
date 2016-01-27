/**
 * Implement GreedyMotifSearch
 */
package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class BA2D {
	
	public static void main(String[] args) {
		
		File file = new File(System.getProperty("user.dir") + "/files/greedy_motif_search_small.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String in;
		scanner.next();
		int k = Integer.valueOf(scanner.next()),
			t = Integer.valueOf(scanner.next()),
			count = 0;
		String[] s = new String[t];
		while(count < t){
			in = scanner.next();
			s[count] = in;
			++count;
		}
		
		String[] output = greedyMotifSearch(s, k, t);
		System.out.println("=========================================\nOutput:");
		for (String out : output) {
			System.out.println(out);
		}
	}

	private static String[] greedyMotifSearch(String[] s, int k, int t) {
		
		String[] motifs = new String[t];
		double[][] profile = new double[4][k];
		int curScore, bestScore;
		String[] bestMotifs = new String[t];
		for (int i = 0; i < bestMotifs.length; i++) {
			bestMotifs[i] = s[i].substring(0, k);
		}
		bestScore = calcScore(bestMotifs);
		
		for (int i = 0; i + k <= s[0].length(); i++) {
			motifs[0] = s[0].substring(i, i + k);
			System.out.println(i + " k-mer from 0 String = " + s[0] + ": " + motifs[0]);
			
			for (int j = 1; j < t; j++) {
				profile = createProfileMatrix(motifs, k, t);
				String motif = findMostProbableMotif(s[j], k, profile);
				System.out.println(j + ": String = " + s[j] + " -> MostProbableMotif: " + motif);
				motifs[j] = motif;	
			}
			
			curScore = calcScore(motifs);
			if (curScore < bestScore){ //возможно нужно <=
				bestMotifs = motifs;
				bestScore = curScore;
			}
			motifs = new String[t];
		}
		
		return bestMotifs;
	}

	private static int calcScore(String[] motifs) {

		int A = 0, C = 0, G = 0, T = 0, sum = 0, result = 0;
		for (int i = 0; i < motifs[i].length(); i++) {
			for (int j = 0; j < motifs.length; j++) {
				char c = motifs[j].charAt(i);
				switch (c) {
				case 'A': ++A; break;
				case 'C': ++C; break;
				case 'G': ++G; break;
				case 'T': ++T; break;
				}
			}
			int greater = Math.max(Math.max(Math.max(A, C), G), T);
			int multyGreater = 0;
			if (A != greater) sum += A;
				else multyGreater++;
			if (C != greater) sum += C;
				else multyGreater++;
			if (G != greater) sum += G;
				else multyGreater++;
			if (T != greater) sum += T;
				else multyGreater++;
//			sum += (A != greater ? A : 0);
//			sum += (C != greater ? C : 0);
//			sum += (G != greater ? G : 0);
//			sum += (T != greater ? T : 0);
			if (multyGreater > 1) result += greater;
			result += sum;
			A = C = G = T = sum = 0;
		}
		
		
		return result;
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
		
		//если все вероятности p будут нулевыми, то вернётся shift = 0, т.е. первый k-мер
		return str.substring(shift, shift + k);
	}

	private static double calcProbability(int shift, String str, int k, double[][] profile) {
		
		double p = 1;	
		for (int i = shift, col = 0; i < k + shift; i++, col++) {
			char c = str.charAt(i);
			switch (c) {
				case 'A': p *= profile[0][col]; break;
				case 'C': p *= profile[1][col]; break;
				case 'G': p *= profile[2][col]; break;
				case 'T': p *= profile[3][col]; break;
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
