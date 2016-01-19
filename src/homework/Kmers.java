package homework;

public class Kmers {
	
	private String src;
	private int klen;

	public Kmers(String source, int klen) {
		this.src = source;
		this.klen = klen;
	}
	
	public char charAt(int shift, int position){
		return this.src.charAt(shift + position);
	}
	
	public String getString(int shift){
		return src.substring(shift, shift + klen);
	}
	
	public int kmerLength(){
		return klen;
	}
	
	
	public static void main(String[] args) {
		
		String s = "ACAACTATGCATCACTATCGGGAACTATCCT";
		int l = 5;
		
		Kmers k = new Kmers(s, l);
		//print all kmers
		for (int i = 0; i <= s.length() - l; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < l; j++) {
				System.out.print(k.charAt(i, j));
			}
			System.out.println();
		}
		
		//random char request
		System.out.println( k.charAt(7, 0) == 'T' ? true : false);
		System.out.println( k.charAt(23, 0) == 'A' ? true : false);
		System.out.println( k.charAt(29, 0) == 'C' ? true : false);
		
		//Equal strings
		
		
	}
}
