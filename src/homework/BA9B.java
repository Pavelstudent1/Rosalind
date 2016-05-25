package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.princeton.cs.algs4.TST;

public class BA9B {
	
	private static class Pack {
		String text;
		List<String> patterns;
		int[] result;
	}
	
	static class TrieMatching {
		
		public TrieMatching() {}

		public List<Integer> makeSearch(String text, TST<?> trie){
			long start = System.nanoTime();
			
			StringBuilder sb = new StringBuilder(text);
			List<Integer> list =  new ArrayList<Integer>();
			
			for(int i = 0; sb.length() > 0; i++){
				if ((trie.longestPrefixOf(sb.toString()).length() > 0)){
					list.add(i);
				}
				sb.deleteCharAt(0);
			}
			
			long stop = System.nanoTime();
			System.out.println("Original: " + (double)(stop - start)/1000000 + " ms.");
			
			return list;
		}
		
		
		public List<Integer> makeSearch(String text, CountingTST<?> trie){
			long start = System.nanoTime();
			
			List<Integer> list =  new ArrayList<Integer>();
			
			for(int i = 0; i < text.length(); i++){
				if (trie.optim_longestPrefixOf(text, i)){
					list.add(i);
				}
			}
			
			long stop = System.nanoTime();
			System.out.println("Opimized: " + (double)(stop - start)/1000000 + " ms.");
			
			return list;
		}
		
	}
	
	
	
	public static void main(String[] args) {
		
		Pack pack = loadData("big");
		TrieMatching matcher = new TrieMatching();
		TST<Integer> trie = new TST<>();
		CountingTST<Integer> trie2 = new CountingTST<>();
		
		for (String pattern : pack.patterns) {
			trie.put(pattern, pattern.hashCode());
			trie2.put(pattern, pattern.hashCode());
		}
		
		List<Integer> result = matcher.makeSearch(pack.text, trie);
		System.out.println(result);
		System.out.println("Result " + (checkResult(pack, result) ? "is" : "isn't") + " correct!");
		
		System.out.println();
		
		result = matcher.makeSearch(pack.text, trie2);
		System.out.println(result);
		System.out.println("Result " + (checkResult(pack, result) ? "is" : "isn't") + " correct!");
	}
	
	
	private static boolean checkResult(Pack pack, List<Integer> result) {
		int i = 0;
		for (; i < pack.result.length; i++) {
			if (result.get(i) != pack.result[i]) break;
		}
		
		return i == pack.result.length;
	}


	private static Pack loadData(String type){
		
		Pack pack = new Pack();
		
		Scanner scanner = null;
		File file = new File(System.getProperty("user.dir") + "/files/ba9b_" + type + ".txt");
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scanner.nextLine();
		pack.text = scanner.nextLine();
		pack.patterns = new ArrayList<>();
		while(!scanner.hasNext("Output")){
			pack.patterns.add(scanner.nextLine());
		}
		
		scanner.nextLine();
		String s = scanner.nextLine();
		String[] s2 = s.split(" ");
		pack.result = new int[s2.length];
		for (int i = 0; i < s2.length; i++) {
			pack.result[i] = Integer.valueOf(s2[i]);
		}
			
		return pack;
	}
	
	
}
