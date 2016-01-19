package homework;

import java.util.ArrayList;
import java.util.List;

public class CountingTSTv2 {
	private int N = 0;
	private Node root = null;
	private int mostFreq = 1;
    private List<Integer> freqLeaf;
    private Kmers src;
	
	private static class Node {
		char c;
		int count = 0;
		int sIndex;
		boolean isEndLeaf = false;
		Node left, mid, right;
	}
	
	public CountingTSTv2(String txt, int klen) {
		this.src = new Kmers(txt, klen);
		this.freqLeaf = new ArrayList<>();
	}
	
    public void put(int subInx) {
//        if (!contains(key)) N++;
        root = put(root, subInx, 0);
    }
    
    public List<Integer> findMostFreq(){
    	findMFreq(root);
    	return freqLeaf;
    }
    
    public List<Integer> getMostFreq(){
    	return freqLeaf;
    }

    private void findMFreq(Node r) {
    	
        if (r.left != null) findMFreq(r.left);
        if (r.right != null) findMFreq(r.right);
        if (r.mid != null) findMFreq(r.mid);
        
        if (r.isEndLeaf){
        	if (mostFreq < r.count) {
        		mostFreq = r.count;
        		this.freqLeaf = new ArrayList<>();
        	}
        	if (mostFreq != 1 && mostFreq == r.count){
        		this.freqLeaf.add((Integer) r.sIndex);
        	}
        }
	}

	private Node put(Node x, int shift, int d) {
        char c = src.charAt(shift, d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }

        if      (c < x.c)               x.left  = put(x.left,  shift, d);
        else if (c > x.c)               x.right = put(x.right, shift, d);
        else if (d < src.kmerLength() - 1)  x.mid   = put(x.mid,   shift, d+1);
        else{
        	x.isEndLeaf = true;
        	x.sIndex = shift;
        	++x.count;
        	if (mostFreq < x.count) {
        		mostFreq = x.count;
        		this.freqLeaf = new ArrayList<>(); //встретив новый наибольший, старые не нужны
        	}
        	if (mostFreq != 1 && mostFreq == x.count){
        		this.freqLeaf.add((Integer) x.sIndex);
        	}
        	
        }

        return x;
    }
    
    public List<Integer> getShiftsOfMostFrequent(){
    	return this.freqLeaf;
    }
    
    public int getMostFrequent(){
    	return mostFreq;
    }
	
}
