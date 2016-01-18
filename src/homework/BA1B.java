/**
 * Find the Most Frequent Words in a String
 */

package homework;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BA1B {
	
	public static void main(String[] args) {
		
//		String s = args[0];
//		int l = Integer.valueOf(args[1]);
		
//		String s = "CGGAAGCGAGATTCGCGTGGCGTGATTCCGGCGGGCGTGGAGAAGCGAGATTCATTCAAGCCGGGAGGCGTGGCGT"
//				 + "GGCGTGGCGTGCGGATTCAAGCCGGCGGGCGTGATTCGAGCGGCGGATTCGAGATTCCGGGCGTGCGGGCGTGAAG"
//				 + "CGCGTGGAGGAGGCGTGGCGTGCGGGAGGAGAAGCGAGAAGCCGGATTCAAGCAAGCATTCCGGCGGGAGATTCGC"
//				 + "GTGGAGGCGTGGAGGCGTGGAGGCGTGCGGCGGGAGATTCAAGCCGGATTCGCGTGGAGAAGCGAGAAGCGCGTGC"
//				 + "GGAAGCGAGGAGGAGAAGCATTCGCGTGATTCCGGGAGATTCAAGCATTCGCGTGCGGCGGGAGATTCAAGCGAGG"
//				 + "AGGCGTGAAGCAAGCAAGCAAGCGCGTGGCGTGCGGCGGGAGAAGCAAGCGCGTGATTCGAGCGGGCGTGCGGAAG"
//				 + "CGAGCGG";
//		int l = 12;
		String s = "ACAACTATGCATCACTATCGGGAACTATCCT";
		int l = 5;
		
		System.out.println("Suffix:  " + suffixWay(s,l));
		System.out.println("Hashing: " + hashWay(s,l));
		System.out.println("TST:     " + tstWay(s,l));
	}

	private static String hashWay(String s, int l) {
		long start = System.currentTimeMillis();
		
		Map<String, Integer> map = new HashMap<>();
		String str;
		int biggest = 0;
		for(int i = 0; i + l <= s.length(); i++){
			str = s.substring(i, i + l);
			Integer val = map.get(str);
			if (val == null){
				map.put(str, 1);
			}else{
				map.put(str, ++val);
				if (biggest < val) biggest = val;
			}
		}
		
		List<String> out = new ArrayList<>();
		for(String st : map.keySet()){
			if (map.get(st) == biggest){
				out.add(st);
			}
		}
		
		long stop = System.currentTimeMillis();
		System.out.println("Time: " + (stop - start) + " ms.");
		return out.toString();
	}
	
	

	private static String tstWay(String s, int l) {
		
		long start = System.currentTimeMillis();
		CountingTST<Integer> trie = new CountingTST<>();
		
//		Set<String> tmp = new HashSet<>();
		for(int i = 0; i + l <= s.length(); i++){
			String t = s.substring(i, i + l);
			System.out.println(t);
			trie.put(t, i);
//			tmp.add(t);
		}
		
		int freq = trie.getMostFrequent();
		List<String> out = new ArrayList<>();
		for(String str : trie.keys()){
			if (freq == trie.getFrequency(str)){
				out.add(str);
			}
		}
		
		long stop = System.currentTimeMillis();
		System.out.println("Time: " + (stop - start) + " ms.");
		return out.toString();
	}
	
	
	

	private static String suffixWay(String s, int l) {
		
		long start = System.currentTimeMillis();
		List<String> suffix = new ArrayList<>(s.length() * 2); 
		for(int i= 0; i + l <= s.length(); i++){
			suffix.add(s.substring(i, i + l));
		}
		
		suffix.sort(new StringComparator());
		
		Comparator<String> comp = new StringComparator();
		Map<Integer, List<String>> out = new HashMap<>();
		
		List<String> tmp;
		int biggest = 0;
		for(int i = 0, st = 0, count = 1; i < suffix.size() - 1; i++){
			
			if (comp.compare(suffix.get(i), suffix.get(i + 1)) == 0){
				if (st == 0) st = i;
				++count;
				if (count > biggest) biggest = count;
			}else{
				if (count == 1){
					continue;
				}else{
					tmp = out.get(count);
					if (tmp != null) tmp.add(suffix.get(st));
					else{
						tmp = new ArrayList<>();
						tmp.add(suffix.get(st));
						out.put(count, tmp);
					}
					tmp = null;
					st = 0;
					count = 1;
				}
			}
			
		}
		
		long stop = System.currentTimeMillis();
		System.out.println("Time: " + (stop - start) + " ms.");
		return out.get(biggest).toString();
	}
	
	static class StringComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			
			for(int i = 0; i < o1.length(); i++){
				if (o1.charAt(i) < o2.charAt(i)) return -1;
				if (o1.charAt(i) > o2.charAt(i)) return 1;					
			}
			
			return 0;
		}
		
	}
}
